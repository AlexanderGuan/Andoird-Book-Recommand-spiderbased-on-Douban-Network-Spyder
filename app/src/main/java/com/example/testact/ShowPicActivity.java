package com.example.testact;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowPicActivity extends AppCompatActivity {
    private ArrayList<HashMap> books;
    private HashMap bookInfo;
    private TextView showID;
    private TextView showName;
    private Button btnNext;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        books = new ArrayList<HashMap>();
        showID = (TextView)findViewById(R.id.showBook);
        showName = (TextView)findViewById(R.id.txt_BookId);
        btnNext = (Button)findViewById(R.id.test_login);

    }

    public void OnMyClick(View view){


    }
}
