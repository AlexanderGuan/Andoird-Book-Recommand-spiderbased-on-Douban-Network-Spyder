package com.example.testact;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.GONE;

public class ChatView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_view);
        TextView textView=(TextView)findViewById(R.id.chat_view_text);
        Intent intent=getIntent();
        String name=intent.getStringExtra("username");
        textView.setText(name);

        //沉浸式
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //返回按钮事件
        ImageButton imageButton=(ImageButton)findViewById(R.id.chat_return);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发送按钮的隐藏与显示
        Button button=(Button)findViewById(R.id.send);
        button.setVisibility(GONE);
        EditText editText=(EditText)findViewById(R.id.chat_edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Button button=(Button)findViewById(R.id.send);
                button.setVisibility(View.VISIBLE);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0) {
                    Button button = (Button) findViewById(R.id.send);
                    button.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0){
                    Button button=(Button)findViewById(R.id.send);
                    button.setVisibility(GONE);
                }
            }
        });
    }
}
