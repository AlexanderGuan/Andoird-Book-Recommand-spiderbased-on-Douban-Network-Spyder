package com.example.testact;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testact.http.Http;
import com.example.testact.http.HttpResponse;
import com.example.testact.http.OnHttpListener;
import com.example.testact.http.RequestParams;

public class RegistActivity extends AppCompatActivity {
    private EditText editPhone;
    private EditText editPwd;
    private Button btnRegist;
    private ConnectByArray conn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPwd = (EditText) findViewById(R.id.editPwd);
        btnRegist = (Button) findViewById(R.id.btnRegist);
    }

   public void OnMyClick(View view)
   {
       new regist().start();
   }

   class regist extends Thread{

       @Override
       public void run() {
       System.out.println(editPhone.getText().toString().trim());
       System.out.println(editPwd.getText().toString().trim());
       final String phone = editPhone.getText().toString().trim();
       final String pwd = editPwd.getText().toString().trim();
       String keys[] = new String[]{"rigistname","password"};
       String values[] = new String[]{phone,pwd};

       conn = new ConnectByArray();
       conn.HttpConnectMethod("http://121.199.53.20:8081/Android_Web/Demo2",
               ConnectByArray.DO_GET, keys,values, new ConnectByArray.ConnectCallBack(){
                   @Override
                   public void getConnectMsg(String msg) {
                       if(msg.contains("s")){
                           System.out.println();
                           Toast.makeText(RegistActivity.this,"注册成功!",Toast.LENGTH_LONG).show();
                           Intent intent=new Intent();
                           intent.putExtra("userName",editPhone.getText().toString().trim());
                           intent.setClass(RegistActivity.this, ChooseActivity.class);
                           startActivity(intent);
                       }else if (msg == null){
                           Toast.makeText(RegistActivity.this, "Null", Toast.LENGTH_LONG).show();
                       }
                   }
               });
       }
   }

}
/*
System.out.println();
        Toast.makeText(RegistActivity.this,"注册成功!",Toast.LENGTH_LONG).show();
        Intent intent=new Intent();
        intent.setClass(RegistActivity.this, ChooseActivity.class);
        startActivity(intent);*/