package com.example.testact;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.image.SmartImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowCiyunPic extends AppCompatActivity {
    private ImageView iv;
    protected static final int CIYUN = 1;
    protected static final int NOTHING = 0;
    private HttpURLConnection conn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ciyuntest);
        String url = "http://192.168.3.182:8090/Android_Web/DemoLocal?BookID=";
        String bookID = getIntent().getStringExtra("bookId");
        url = url + bookID + "&Type=Ciyun";
        new ReceivePic(url).start();
        System.out.println(url);
        iv = (ImageView)findViewById(R.id.ciyun);
    }

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

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == CIYUN){
                //Toast.makeText(DetailActivity.this,"收藏成功",Toast.LENGTH_LONG).show();
                Bitmap ciyun = (Bitmap) msg.obj;
                iv.setImageBitmap(ciyun);

            }

            else if (msg.what == NOTHING){
                System.out.println("ShowCiyunPic:Nothing Here!!!");
            }
        }
    };
}
