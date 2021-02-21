package com.example.testact;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.view.MotionEvent;
import java.util.ArrayList;

public class BottomBarActivity extends AppCompatActivity implements View.OnClickListener
{
    //初始底部导航栏的所有控件
    //private RelativeLayout bottom_title_bar;
    private LinearLayout main_body_bar;
    private RelativeLayout main_body;

    private RelativeLayout bottom_bar_1_btn;
    private RelativeLayout bottom_bar_2_btn;
    private RelativeLayout bottom_bar_3_btn;
    private RelativeLayout bottom_bar_4_btn;


    private ImageView bottom_bar_image_1;
    private ImageView bottom_bar_image_2;
    private ImageView bottom_bar_image_3;
    private ImageView bottom_bar_image_4;

    private TextView bottom_bar_text_1;
    private TextView bottom_bar_text_2;
    private TextView bottom_bar_text_3;
    private TextView bottom_bar_text_4;


    //初始化三个Fragment碎片
    private  FragmentActivity1 fragment1;
    private  FragmentActivity2 fragment2;
    private  FragmentActivity3 fragment3;
    private  FragmentActivity4 fragment4;

    int TAG=0;

    //绑定导航栏中所有的控件
    private void initView()
    {
        main_body=(RelativeLayout)findViewById(R.id.main_body);
        main_body_bar=findViewById(R.id.main_body_bar);
        bottom_bar_1_btn=findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_2_btn=findViewById(R.id.bottom_bar_2_btn);
        bottom_bar_3_btn=findViewById(R.id.bottom_bar_3_btn);
        bottom_bar_4_btn=findViewById(R.id.bottom_bar_4_btn);
        bottom_bar_image_1=findViewById(R.id.bottom_bar_image_1);
        bottom_bar_image_2=findViewById(R.id.bottom_bar_image_2);
        bottom_bar_image_3=findViewById(R.id.bottom_bar_image_3);
        bottom_bar_image_4=findViewById(R.id.bottom_bar_image_4);
        bottom_bar_text_1=findViewById(R.id.bottom_bar_text_1);
        bottom_bar_text_2=findViewById(R.id.bottom_bar_text_2);
        bottom_bar_text_3=findViewById(R.id.bottom_bar_text_3);
        bottom_bar_text_4=findViewById(R.id.bottom_bar_text_4);

        bottom_bar_1_btn.setOnClickListener(this);
        bottom_bar_2_btn.setOnClickListener(this);
        bottom_bar_3_btn.setOnClickListener(this);
        bottom_bar_4_btn.setOnClickListener(this);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar);
        initView();
        //改动内容--------------
        ArrayList<String> books;
        books = getIntent().getStringArrayListExtra("recommends");
        System.out.println();
        fragment1=new FragmentActivity1();
        Bundle bundle1 = new Bundle();
        bundle1.putStringArrayList("recommends",books);
        bundle1.putString("userName",getIntent().getStringExtra("userName"));
        fragment1.setArguments(bundle1);
        //改动内容--------------
        fragment2=new FragmentActivity2();
        fragment3=new FragmentActivity3();
        fragment4=new FragmentActivity4();
        Bundle bundle4 = new Bundle();
        bundle4.putString("userName",getIntent().getStringExtra("userName"));
        fragment4.setArguments(bundle4);
    }
    //底部导航栏状态切换方法
    public void setSelectStatus(int index)
    {
        switch (index){
            case 0:
                bottom_bar_image_1.setImageResource(R.drawable.main_r);
                bottom_bar_text_1.setTextColor(Color.parseColor("#C91F37"));

                bottom_bar_image_2.setImageResource(R.drawable.chat);
                bottom_bar_text_2.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_3.setImageResource(R.drawable.look);
                bottom_bar_text_3.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_4.setImageResource(R.drawable.mine);
                bottom_bar_text_4.setTextColor(Color.parseColor("#000000"));


                break;
            case 1:
                bottom_bar_image_1.setImageResource(R.drawable.main);
                bottom_bar_text_1.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_2.setImageResource(R.drawable.chat_r);
                bottom_bar_text_2.setTextColor(Color.parseColor("#C91F37"));

                bottom_bar_image_3.setImageResource(R.drawable.look);
                bottom_bar_text_3.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_4.setImageResource(R.drawable.mine);
                bottom_bar_text_4.setTextColor(Color.parseColor("#000000"));


                break;
            case 2:
                bottom_bar_image_1.setImageResource(R.drawable.main);
                bottom_bar_text_1.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_2.setImageResource(R.drawable.chat);
                bottom_bar_text_2.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_3.setImageResource(R.drawable.look_r);
                bottom_bar_text_3.setTextColor(Color.parseColor("#C91F37"));

                bottom_bar_image_4.setImageResource(R.drawable.mine);
                bottom_bar_text_4.setTextColor(Color.parseColor("#000000"));


                break;
            case 3:
                bottom_bar_image_1.setImageResource(R.drawable.main);
                bottom_bar_text_1.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_2.setImageResource(R.drawable.chat);
                bottom_bar_text_2.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_3.setImageResource(R.drawable.look);
                bottom_bar_text_3.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_4.setImageResource(R.drawable.mine_r);
                bottom_bar_text_4.setTextColor(Color.parseColor("#C91F37"));


                break;

            case 4:
                bottom_bar_image_1.setImageResource(R.drawable.main);
                bottom_bar_text_1.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_2.setImageResource(R.drawable.chat);
                bottom_bar_text_2.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_3.setImageResource(R.drawable.look);
                bottom_bar_text_3.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_4.setImageResource(R.drawable.mine);
                bottom_bar_text_4.setTextColor(Color.parseColor("#0097F7"));


                break;

        }
    }
    //进行点击操作
    public void onClick(View v)
    {
        if(TAG==0){
            TAG=1;
            switch (v.getId()){
                case R.id.bottom_bar_1_btn:
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment1).commitAllowingStateLoss();
                    setSelectStatus(0);
                    break;
                case R.id.bottom_bar_2_btn:
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment2).commitAllowingStateLoss();
                    setSelectStatus(1);
                    break;
                case R.id.bottom_bar_3_btn:
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment3).commitAllowingStateLoss();
                    setSelectStatus(2);
                    break;
                case R.id.bottom_bar_4_btn:
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment4).commitAllowingStateLoss();
                    setSelectStatus(3);
                    break;

            }

        }else if(TAG==1){
            switch (v.getId()){
                case R.id.bottom_bar_1_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment1).commitAllowingStateLoss();
                    setSelectStatus(0);
                    break;
                case R.id.bottom_bar_2_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment2).commitAllowingStateLoss();
                    setSelectStatus(1);
                    break;
                case R.id.bottom_bar_3_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment3).commitAllowingStateLoss();
                    setSelectStatus(2);
                    break;
                case R.id.bottom_bar_4_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment4).commitAllowingStateLoss();
                    setSelectStatus(3);
                    break;

            }
        }
    }

    //下面是实现Fragment的触控监听功能
    //定义接口
    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent event);
    }

    // 保存MyTouchListener接口的列表
    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(1);
    //提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }
    //提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener) ;
    }
    //分发触摸事件给所有注册了MyTouchListener的接口
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            if(listener != null) {
                listener.onTouch(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}


