package com.example.testact;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import com.alibaba.fastjson.JSONObject;
/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentActivity1 extends Fragment
{

    private TextView tv_text1;
    //定义图片url数组
    //改动内容---------
    //private int mark = 0;//标志第几本书
    private String[] photo_url;
    private int i = 0;//图片下标的序号
    private ArrayList<String> books;
    private String userName;
    private TextView book_info_author;
    private TextView book_info_name;
    private ArrayList<JSONObject> jsonObjects;
    //改动内容---------

    //注册控件
    private SmartImageView siv;
    private Button interbook;
    //定义监听对象
    private GestureDetector gestureDetector;

    /*               */
    public FragmentActivity1() {
        // Required empty public constructor
    }

    @Override
    //Fragment碎片的生命周期，加载页面布局
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_activity1,container,false);


        //绑定控件,把Activity里的findViweById改成view.findViewById
        interbook=(Button)view.findViewById(R.id.btn_inter_book);
        siv=(SmartImageView)view.findViewById(R.id.photo_show);
        book_info_author = (TextView)view.findViewById(R.id.book_info_author);
        book_info_name = (TextView)view.findViewById(R.id.book_info_name);
        gestureDetector = new GestureDetector(onGestureListener);   //设置手势监听由onGestureListener处理

        //给button设置监听：调用按钮执行方法
        interbook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                On_Book_Detail(v);
            }
        });

        /* Fragment中，注册、接收MainActivity的Touch回调的对象
            * 重写其中的onTouchEvent函数，并进行该Fragment的逻辑处理
        */
        BottomBarActivity.MyOnTouchListener onTouchListener = new BottomBarActivity.MyOnTouchListener() {
            @Override
            public boolean onTouch(MotionEvent event) {
                // 处理手势事件
                gestureDetector.onTouchEvent(event);
                return false;



            }
        };

        // 将myTouchListener注册到分发列表
        ((BottomBarActivity)this.getActivity()).registerMyOnTouchListener(onTouchListener);


        return view;
    }
    //创建碎片后要立即被触发的事件：相当于Activity的oncreate
    @Override
    public void onViewCreated(View view,Bundle saveInstanceState)
    {
        super.onViewCreated(view,saveInstanceState);

//改动内容--------------
        //将Url传输到SmartImage控件中，第二第三个参数分别为：加载时候显示的图片、加载失败时显示的图片
        books = new ArrayList<>();
        //绑定控件,把Activity里的findViweById改成view.findViewById
        books = getArguments().getStringArrayList("recommends");
        userName = getArguments().getString("userName");
        jsonObjects = new ArrayList<JSONObject>();
        if(books.isEmpty())
            System.out.println("FragmentActivity1:Nothing here!!!!!");
        else {
            photo_url = new String[books.size()];
            System.out.println( "FragmentActivity1_books.size:" + books.size());
            int n = 0;
            JSONObject js;
            for (String book:books)
                System.out.println("FragmentActivity1_BOOK:" + book);
            for (String book:books){
                if(book.equals(null))
                    continue;
                js = JSONObject.parseObject(book);
                jsonObjects.add(js);
                //bookJson.add(js);
                if(js.getString("ImageLink").isEmpty())
                    System.out.println("FragmentActivity1_BookID:" + js.getString("BookID"));
                photo_url[n] = js.getString("ImageLink");
                System.out.println("FragmentActivity1:" + photo_url[n]);
                n += 1;
            }
        }
//将Url传输到SmartImage控件中，第二第三个参数分别为：加载时候显示的图片、加载失败时显示的图片
        //siv.setImageUrl(photo_url[i],R.mipmap.waiting,R.mipmap.bg1);
        //book_info_author.setText(jsonObjects.get(i).getString("Author"));
        //book_info_name.setText(jsonObjects.get(i).getString("Title"));
        //siv.setImageUrl(photo_url[5],R.mipmap.waiting,R.mipmap.bg1);
        //改动内容--------------

    }
    //点击事件按钮的处理函数
    public void On_Book_Detail(View v)
    {
        //改动内容--------------
        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailActivity.class);
        intent.putExtra("bookInfo",books.get(i));
        intent.putExtra("userName",userName);
        startActivity(intent);
        //改动内容--------------
    }
    //当Activity被触碰时回调
    /*
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
     */
    //自定义GestureDetector的手势识别监听器
    private GestureDetector.OnGestureListener onGestureListener
            = new GestureDetector.SimpleOnGestureListener() {
        //当识别的手势是滑动手势时回调onFinger方法
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //得到手触碰位置的起始点和结束点坐标 x , y ，并进行计算
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();
            //通过计算判断是向左还是向右滑动
            if (x > 0) {
                i++;
                i %= (photo_url.length - 1);
                // 想显示多少图片，就把定义图片的数组长度-1

            } else if (x < 0) {
                i--;
                i = (i + (photo_url.length - 1)) % (photo_url.length - 1);

            }

            siv.setImageUrl(photo_url[i],R.mipmap.bg6,R.mipmap.waiting);  //切换SmartimageView的图片
            book_info_author.setText(jsonObjects.get(i).getString("Author"));
            book_info_name.setText(jsonObjects.get(i).getString("Title"));
            return true;
        }
    };

}
