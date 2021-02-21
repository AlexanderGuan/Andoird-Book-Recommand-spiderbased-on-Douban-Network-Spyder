package com.example.testact;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;

import com.loopj.android.image.SmartImageView;

public class Home1Activity extends AppCompatActivity {

    //定义图片
    private int[] resId = new int[]{
            R.drawable.book1, R.drawable.book2, R.drawable.book3
    };
    //定义图片url数组
    private String[] photo_url=new String[]{
            "https://img3.doubanio.com/view/subject/l/public/s29635861.jpg","https://img9.doubanio.com/view/subject/l/public/s6807265.jpg",
            "https://img9.doubanio.com/view/subject/s/public/s33583535.jpg"
    };
    //String[] photo_url1={'https://img3.doubanio.com/view/subject/l/public/s27814883.jpgv',};
    //图片下标序号
   //private int count = 0;
    //定义手势监听对象
   // private GestureDetector gestureDetector;
    //定义ImageView对象
    private ImageView iv;
    private SmartImageView siv;
    private Button interbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home2);
        interbook=(Button)findViewById(R.id.btn_inter_book);
        siv=(SmartImageView) findViewById(R.id.photo_show);
        siv.setImageUrl(photo_url[0],R.mipmap.waiting,R.mipmap.bg1);


        //iv = (ImageView) findViewById(R.id.iv1);               //获取ImageView控件id
        //iv = (ImageView) findViewById(R.id.iv2);
        //iv = (ImageView) findViewById(R.id.iv3);
        //gestureDetector = new GestureDetector(onGestureListener);   //设置手势监听由onGestureListener处理

    }
    public void On_Book_Detail(View v)
    {
        Intent intent = new Intent();
        intent.setClass(Home1Activity.this, DetailActivity.class);
        startActivity(intent);
    }



    /*
    //当Activity被触摸时回调
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
     */

    /*
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

           // iv.setImageResource(resId[count]);  //切换imageView的图片
            return true;
        }
    };
    */



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