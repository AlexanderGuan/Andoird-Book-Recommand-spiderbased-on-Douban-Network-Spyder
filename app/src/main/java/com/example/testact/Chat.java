package com.example.testact;

public class Chat {
    private String chatName,chatText,chatTime;
    private int chatImg;
    public Chat(int chatImg, String chatName, String chatText, String chatTime){
        this.chatImg=chatImg;//头像
        this.chatName=chatName;//名字
        this.chatText=chatText;//聊天记录文字
        this.chatTime=chatTime;//时间
    }
    public String getChatTime(){
        return chatTime;
    }
    public String getChatName(){
        return chatName;
    }
    public String getChatText(){
        return chatText;
    }
    public int getChatImg(){
        return chatImg;
    }
}
