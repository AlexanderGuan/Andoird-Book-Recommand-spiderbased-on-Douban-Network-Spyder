package com.example.testact;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.example.testact.http.ImageUtils;

import java.util.ArrayList;
import java.util.List;


public class CollectActivity extends AppCompatActivity {
    private List<book_collect1> bookList=new ArrayList<>();
    //-----------改动2------------------------
    private ArrayList<String> collects;
    //-----------改动2------------------------
    private String bookName="";
    private String author="";
    private String bookurl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
        //-----------改动2------------------------
        collects = getIntent().getStringArrayListExtra("collects");
        JSONObject js;
        for(String book:collects){
            System.out.println("CollectActivity_get:" + book);
            js = JSONObject.parseObject(book);
            bookName = js.getString("Title");
            author = js.getString("Author");
            bookurl = js.getString("ImageLink");
            initBooks();
        }
        //-----------改动2------------------------
        bookAdapter arrayAdapter = new bookAdapter(CollectActivity.this,R.layout.book_item,bookList);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(arrayAdapter);
    }

    private void initBooks()
    {
        book_collect1 a1 =new book_collect1(bookName,author,bookurl);
        bookList.add(a1);
    }
}
