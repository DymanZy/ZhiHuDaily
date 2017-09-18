package com.dyman.zhihudaily.listener;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dyman on 2017/9/12.
 */

public class OnMultiClickListener implements View.OnTouchListener {

    private final String TAG = this.getClass().getSimpleName();
    private final int interval = 300; // 点击间的时间间隔，单位毫秒
    private long clickTime = 0;
    private int count = 0;
    private MultiClickCallback mCallback;
    private Handler handler;
    private Runnable mRun;


    public OnMultiClickListener(MultiClickCallback callback) {
        super();
        this.mCallback = callback;
        handler = new Handler(Looper.getMainLooper());
    }


    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        Log.i(TAG, "OnMultiClickListener --- onTouch");
        if (MotionEvent.ACTION_UP == event.getAction()) {

            if (clickTime == 0 || System.currentTimeMillis() - clickTime < interval) {
                count++;
            } else {
                // 清零重新开始计算
                count = 1;
            }
            clickTime = System.currentTimeMillis();
            Log.i(TAG, "onTouch: --------- count = "+count);

            if (mRun != null) {
                handler.removeCallbacks(mRun);
                mRun = null;
            }

            mRun = new Runnable() {
                @Override
                public void run() {
                    if (count == 2) {
                        Log.i(TAG, "    执行二连击的回调");
                        mCallback.onDoubleClick(v, event);
                        count = 0;
                    }
                }
            };

            if (count == 3) {
                Log.i(TAG, "onTouch:  执行三连击的回调");
                mCallback.onTripleClick(v, event);
                clickTime = 0;
                handler.removeCallbacks(mRun);
            }

            handler.postDelayed(mRun, 200);
        }
        return true;
    }


    public interface MultiClickCallback {
        void onDoubleClick(View v, MotionEvent event);

        void onTripleClick(View v, MotionEvent event);
    }
}
