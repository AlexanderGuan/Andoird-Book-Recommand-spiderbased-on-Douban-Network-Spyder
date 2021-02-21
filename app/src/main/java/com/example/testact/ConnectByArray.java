package com.example.testact;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.net.URLEncoder;

public class ConnectByArray {

    public static final String DO_GET = "GET";
    public static final String DO_POST = "POST";
    public String msg = "Null";
    public String httpurl = "Null";

    //回调接口
    interface ConnectCallBack{
        public void getConnectMsg(String msg);
    }

    //注册接口
    public ConnectCallBack mCallBack;
    public void setConnectCallBack(ConnectCallBack hCallBack){
        mCallBack = hCallBack;
    }
    /*不带参数的带参数注册接口*/
    public void HttpConnectMethod(String url, final String MethodType, ConnectCallBack hCallBack){
        HttpConnectMethod(url,MethodType,null,null,hCallBack);
    }

    /*带参数的 对HttpClient请求网络的一个封装*/
    public void HttpConnectMethod(String url, final String MethodType, final String []keys,final String []values, ConnectCallBack hCallBack){
        mCallBack = hCallBack;
        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {    //String... 是个可变参数
                System.out.println("Enter...");
                return connectAndGetMessage(params[0],MethodType,keys,values);
            }

            protected void onPostExecute(String result) {
                mCallBack.getConnectMsg(result); //回调将结果传回去
            };
        }.execute(url);
    }

    /*连接网络取数据*/
    public String connectAndGetMessage(String url, String MethodType, String []keys,String []values){
        HttpClient client = new DefaultHttpClient();
        HttpUriRequest request = getRequest(url,MethodType,keys,values);
        System.out.println("Enter2...");
        String msg = null;
        if(request != null){
            try {
                System.out.println("Break...");
                HttpResponse response = client.execute(request);
                if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){   //网络请求是否成功
                    msg = EntityUtils.toString(response.getEntity(),"utf-8");   //得到响应数据
                    this.msg = msg;
                    System.out.println("msg:" + msg);
                    System.out.println("this:" + this.msg);

                }else{
                    msg = "网络出错";
                    this.msg = msg;
                    return null;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Request is null!");
        }
        return msg;
    }

    /*将获取request的代码封装成方法*/
    public HttpUriRequest getRequest(String url, String MethodType,String []keys,String []values){
        String httpUrl = url;

        try{
            if(MethodType.equalsIgnoreCase(ConnectByArray.DO_GET)){   //get请求
                if(values != null){  //参数不为空。
                    System.out.println("Enter3...");
                    httpUrl = httpUrl + "?";
                    StringBuilder sb = new StringBuilder(httpUrl);
                    for(int i = 0;i < keys.length;i++){
                        String value = URLEncoder.encode(values[i], "utf-8");
                        System.out.println(values);
                        sb.append(keys[i]).append("=").append(value).append("&");
                        //122.199.53.20:8081/Android_Web/demo2?username=xxx&password=xxx&  就是多出一个&
                    }
                    httpUrl = sb.substring(0, sb.length()-1);
                }
                System.out.println(httpUrl);
                HttpGet getRequest = new HttpGet(httpUrl);
                this.httpurl = httpUrl;
                return getRequest;
            }else if(MethodType.equalsIgnoreCase(ConnectByArray.DO_POST)){    //post请求
                HttpPost postRequst = new HttpPost(httpUrl);
                if(keys != null){  //带参数的
                    ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
                    for(int i = 0;i < keys.length;i++){
                        String value = URLEncoder.encode(values[i], "utf-8");
                        BasicNameValuePair Pair = new BasicNameValuePair(keys[i], value);
                        pairs.add(Pair);
                    }
                    try {
                        HttpEntity entity = new UrlEncodedFormEntity(pairs, "UTF-8");//或者改成服务器的编码方式
                        postRequst.setEntity(entity);  //向请求中加载参数
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return postRequst;
            }
        }catch (Exception e){

        }

        return null;
    }
}
