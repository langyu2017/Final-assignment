package com.example.videowork;


import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.videowork.Adapter.MyAdapter;
import com.example.videowork.Common.MyLayoutManager2;
import com.example.videowork.Message.Data;
import com.example.videowork.recycleview.RecycleViewActivity;


import java.util.List;



public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "LANG";
    private List<Data> dataList;
    private RecyclerView rvPage2;
    MyLayoutManager2 myLayoutManager;
    MyAdapter myAdapter ;
    long lastClick = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, FullPageActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, RecycleViewActivity.class));
                break;

        }
    }


}
