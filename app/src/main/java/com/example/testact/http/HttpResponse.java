package com.example.testact.http;

import java.io.Serializable;


public class HttpResponse implements Serializable {

    private boolean isCache;
    //返回的数据
    private String body;
    //请求地址
    private String url;
    //请求的结果code
    private int code;
    //请求参数
    private RequestParams requestParams;
    //文件流异常
    private Exception exception;
    //回调接口
    private OnHttpListener httpListener;

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public String body() {
        return body;
    }

    public void body(String body) {
        this.body = body;
    }

    public String url() {
        return url;
    }

    public void url(String url) {
        this.url = url;
    }

    public Exception exception() {
        return exception;
    }

    public void exception(Exception exception) {
        this.exception = exception;
    }

    public OnHttpListener listener() {
        return httpListener;
    }

    public void listener(OnHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    public RequestParams requestParams() {
        return requestParams;
    }

    public void requestParams(RequestParams requestParams) {
        this.requestParams = requestParams;
    }

    public int code() {
        return code;
    }

    public void code(int code) {
        this.code = code;
    }
}
