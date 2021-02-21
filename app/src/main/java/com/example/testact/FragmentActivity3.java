package com.example.testact;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentActivity3 extends Fragment {

    private TextView tv_text3;
    public FragmentActivity3() {
        // Required empty public constructor
    }

    @Override
    //碎片的生命周期
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_activity3,container,false);
        return view;
    }
    //创建碎片后要立即被触发的事件：
    @Override
    public void onViewCreated(View view,Bundle saveInstanceState)
    {
        super.onViewCreated(view,saveInstanceState);

        tv_text3=view.findViewById(R.id.tv_text3);
    }

}