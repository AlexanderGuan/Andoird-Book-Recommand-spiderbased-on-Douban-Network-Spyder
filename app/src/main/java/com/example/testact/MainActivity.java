package com.example.testact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

;


public class MainActivity extends AppCompatActivity {
    private Button btn_phone_login;
    private Button btn_wechat_login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_phone_login=(Button)findViewById(R.id.btn_phone);


    }
    public void Regist(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, RegistActivity.class);
        startActivity(intent);
    }
    public void Login(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }


}