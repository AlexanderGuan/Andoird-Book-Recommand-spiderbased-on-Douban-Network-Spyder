package com.example.testact;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentActivity4 extends Fragment {

    private static final int RECEIVE = 1;
    private static final int NOTHING = 0;
    private ItemView msixin;
    private ItemView mxunzhang;
    private ItemView mshoucang;
    private ItemView mxiazai;
    private ItemView mhuiyuan;
    private ItemView msaosao;
    private ItemView myijian;
    private ItemView mbanben;
    private ItemView mhuancun;
    private ItemView mbangzhu;
    private ItemView mguanyu;
    public String bookInfo;
    public String userName;
    public String userInfo;
    public SerachPlus searcher;
    public Flag lock;
    public ArrayList<String> books;

    private OnDataTransmissionListener mListener;
    public interface OnDataTransmissionListener {
        public void dataTransmission(String data);
    }

    public void setOnDataTransmissionListener(OnDataTransmissionListener mListener) {
        this.mListener = mListener;
    }
    @Override
    //碎片的生命周期
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_activity4,container,false);



        //下面item控件
        msixin = (ItemView) view.findViewById(R.id.sixin);
        mxunzhang = (ItemView) view.findViewById(R.id.xunzhang);
        mshoucang = (ItemView) view.findViewById(R.id.shoucang);
        mxiazai = (ItemView) view.findViewById(R.id.xiazai);
        mhuiyuan = (ItemView) view.findViewById(R.id.huiyuan);
        msaosao = (ItemView) view.findViewById(R.id.saosao);
        myijian = (ItemView) view.findViewById(R.id.yijian);
        mbanben = (ItemView) view.findViewById(R.id.banben);
        mhuancun = (ItemView) view.findViewById(R.id.huancun);
        mbangzhu = (ItemView) view.findViewById(R.id.bangzhu);
        mguanyu = (ItemView) view.findViewById(R.id.guanyu);
        mshoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-----------改动2------------------------
                String[] common_keys = new String[]{"Col","Key","Value"};
                String[] values = new String[]{"UserInfo","UserName",userName};
                try{
                    new SearchMsg(common_keys,values).start();
                    new Getter_UserInfo().start();
                }catch(Exception e){
                    Toast.makeText(getActivity(),"Exception",Toast.LENGTH_LONG).show();
                }
                //-----------改动2------------------------
            }
        });
        mguanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    //创建碎片后要立即被触发的事件：
    @Override
    public void onViewCreated(View view,Bundle saveInstanceState)
    {
        super.onViewCreated(view,saveInstanceState);
        lock = new Flag();
        searcher = new SerachPlus();
        userName = getArguments().getString("userName");
        books = new ArrayList<>();
    }

    class SearchMsg extends Thread {
        private String[] keys;
        private String[] values;

        public SearchMsg(String[] keys,String[] values){
            this.keys = keys;
            this.values = values;
        }
        @Override
        public void run() {
            searcher.info(this.keys,this.values);
        }


    }
    class Getter_UserInfo extends Thread {
        @Override
        public void run() {
            try{
                userInfo = searcher.myMsgGetter();
                Message msg = new Message();
                if(userInfo!=null){
                    msg.what = RECEIVE;
                    msg.obj = userInfo;
                    handler.sendMessage(msg);
                }else {
                    msg.what = NOTHING;
                    handler.sendMessage(msg);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            //改动内容2--------------
            if(msg.what == RECEIVE){
                Toast.makeText(getActivity(),"succeed",Toast.LENGTH_LONG).show();
                JSONObject js = JSONObject.parseObject((String)userInfo);
                if(js.getJSONArray("BookList")!=null){
                    for(Object book:js.getJSONArray("BookList")){
                        books.add(String.valueOf(book));
                    }
                    new Get_Books().start();
                }
                else
                    Toast.makeText(getActivity(),"当前收藏为空",Toast.LENGTH_LONG).show();
            }
            else if (msg.what == NOTHING){
                Toast.makeText(getActivity(),"error!!!",Toast.LENGTH_LONG).show();
            }
            //改动内容2--------------
        }
    };


    class Get_Books extends Thread{
        private ArrayList<String> collects;
        private String[] keys;
        private String[] values;
        Get_Books(){collects = new ArrayList<String>();}

        @Override
        public void run() {
            this.keys = new String[]{"Col","Key","Value"};
            this.values = new String[3];
            this.values[0] = "BookInfo";
            this.values[1] = "BookID";
            synchronized (lock){
                try {
                    for(String bookId : books){
                        this.values[2] = bookId;
                        getBookInfos(this.keys,this.values);
                        lock.wait();
                        if(bookInfo == null){
                            System.out.println("Book is Null!Not Work!");
                            break;
                        }
                        collects.add(bookInfo);
                    }
                }catch (Exception e){

                }
            }
            System.out.println("FragmentActivity4_collects_size:"+collects.size());
            for(String temp:collects){
                System.out.println("FragmentActivity4_collects:"+temp);
            }
            Intent intent = new Intent();
            intent.setClass(getActivity(), CollectActivity.class);
            intent.putExtra("collects", collects);
            intent.putExtra("userName", userName);
            startActivity(intent);//启动Activity
        }
    }

    private void getBookInfos(String []keys, String []values){
        new Book_SearchMsg(keys,values).start();
        new Getter_BookInfo().start();
    }

    class Book_SearchMsg extends Thread {
        private String[] keys;
        private String[] values;
        private ArrayList<String> recommends;
        public Book_SearchMsg(String[] keys,String[] values){
            this.keys = keys;
            this.values = values;
        }

        @Override
        public void run() {
            searcher.info(this.keys,this.values);
        }
    }

    class Getter_BookInfo extends Thread {
        private String msg;

        @Override
        public void run() {
            try{
                synchronized (lock){
                    this.msg = searcher.myMsgGetter();
                    System.out.println("BookInfo:" + this.msg);
                    bookInfo = searcher.getBookinfo(this.msg);
                    System.out.println("In thread:" + bookInfo);
                    lock.notify();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
