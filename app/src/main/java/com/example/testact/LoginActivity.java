package com.example.testact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.testact.http.Http;
import com.example.testact.http.HttpResponse;
import com.example.testact.http.OnHttpListener;
import com.example.testact.http.RequestParams;


public class LoginActivity extends AppCompatActivity  {
    private Flag lock;
    private StringBuilder stringBuilder;
    private EditText userNameText;
    private EditText passwd;
    private Button login;
    private String userName = "";
    private SerachPlus searcher = new SerachPlus();
    private String [] common_keys;
    private String [] recommend_values;
    private ArrayList<String> recommends;
    private String info;
    private ConnectByArray conn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        common_keys = new String[]{"Col","Key","Value"};
        recommend_values = new String[3];
        login = (Button)findViewById(R.id.btnLogin);
        userNameText = (EditText)findViewById(R.id.editPhone);
        passwd = (EditText)findViewById(R.id.editPwd);
    }

    public void OnMyRegistClick(View v)  {
        Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
        //intent.putExtra("info", "No66778899");
        LoginActivity.this.startActivity(intent);
    }

    private ArrayList<String> getRecommend(String []keys,String []values){
        new SearchRecommends(keys,values).start();
        new Getter_Recommends().start();
        return recommends;
    }

    class SearchRecommends extends Thread {
        private String[] keys;
        private String[] values;

        public SearchRecommends(String[] keys,String[] values){
            this.keys = keys;
            this.values = values;
        }
        @Override
        public void run() {
            try{
                Thread.sleep(1000);//等待数据写入数据库
                searcher.info(this.keys,this.values);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class Getter_Recommends extends Thread {
        private String msg;
        @Override
        public void run() {
            try{
                this.msg = searcher.myMsgGetter();
                recommends = searcher.getRecommendList(this.msg);
                System.out.println("recommends:" + this.msg);
                userName = userNameText.getText().toString();
                System.out.println("userName:" + userName);
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, WelcomeActivity.class);
                intent.putExtra("recommends", recommends);
                intent.putExtra("userName", userName);
                startActivity(intent);//启动Activity
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class login extends Thread{

        @Override
        public void run() {
            System.out.println(userNameText.getText().toString().trim());
            System.out.println(passwd.getText().toString().trim());
            final String phone = userNameText.getText().toString().trim();
            final String pwd = passwd.getText().toString().trim();
            String keys[] = new String[]{"logname","password"};
            String values[] = new String[]{phone,pwd};

            conn = new ConnectByArray();
            conn.HttpConnectMethod("http://121.199.53.20:8081/Android_Web/Demo2",
                    ConnectByArray.DO_GET, keys,values, new ConnectByArray.ConnectCallBack(){
                        @Override
                        public void getConnectMsg(String msg) {
                            if(msg.contains("s")){
                                recommend_values[0] = "RecommendList";
                                recommend_values[1] = "UserName";
                                recommend_values[2] = userNameText.getText().toString();
                                System.out.println("Start...");
                                Toast.makeText(LoginActivity.this,
                                        "载入中",Toast.LENGTH_LONG).show();
                                getRecommend(common_keys,recommend_values);
                            }else{
                                Toast.makeText(LoginActivity.this, "账号或密码输入错误", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void btnLogin(View view) {
        new login().start();
    }
    /*
    public void btnLogin(View view) {
        recommend_values[0] = "RecommendList";
        recommend_values[1] = "UserName";
        recommend_values[2] = userNameText.getText().toString();
        System.out.println("Start...");
        Toast.makeText(LoginActivity.this,
                "载入中",Toast.LENGTH_LONG).show();
        getRecommend(common_keys,recommend_values);
    }*/

}
