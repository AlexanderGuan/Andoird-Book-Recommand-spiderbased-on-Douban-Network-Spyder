package com.example.testact;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener{
    private Button Kepu;
    private Button Hulianwang;
    private Button Kexue;
    private Button Biancheng;
    private Button Jiaohusheji;
    private Button Yonghutiyan;
    private Button Suanfa;
    private Button Keji;
    private Button Web;
    private Button Jiaohu;
    private Button Tongxin;
    private Button Ue;
    private Button Shenjingwangluo;
    private Button Ucd;
    private Button Chengxu;
    private Button sub_choose;
    private StringBuilder stringBuilder;
    private String [] common_keys;
    private String [] recommend_values;
    private ArrayList<String> recommends;
    private SerachPlus searcher = new SerachPlus();
    //private TextView showMsg;
    private String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);
        stringBuilder = new StringBuilder();
        //showMsg = (TextView) findViewById(R.id.txt_ShowMsg);
        //username = "13128155530";由注册页面传入
        username = getIntent().getStringExtra("userName");
        common_keys = new String[]{"Col","Key","Value"};
        recommend_values = new String[3];

        Kepu=(Button)findViewById(R.id.kepu);
        Hulianwang=(Button)findViewById(R.id.hulianwang);
        Kexue=(Button)findViewById(R.id.kexue);
        Biancheng=(Button)findViewById(R.id.biancheng);
        Jiaohusheji=(Button)findViewById(R.id.jiaohusheji);
        Yonghutiyan=(Button)findViewById(R.id.yonghutiyan);
        Suanfa=(Button)findViewById(R.id.suanfa);
        Keji=(Button)findViewById(R.id.keji);
        Web=(Button)findViewById(R.id.web);
        Jiaohu=(Button)findViewById(R.id.jiaohu);
        Tongxin=(Button)findViewById(R.id.tongxin);
        Ue=(Button)findViewById(R.id.ue);
        Shenjingwangluo=(Button)findViewById(R.id.shenjinwangluo);
        Ucd=(Button)findViewById(R.id.ucd);
        Chengxu=(Button)findViewById(R.id.chengxu);
        sub_choose=(Button)findViewById(R.id.btn_choose);

        Kepu.setOnClickListener(this);
        Hulianwang.setOnClickListener(this);
        Kexue.setOnClickListener(this);
        Biancheng.setOnClickListener(this);
        Jiaohusheji.setOnClickListener(this);
        Yonghutiyan.setOnClickListener(this);
        Suanfa.setOnClickListener(this);
        Keji.setOnClickListener(this);
        Web.setOnClickListener(this);
        Jiaohu.setOnClickListener(this);
        Tongxin.setOnClickListener(this);
        Ue.setOnClickListener(this);
        Shenjingwangluo.setOnClickListener(this);
        Ucd.setOnClickListener(this);
        Chengxu.setOnClickListener(this);
        sub_choose.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.keji:
                Keji.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",科技");
                break;
            case R.id.hulianwang:
                Hulianwang.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",互联网");
                break;
            case R.id.kepu:
                Kepu.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",科普");
                break;
            case R.id.kexue:
                Kexue.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",科学");
                break;
            case R.id.biancheng:
                Biancheng.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",编程");
                break;
            case R.id.jiaohusheji:
                Jiaohusheji.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",交互设计");
                break;
            case R.id.yonghutiyan:
                Yonghutiyan.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",用户体验");
                break;
            case R.id.jiaohu:
                Jiaohu.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",交互");
                break;
            case R.id.web:
                Web.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",web");
                break;
            case R.id.suanfa:
                Suanfa.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",算法");
                break;
            case R.id.tongxin:
                Tongxin.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",通信");
                break;
            case R.id.ucd:
                Ucd.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",UCD");
                break;
            case R.id.chengxu:
                Chengxu.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",程序");
                break;
            case R.id.shenjinwangluo:
                Shenjingwangluo.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",神经网络");
                break;
            case R.id.ue:
                Ue.setBackground(getResources().getDrawable(R.drawable.check_border2));
                stringBuilder.append(",UE");
                break;
            case R.id.btn_choose:
                recommend_values[0] = "RecommendList";
                recommend_values[1] = "UserName";
                recommend_values[2] = username;
                System.out.println("Start...");
                Toast.makeText(ChooseActivity.this,
                        "载入中",Toast.LENGTH_LONG).show();
                getRecommend(common_keys,recommend_values);
                sendTagSelected(username);
                break;
        }
    }


    private ArrayList<String> getRecommend(String []keys,String []values){
        new SearchRecommends(keys,values).start();
        new Getter_Recommends().start();
        return recommends;
    }
    //发送已选择Tag，无返回值
    private void sendTagSelected(String userName){
        ConnectByArray conn = new ConnectByArray();
        String temp = stringBuilder.toString();
        String taglist = "";
        for (int i = 0; i < temp.toCharArray().length - 1; i++) {
            taglist += temp.toCharArray()[i + 1];
        }

        String []keys = {"userName","ST"};
        String []values = {userName,taglist};
        System.out.println("keys:" + userName);
        System.out.println("values:" + taglist);
        conn.HttpConnectMethod("http://121.199.53.20:8081/Android_Web/Demo2",
                ConnectByArray.DO_GET, keys,values, new ConnectByArray.ConnectCallBack(){
                    @Override
                    public void getConnectMsg(String msg) {

                    }
                });
        //showMsg.setText(conn.httpurl);
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

                System.out.println("userName:" + username);
                Intent intent = new Intent();
                intent.setClass(ChooseActivity.this, WelcomeActivity.class);
                intent.putExtra("recommends", recommends);
                intent.putExtra("userName", username);
                startActivity(intent);//启动Activity
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
