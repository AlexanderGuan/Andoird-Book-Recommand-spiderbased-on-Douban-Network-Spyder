package com.example.testact;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ReciveMsg {
    private Flag lock;
    private StringBuilder stringBuilder;
    private TextView showID;
    private TextView showMsg;
    private String userName = "";
    private SerachPlus searcher = new SerachPlus();
    private String [] common_keys;
    private String [] recommend_values;
    private String [] book_values;
    private String [] comment_values;
    private ArrayList<String> recommends;
    private ArrayList<String> comments;
    private String bookInfo;
    private String info;

    protected void main(String []args){

        /*userName = "13190856402";//由登录或注册页面传入
        showID = (TextView) findViewById(R.id.txt_BookId);
        showMsg = (TextView) findViewById(R.id.showBook);
        stringBuilder = new StringBuilder();

        common_keys = new String[]{"Col","Key","Value"};//生成URL用的key
        recommend_values = new String[]{"RecommendList","UserName",userName};//生成URL用的value
        book_values = new String[]{"BookInfo","BookID",""};
        comment_values = new String[]{"Comment","BookID",""};
        recommends = new ArrayList<String>();

        String [] temp= new String[]{"1260",
                "1180",
                "1220",
                "1280",
                "1000",
                "1160",
                "1060",
                "1140",
                "1020",
                "1040",
                "1100",
                "1200",
                "1120",
                "1080",
                "1240",
                "1261",
                "1141",
                "1121",
                "1181",
                "1201"};//在实际中为ArrayList<String>，由登录或注册页面执行getRecommend()后生成，并传递

        for(int i = 0;i < temp.length;i++){
            recommends.add(temp[i]);
        }

        for(String bookId:recommends){
            //book_values[2] = bookId;
            comment_values[2] = bookId;
            //getBookInfos(common_keys,book_values);
            getComments(common_keys,comment_values);
        }*///示例
    }

    private ArrayList<String> getRecommend(String []keys,String []values){
        new SearchRecommends(keys,values).start();
        new Getter_Recommends().start();
        return recommends;
    }

    private void getComments(String []keys,String []values){
        new SearchMsg(keys,values).start();
        new Getter_Comments().start();
    }

    private void getBookInfos(String []keys,String []values){
        new SearchMsg(keys,values).start();
        new Getter_BookInfo().start();
    }

    //获取原始信息
    class SearchMsg extends Thread {
        private String[] keys;
        private String[] values;

        public SearchMsg(String[] keys,String[] values){
            this.keys = keys;
            this.values = values;
        }
        @Override
        public void run() {
            searcher.info(this.keys,this.values);
        }
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
                Thread.sleep(2000);
                searcher.info(this.keys,this.values);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class Getter extends Thread {
        @Override
        public void run() {
            try{
                info = searcher.myMsgGetter();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    class Getter_BookInfo extends Thread {
        private String msg;

        @Override
        public void run() {
            try{
                this.msg = searcher.myMsgGetter();
                System.out.println("BookInfo:" + this.msg);
                bookInfo = searcher.getBookinfo(this.msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class Getter_Comments extends Thread {
        private String msg;
        @Override
        public void run() {
            try{
                this.msg = searcher.myMsgGetter();
                System.out.println("Comments:" + this.msg);
                comments = searcher.getComment(this.msg);
                /*showID.post(new Runnable() {
                    @Override public void run() {
                        //showMsg.setText("");
                        for(String str : comments)
                            showMsg.setText(showID.getText() +"," + str);
                    }//控件在线程中执行更新，并调用其中的内容
                    //已在主线程中，可以更新UI
                } );*/
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
                /*showID.post(new Runnable() {
                    @Override public void run() {
                        for(String str : recommends)
                            showID.setText(showID.getText() +"," + str);
                    }//控件在线程中执行更新，并调用其中的内容
                    //已在主线程中，可以更新UI
                } );*/


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
