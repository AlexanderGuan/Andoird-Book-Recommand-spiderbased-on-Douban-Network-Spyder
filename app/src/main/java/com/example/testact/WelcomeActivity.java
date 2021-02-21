package com.example.testact;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    private String bookInfo;
    private ArrayList<String> books;
    private SerachPlus searcher = new SerachPlus();
    private String userName;
    private Flag lock = new Flag();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        books = new ArrayList<String>();
        System.out.println("Here is WelcomeActivity!");
        new Get_Books().start();
    }

    class Get_Books extends Thread{
        private ArrayList<String> recommends;
        private String[] keys;
        private String[] values;
        Get_Books(){
            Intent get_Intent = getIntent();
            this.recommends = get_Intent.getStringArrayListExtra("recommends");
            userName = get_Intent.getStringExtra("userName");
        }

        @Override
        public void run() {
            this.keys = new String[]{"Col","Key","Value"};
            this.values = new String[3];
            this.values[0] = "BookInfo";
            this.values[1] = "BookID";
            synchronized (lock){
                try {
                    for(String bookId : this.recommends){
                        this.values[2] = bookId;
                        getBookInfos(this.keys,this.values);
                        lock.wait();
                        if(bookInfo == null){
                            System.out.println("Book is Null!Not Work!");
                            break;
                        }
                        books.add(bookInfo);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

           System.out.println(books.size());
           for(String temp:books){
               System.out.println(temp);
           }
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, BottomBarActivity.class);
            intent.putExtra("recommends", books);
            intent.putExtra("userName", userName);
            startActivity(intent);//启动Activity
        }
    }

    private void getBookInfos(String []keys, String []values){
        new Book_SearchMsg(keys,values).start();
        new Getter_BookInfo().start();
    }

    class Book_SearchMsg extends Thread {
        private String[] keys;
        private String[] values;
        private ArrayList<String> recommends;
        public Book_SearchMsg(String[] keys,String[] values){
            this.keys = keys;
            this.values = values;
        }

        public Book_SearchMsg(){
            Intent get_Intent = getIntent();
            this.recommends = get_Intent.getStringArrayListExtra("recommends");
            this.keys = new String[]{"Col","Key","Value"};
            this.values = new String[3];
            this.values[0] = "BookInfo";
            this.values[1] = "BookID";
            this.values[2] = recommends.get(0);
            recommends.remove(0);
        }
        @Override
        public void run() {
            searcher.info(this.keys,this.values);
        }
    }

    class Getter_BookInfo extends Thread {
        private String msg;

        @Override
        public void run() {
            try{
                synchronized (lock){
                    this.msg = searcher.myMsgGetter();
                    System.out.println("BookInfo:" + this.msg);
                    bookInfo = searcher.getBookinfo(this.msg);
                    System.out.println("In thread:" + bookInfo);
                    lock.notify();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
