package com.example.testact;
import android.graphics.Bitmap;


public class book_collect1 {
    private String name;
    private String info;
    private String picture;
    public book_collect1(String name,String info,String picture)
    {
        this.name=name;
        this.picture=picture;
        this.info=info;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return  picture;
    }

    public String getInfo() {
        return info;
    }
}
