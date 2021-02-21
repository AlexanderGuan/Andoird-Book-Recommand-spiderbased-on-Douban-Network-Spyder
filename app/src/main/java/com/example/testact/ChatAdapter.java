package com.example.testact;

import android.content.Context;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private int resourceID;
    public ChatAdapter(Context context, int textViewResourceID, List<Chat> objects){
        super(context,textViewResourceID,objects);
        resourceID=textViewResourceID;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Chat chat=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.chat_img);//头像ImageView
        TextView textView1=(TextView)view.findViewById(R.id.chat_name);//名字TextView
        TextView textView2=(TextView)view.findViewById(R.id.chat_text);//聊天TextView
        TextView textView3=(TextView)view.findViewById(R.id.chat_time);//时间TextView
        imageView.setImageResource(chat.getChatImg());//获取头像
        textView1.setText(chat.getChatName());//获取名字
        textView2.setText(chat.getChatText());//获取聊天记录
        textView3.setText(chat.getChatTime());//获取时间
        return view;
    }
}

