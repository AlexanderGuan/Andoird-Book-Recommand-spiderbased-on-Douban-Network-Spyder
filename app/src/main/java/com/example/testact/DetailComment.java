package com.example.testact;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;


import android.widget.TextView;

import android.text.method.ScrollingMovementMethod;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

public class DetailComment extends AppCompatActivity {
    private Button btn_inter_comment;
    private Button btn_inter_wordcloud;
    private TextView book_info_name;
    private TextView book_info_author;
    private TextView book_info_publisher;
    private TextView book_info_pubDate;
    private TextView book_info_price;
    private TextView book_info_score;
    private TextView txt_comment;
    private Button btnCollect;
    private JSONObject js;
    private String bookInfo;
    private SmartImageView siv;
    private String userName;
    protected static final int COLLECT = 1;
    protected static final int NOTHING = 0;
    protected static final int GET = 2;
    private SerachPlus searcher = new SerachPlus();
    private ArrayList<String> comments;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_comment);
//改动内容--------------
        userName = getIntent().getStringExtra("userName");
        System.out.println("DetailComment:" + userName);
        bookInfo = getIntent().getStringExtra("bookInfo");
        js = JSONObject.parseObject(bookInfo);
        System.out.println("DetailComment:" +bookInfo);
        String[] common_keys = new String[]{"Col","Key","Value"};
        String[] comment_values = new String[]{"Comment","BookID",""};
        comment_values[2] = js.getString("BookID");
        new SearchMsg(common_keys,comment_values).start();
        new Getter_Comments().start();

        book_info_name = (TextView)findViewById(R.id.book_info_name);
        book_info_author = (TextView)findViewById(R.id.book_info_author);
        book_info_publisher = (TextView)findViewById(R.id.book_info_publisher);
        book_info_pubDate = (TextView)findViewById(R.id.book_info_pubDate);
        book_info_price = (TextView)findViewById(R.id.book_info_price);
        book_info_score = (TextView)findViewById(R.id.book_info_score);
        siv=(SmartImageView) findViewById(R.id.photo_show_detail);
        btn_inter_comment=(Button)findViewById(R.id.btn_inter_comment);
        btn_inter_wordcloud=(Button)findViewById(R.id.btn_comment_wordcloud);
        btnCollect = (Button)findViewById(R.id.collect);
        txt_comment=(TextView)findViewById(R.id.txt_comment);
        txt_comment.setMovementMethod(ScrollingMovementMethod.getInstance());

        siv.setImageUrl(js.getString("ImageLink"),R.mipmap.waiting,R.mipmap.bg1);
        book_info_price.setText(js.getString("Price"));
        book_info_publisher.setText(js.getString("Publisher"));
        book_info_pubDate.setText(js.getString("Publication_date"));
        book_info_author.setText(js.getString("Author"));
        book_info_name.setText(js.getString("Title"));
        book_info_score.setText(js.getString("Grades"));



        btn_inter_comment=(Button)findViewById(R.id.btn_inter_comment);
        btn_inter_wordcloud=(Button)findViewById(R.id.btn_comment_wordcloud);
    }

    public void click_inter_wordcloud(View view){
        Intent intent = new Intent();
        intent.setClass(DetailComment.this,DetailWordCould.class);
        intent.putExtra("bookInfo",bookInfo);
        startActivity(intent);//启动Activity
    }

    public void CollectClick_cm(View v){
        Collect collect = new Collect(userName,js.getString("BookID"));
        collect.start();
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == COLLECT){
                Toast.makeText(DetailComment.this,"收藏成功",Toast.LENGTH_LONG).show();
            }
            else if (msg.what == NOTHING){
                Toast.makeText(DetailComment.this,"DetailComment:NOTHING!!!",Toast.LENGTH_LONG).show();
                System.out.println("DetailComment:NOTHING!!!");
            }else if(msg.what == GET){
                StringBuilder builder = new StringBuilder();
                for(Object comment:(ArrayList)msg.obj){
                    builder.append(String.valueOf(comment));
                    builder.append("\n");
                    builder.append("\n");
                }

                txt_comment.setText(builder.toString());
            }
        }
    };

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

    class Getter_Comments extends Thread {
        private String msg;
        @Override
        public void run() {
            try{
                this.msg = searcher.myMsgGetter();
                System.out.println("Comments:" + this.msg);
                comments = searcher.getComment(this.msg);
                Message message = new Message();
                message.what = GET;
                message.obj = comments;
                handler.sendMessage(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class Collect extends Thread{
        private ConnectByArray conn;
        private String[] keys;
        private String[] values;
        public Collect(String userName,String bookId){
            conn = new ConnectByArray();
            keys = new String[]{"User","BookID","T"};
            values = new String[]{userName,bookId,"Collect"};
        }

        @Override
        public void run(){
            conn.HttpConnectMethod("http://121.199.53.20:8081/Android_Web/Demo2",
                    ConnectByArray.DO_GET, keys,values, new ConnectByArray.ConnectCallBack(){
                        @Override
                        public void getConnectMsg(String msg) {
                            Message message = new Message();
                            message.what = COLLECT;
                            handler.sendMessage(message);
                        }
                    });
        }
    }
}