package com.example.testact;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

public class SerachPlus {
    public String []values = {};//查询值，如数据库Comment，字段名BookID，字段值1002
    public String []keys = {"Col","Key","Value"};//
    private String myMsg;
    private Flag lock;
    private ConnectByArray conn;
    //for BookID Search
    public SerachPlus(){myMsg = "";lock = new Flag();
        conn = new ConnectByArray();}

    //获取Json字符串
    public String info(String []keys,String []values){
        conn.HttpConnectMethod("http://121.199.53.20:8081/Android_Web/Demo2",
                ConnectByArray.DO_GET, keys,values, new ConnectByArray.ConnectCallBack(){
                    @Override
                    public void getConnectMsg(String msg) {
                        synchronized (lock){
                            myMsg =  msg;
                            System.out.println("getConnectMsg:" + msg);
                            lock.notify();
                        }
                    }
                });

        return myMsg;
    }

    public String myMsgGetter() throws InterruptedException {
        synchronized(lock){
            lock.wait();
            return myMsg;
        }
    }
    //获取图书信息
    public String getBookinfo(String msg)throws InterruptedException {
        return msg;
    }
    //获取图书评论
    public ArrayList<String> getComment(String msg) throws InterruptedException, UnsupportedEncodingException {
        JSONObject js = JSONObject.parseObject(msg);
        ArrayList<String> comments = new ArrayList<String>();
        if(js.getJSONArray("Comment").size() == 0){
            comments.add("No Comments");
            return comments;
        }
        for(Object comment : js.getJSONArray("Comment")){
            try{
                System.out.println("SerachPlus:" + URLDecoder.decode(String.valueOf(comment),"utf-8"));
                comments.add(String.valueOf(comment));
            }catch (Exception e){
                e.printStackTrace();
                return comments;
            }
        }
        return comments;
    }
    //获取推荐书籍
    public ArrayList<String> getRecommendList(String msg)throws InterruptedException{

        JSONObject js = JSONObject.parseObject(msg);
        ArrayList<String> recommends = new ArrayList<String>();
        for(Object recommend : js.getJSONArray("Recommend")){
            System.out.println("BookID:" + recommend);
            recommends.add(String.valueOf(recommend));
        }
        return recommends;

        /*synchronized(lock){
            lock.wait();
            JSONObject js = JSONObject.parseObject(msg);
            ArrayList<String> recommends = new ArrayList<String>();
            for(Object recommend : js.getJSONArray("Recommend")){
                recommends.add(String.valueOf(recommend));
            }
            return recommends;
        }*/
    }
}
