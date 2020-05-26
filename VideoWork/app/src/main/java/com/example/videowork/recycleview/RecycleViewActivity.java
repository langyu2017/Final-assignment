package com.example.videowork.recycleview;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.videowork.Adapter.MyAdapter;
import com.example.videowork.Common.CallBack;
import com.example.videowork.Common.DoubleClickListener;
import com.example.videowork.Common.FullWindowVideoView;
import com.example.videowork.Common.MyLayoutManager2;
import com.example.videowork.Common.OnViewPagerListener;
import com.example.videowork.MainActivity;
import com.example.videowork.Message.ApiService;
import com.example.videowork.Message.Data;
import com.example.videowork.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class RecycleViewActivity extends Activity {

    RecyclerView rv;
    MyAdapter2 mAdapter;
    RecyclerView.LayoutManager myLayoutManager;
    public List<Data> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        dataList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        mAdapter = new MyAdapter2(this);
        getData(new CallBack() {
            @Override
            public void onSuccess(List<Data> list) {
                Log.d(TAG,"OnSuccess"+list.toString());

                dataList.addAll(list);
                Log.d(TAG,"OnSuccess"+dataList.toString());
                initView();
                //initListener();
            }
        });


        mAdapter.setRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int Position, List<Data> dataList) {
                //具体的操作逻辑
                Log.d("CKL", "count="+Position+"\n");
                Intent intent1 = new Intent();
                intent1.setClass(RecycleViewActivity.this,VideoPlayActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putString("feedurl",dataList.get(Position).feedurl);
                intent1.putExtras(bundleSimple);
                startActivity(intent1);
//                finish();
            }
        });
    }
    @SuppressLint("WrongConstant")
    private void initView(){

        rv = findViewById(R.id.rv);
        myLayoutManager = new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL);
        mAdapter.setData(dataList);
        mAdapter.notifyDataSetChanged();
        rv.setLayoutManager(myLayoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);

    }

    private void getData(final CallBack callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getArticles().enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                Log.d("Here","get。。。"+response.body());
                if (response.body() != null) {
                    Log.d("Here2","get。。。"+response.body());
                    callback.onSuccess(response.body());
                    Log.d("retrofit1",dataList.toString());
                    if (dataList.size() != 0) {
                        //  mAdapter.setData(response.body().articles);  TODO:Adapter
                        //   mAdapter.notifyDataSetChanged();
                    }
                }
                else
                    Log.d("retrofit","NULL");
            }
            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }
    @Override
    protected void onStart() {
        Log.d("teston", "recycle onStart: ");
        super.onStart();
    }
    @Override
    protected void onRestart() {
        Log.d("teston", "recycle onRestart: ");
        super.onRestart();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {//否则返回几次后就崩溃了
        super.onStop();
        Log.d("teston", "recycle  onStop: ");
       // finish();
    }
    @Override
    protected void onDestroy() {//否则返回几次后就崩溃了
        super.onDestroy();
        Log.d("teston", "recycle onDes: ");
    }
}
