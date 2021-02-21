package com.example.testact;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.os.Message;

import android.widget.TextView;

import android.text.method.ScrollingMovementMethod;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.image.SmartImageView;
import android.widget.Toast;
public class DetailActivity extends AppCompatActivity {
    private Button btn_inter_comment;
    private Button btn_inter_wordcloud;
    private TextView book_info_name;
    private TextView book_info_author;
    private TextView book_info_publisher;
    private TextView book_info_pubDate;
    private TextView book_info_price;
    private TextView book_info_score;
    private TextView txt_introduce;
    //改动内容2--------------
    private Button btnCollect;
    private JSONObject js;
    private String bookInfo;
    private SmartImageView siv;
    private String userName;
    protected static final int COLLECT = 1;
    protected static final int NOTHING = 0;
    protected static final int REQUEST = 1;
    //改动内容2--------------

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        //改动内容--------------
        userName = getIntent().getStringExtra("userName");
        System.out.println("DetailActivity:" + userName);
        bookInfo = getIntent().getStringExtra("bookInfo");
        js = JSONObject.parseObject(bookInfo);
        System.out.println("DetailActivity:" +bookInfo);
        txt_introduce=(TextView)findViewById(R.id.txt_introduce);
        txt_introduce.setMovementMethod(ScrollingMovementMethod.getInstance());
        book_info_price =(TextView)findViewById(R.id.book_info_price);
        book_info_pubDate =(TextView)findViewById(R.id.book_info_pubDate);
        book_info_publisher = (TextView)findViewById(R.id.book_info_publisher);
        book_info_author = (TextView)findViewById(R.id.book_info_author);
        book_info_name = (TextView)findViewById(R.id.book_info_name);
        book_info_score = (TextView)findViewById(R.id.book_info_score);
        siv = (SmartImageView)findViewById(R.id.photo_show_detail);
        btnCollect = (Button)findViewById(R.id.collect);

        siv.setImageUrl(js.getString("ImageLink"),R.mipmap.waiting,R.mipmap.bg1);
        txt_introduce.setText(js.getString("BookIntro"));
        book_info_price.setText(js.getString("Price"));
        book_info_publisher.setText(js.getString("Publisher"));
        book_info_pubDate.setText(js.getString("Publication_date"));
        book_info_author.setText(js.getString("Author"));
        book_info_name.setText(js.getString("Title"));
        book_info_score.setText(js.getString("Grades"));
        //改动内容--------------

        btn_inter_comment=(Button)findViewById(R.id.btn_inter_comment);
        btn_inter_wordcloud=(Button)findViewById(R.id.btn_comment_wordcloud);
    }

    //改动内容--------------
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            //改动内容2--------------
            if(msg.what == COLLECT){
                Toast.makeText(DetailActivity.this,"收藏成功",Toast.LENGTH_LONG).show();
                finish();
            }
            else if (msg.what == NOTHING){

            }
            //改动内容2--------------
        }
    };

    public void click_inter_comment(View view) {
        Intent intent = new Intent();
        intent.setClass(DetailActivity.this, DetailComment.class);
        intent.putExtra("bookInfo",bookInfo);
        startActivity(intent);
    }
    public void click_inter_wordcloud(View view){
        Intent intent = new Intent();
        intent.setClass(DetailActivity.this,DetailWordCould.class);
        intent.putExtra("bookInfo",bookInfo);
        startActivity(intent);//启动Activity
    }

    public void CollectClick(View v){
        Collect collect = new Collect(userName,js.getString("BookID"));
        collect.run();
    }
    //改动内容2--------------
    class Collect{
        private ConnectByArray conn;
        private String[] keys;
        private String[] values;
        public Collect(String userName,String bookId){
            conn = new ConnectByArray();
            keys = new String[]{"User","BookID","T"};
            values = new String[]{userName,bookId,"Collect"};
        }

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
    //改动内容2--------------
}
