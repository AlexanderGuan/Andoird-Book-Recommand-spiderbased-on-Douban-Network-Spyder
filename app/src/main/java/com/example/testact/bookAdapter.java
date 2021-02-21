package com.example.testact;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import com.loopj.android.image.SmartImageView;

public class bookAdapter extends ArrayAdapter<book_collect1> {
    private int resourceID;
    public bookAdapter(Context context, int textViewResourceID, List<book_collect1> objects)
    {
        super(context,textViewResourceID,objects);
        resourceID=textViewResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        book_collect1 book=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        //-----------改动2------------------------
        SmartImageView bookImage=(SmartImageView)view.findViewById(R.id.book_image);
        //-----------改动2------------------------

        TextView bookName =(TextView)view.findViewById(R.id.bookname);
        TextView bookInfo =(TextView)view.findViewById(R.id.bookinfomation);
        //-----------改动2------------------------
        bookName.setText(book.getName());
        bookInfo.setText(book.getInfo());
        bookImage.setImageUrl(book.getPicture(),R.mipmap.waiting,R.mipmap.bg1);
        //-----------改动2------------------------
        return view;
    }


}

