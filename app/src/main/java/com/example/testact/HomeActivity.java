package com.example.testact;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {


    //定义图片
    private int[] resId = new int[]{
            R.drawable.book1, R.drawable.book2, R.drawable.book3
    };
    //定义图片url数组
    private String[] photo_url=new String[]{
            "https://img3.doubanio.com/view/subject/l/public/s27814883.jpgv","https://img9.doubanio.com/view/subject/l/public/s6807265.jpg",
            "https://book.douban.com/subject/27593848/"
    };
    //String[] photo_url1={'https://img3.doubanio.com/view/subject/l/public/s27814883.jpgv',};
    //图片下标序号
    private int count = 0;
    //定义手势监听对象
    private GestureDetector gestureDetector;
    //定义ImageView对象
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        iv = (ImageView) findViewById(R.id.iv1);               //获取ImageView控件id
        iv = (ImageView) findViewById(R.id.iv2);
        iv = (ImageView) findViewById(R.id.iv3);
        gestureDetector = new GestureDetector(onGestureListener);   //设置手势监听由onGestureListener处理






    }





    //当Activity被触摸时回调
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

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
                count++;
                count %= (resId.length - 1);        //想显示多少图片，就把定义图片的数组长度-1
            } else if (x < 0) {
                count--;
                count = (count + (resId.length - 1)) % (resId.length - 1);
            }

            iv.setImageResource(resId[count]);  //切换imageView的图片
            return true;
        }
    };




/*

    public void OnMyChatClick(View view) {
        Intent intent = new Intent();
        intent.setClass(HomeActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    public void OnMyLookClick(View view) {
        Intent intent = new Intent();
        intent.setClass(HomeActivity.this, LookActivity.class);
        startActivity(intent);
    }
*/
}