package com.example.videowork.recycleview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.videowork.Common.DoubleClickListener;
import com.example.videowork.R;

public class VideoPlayActivity extends Activity {
    VideoView mVV ;
    ImageView imgPlay;
    boolean isPlay;
    //MediaController mMediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Bundle bundle = this.getIntent().getExtras();
        String feed = bundle.getString("feedurl");
        Log.d("Videoplayer", "onCreate: "+feed);

        mVV = findViewById(R.id.videoView);
        imgPlay = findViewById(R.id.img_play);

        if(feed!=null)
            mVV.setVideoURI(Uri.parse(feed));
        else
            //mVV.setVideoPath(getVideoPath(R.raw.bytedance));
            mVV.setVideoURI(Uri.parse("http://jzvd.nathen.cn/video/2f03c005-170bac9abac-0007-1823-c86-de200.mp4"));

//        mMediaController = new MediaController(mVV.getContext());
//        mVV.setMediaController(mMediaController);
//        mMediaController.setMediaPlayer(mVV);

        mVV.seekTo(0);
        mVV.start();
        isPlay = true;
        mVV.setOnClickListener(new DoubleClickListener
                (new DoubleClickListener.DoubleClickCallBack() {
                    @Override
                    public void oneClick() {
                        //Toast.makeText(VideoPlayActivity.this, "单击", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void doubleClick() {
                        Toast.makeText(VideoPlayActivity.this, "已点赞",
                                Toast.LENGTH_SHORT).show();
                    }
                })
        );
        imgPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;
            @Override
            public void onClick(View v) {
                if (mVV.isPlaying()) {
                    imgPlay.animate().alpha(0.7f).start();
                    mVV.pause();
                    Log.d("TAG2", "width:"+mVV.getMeasuredWidth()+"height:"+mVV.getMeasuredHeight());
                    isPlaying = false;

                } else {
                    imgPlay.animate().alpha(0f).start();
                    mVV.start();
                    isPlaying = true;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        Log.d("teston", "player onPause ");
        super.onPause();
    }
    @Override
    protected void onStop() {//否则返回几次后就崩溃了
        Log.d("teston", "player onstop: ");
        super.onStop();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("teston", "onKeyDown");
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            onBackPressed();
//        }
        return super.onKeyDown(keyCode, event);
    }
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        mVV.pause();
//        Log.d("teston", "dispatchKeyEvent player pause");
//        //拦截返回键
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
//            Log.d("teston", "dispatchKeyEvent player onBackPressed: KEYCODE_BACK");
//            //判断触摸UP事件才会进行返回事件处理
//            //if (event.getAction() == KeyEvent.ACTION_UP) {
//
//                Log.d("teston", "dispatchKeyEvent player onBackPressed: ACTION_UP");
//                onBackPressed();
//            //}
//            //只要是返回事件，直接返回true，表示消费掉
//            return false;
//        }
//        return super.dispatchKeyEvent(event);
//    }
//    @Override
//    public void onBackPressed() {
//        Log.d("teston", "player onBackPressed: ");
//        super.onBackPressed();
//        //startActivity(new Intent(this, RecycleViewActivity.class));
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("teston", "player onDes: ");
    }
}