package com.example.videowork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.videowork.Adapter.MyAdapter;
import com.example.videowork.Common.CallBack;
import com.example.videowork.Common.DoubleClickListener;
import com.example.videowork.Common.FullWindowVideoView;
import com.example.videowork.Common.MyLayoutManager2;
import com.example.videowork.Common.OnViewPagerListener;
import com.example.videowork.Message.ApiService;
import com.example.videowork.Message.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FullPageActivity extends Activity {

    private static final String TAG = "LANG";
    private List<Data> dataList;
    private RecyclerView rvPage2;
    MyLayoutManager2 myLayoutManager;
    MyAdapter myAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dataList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_item);
        myAdapter = new MyAdapter(this);

        getData(new CallBack() {
            @Override
            public void onSuccess(List<Data> list) {
                Log.d(TAG,"OnSuccess");

                Log.d(TAG,"OnSuccess"+list.toString());
                dataList.addAll(list);
                initView();
                initListener();
            }
        });
    }

    private void getData(final CallBack callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getArticles().enqueue(new Callback <List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (response.body() != null) {
                    callback.onSuccess(response.body());
                    Log.d("retrofit1",dataList.toString());
                }
            }
            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void initView(){

        rvPage2 = (RecyclerView) findViewById(R.id.recyclerView);
        myLayoutManager = new MyLayoutManager2(this, OrientationHelper.VERTICAL, false);
        myAdapter.setData(dataList);
        myAdapter.notifyDataSetChanged();
        rvPage2.setLayoutManager(myLayoutManager);
        rvPage2.setAdapter(myAdapter);

    }

    private void initListener(){
        myLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position, boolean bottom) {
                Log.e(TAG, "选择位置:" + position + " 下一页:" + bottom);

                if (position == dataList.size() - 1) {
                    Toast.makeText(getApplicationContext(),  "已经划到底部，无资源加载", Toast.LENGTH_SHORT).show();
                }

                playVideo(0);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void playVideo(int position) {
        View itemView = rvPage2.getChildAt(position);
        final FullWindowVideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        final ImageView imgThumb = itemView.findViewById(R.id.img_fengmian);

        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        videoView.setOnInfoListener(
                new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        mediaPlayer[0] = mp;
                        mp.setLooping(true);
                        imgThumb.animate().alpha(0).setDuration(200).start();
                        return false;
                    }
                });

        videoView.start();
        videoView.setOnClickListener(new DoubleClickListener
                (new DoubleClickListener.DoubleClickCallBack() {
                    @Override
                    public void oneClick() {
                        //Toast.makeText(MainActivity.this, "单击",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void doubleClick() {
                        Toast.makeText(FullPageActivity.this, "已点赞",
                                Toast.LENGTH_SHORT).show();
                    }
                })
        );
        imgPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    imgPlay.animate().alpha(0.7f).start();
                    videoView.pause();
                    Log.d("TAG2", "width:"+videoView.getMeasuredWidth()+"height:"+videoView.getMeasuredHeight());
                    isPlaying = false;

                } else {
                    imgPlay.animate().alpha(0f).start();
                    videoView.start();
                    isPlaying = true;

                }
            }
        });
    }


    private void releaseVideo(int index) {
        View itemView = rvPage2.getChildAt(index);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgThumb = itemView.findViewById(R.id.img_fengmian);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
    }


}
