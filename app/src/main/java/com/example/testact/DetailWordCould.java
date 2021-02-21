package com.example.testact;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;


import android.widget.ImageView;
import android.widget.TextView;

import android.text.method.ScrollingMovementMethod;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailWordCould extends AppCompatActivity {
    private Button btn_inter_comment;
    private Button btn_inter_wordcloud;
    private TextView book_info_name;
    private TextView book_info_author;
    private TextView book_info_publisher;
    private TextView book_info_pubDate;
    private TextView book_info_price;
    private TextView book_info_score;
    //改动内容--------------
    private SmartImageView siv;
    private Button btnCollect;
    private SmartImageView image_wordcloud;
    protected static final int CIYUN = 2;
    protected static final int COLLECT = 1;
    protected static final int NOTHING = 0;
    private HttpURLConnection conn;
    private JSONObject js;
    private String bookInfo;
    private String userName;
//改动内容--------------

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_wordcloud);
        //改动内容--------------
        book_info_name = (TextView)findViewById(R.id.book_info_name);
        book_info_author = (TextView)findViewById(R.id.book_info_author);
        book_info_publisher = (TextView)findViewById(R.id.book_info_publisher);
        book_info_pubDate = (TextView)findViewById(R.id.book_info_pubDate);
        book_info_price = (TextView)findViewById(R.id.book_info_price);
        book_info_score = (TextView)findViewById(R.id.book_info_score);
        image_wordcloud=(SmartImageView) findViewById(R.id.image_wordcloud);
        btn_inter_comment=(Button)findViewById(R.id.btn_inter_comment);
        btn_inter_wordcloud=(Button)findViewById(R.id.btn_comment_wordcloud);
        btnCollect = (Button)findViewById(R.id.collect);
        siv = (SmartImageView)findViewById(R.id.photo_show_detail);

        userName = getIntent().getStringExtra("userName");
        System.out.println("DetailWordCould:" + userName);
        bookInfo = getIntent().getStringExtra("bookInfo");
        System.out.println("DetailWordCould_bookInfo:" + bookInfo);
        js = JSONObject.parseObject(bookInfo);
        System.out.println("DetailWordCould_bookInfo:" +bookInfo);

        siv.setImageUrl(js.getString("ImageLink"),R.mipmap.waiting,R.mipmap.bg1);
        book_info_price.setText(js.getString("Price"));
        book_info_publisher.setText(js.getString("Publisher"));
        book_info_pubDate.setText(js.getString("Publication_date"));
        book_info_author.setText(js.getString("Author"));
        book_info_name.setText(js.getString("Title"));
        book_info_score.setText(js.getString("Grades"));

        image_wordcloud.setImageAlpha(R.mipmap.waiting);
        String url = "http://121.199.53.20:8081/Android_Web/Demo2?BookID=";
        String bookID = js.getString("BookID");
        url = url + bookID + "&Type=Ciyun";
        System.out.println("DetailWordCould_url:" +url);
        new ReceivePic(url).start();
        System.out.println(url);
        //改动内容--------------

        btn_inter_comment=(Button)findViewById(R.id.btn_inter_comment);
        btn_inter_wordcloud=(Button)findViewById(R.id.btn_comment_wordcloud);
    }
    //改动内容--------------
    class ReceivePic extends Thread{
        private String url;
        public ReceivePic(String url){
            this.url = url;
        }

        @Override
        public void run() {
            try {
                URL conn_url = new URL(this.url);
                conn = (HttpURLConnection) conn_url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                int code = conn.getResponseCode();
                if(code == 200){
                    System.out.println("ShowCiyunPic:Enter");
                    InputStream is = conn.getInputStream();
                    Bitmap ciyun = BitmapFactory.decodeStream(is);
                    Message msg = new Message();
                    msg.what = CIYUN;
                    msg.obj = ciyun;
                    handler.sendMessage(msg);
                }else {
                    Message msg = new Message();
                    msg.what = NOTHING;
                    handler.sendMessage(msg);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            conn.disconnect();
        }
    }

    public void click_inter_comment(View view) {
        Intent intent = new Intent();
        intent.setClass(DetailWordCould.this, DetailComment.class);
        intent.putExtra("bookInfo",bookInfo);
        startActivity(intent);
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == CIYUN){
                //Toast.makeText(DetailActivity.this,"收藏成功",Toast.LENGTH_LONG).show();
                Bitmap ciyun = (Bitmap) msg.obj;

                image_wordcloud.setImageBitmap(ciyun);
            }
            else if (msg.what == NOTHING){
                System.out.println("ShowCiyunPic:Nothing Here!!!");
            }else if(msg.what == COLLECT){
                Toast.makeText(DetailWordCould.this,"收藏成功",Toast.LENGTH_LONG).show();
            }
        }
    };

    public void CollectClick(View v){
        Collect collect = new Collect(userName,js.getString("BookID"));
        collect.start();
    }

    class Collect extends  Thread{
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
//改动内容--------------

}