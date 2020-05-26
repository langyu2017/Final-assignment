package com.example.videowork.Common;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;


public class DoubleClickListener implements View.OnClickListener {
    private static int timeout=300;
    private int clickCount = 0;
    private static Handler handler;
    private DoubleClickCallBack doubleClickCallBack;
    public interface DoubleClickCallBack{
        void doubleClick();
        void oneClick();
    }
    public DoubleClickListener(DoubleClickCallBack doubleClickCallBack) {
        this.doubleClickCallBack = doubleClickCallBack;
        handler = new Handler();
    }
    @Override
    public void onClick(View v) {
        clickCount++;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (clickCount == 1) {
                    doubleClickCallBack.oneClick();
                }else if(clickCount==2){
                    doubleClickCallBack.doubleClick();
                }
                handler.removeCallbacksAndMessages(null);
                clickCount = 0;
            }
        },timeout);
    }
}


