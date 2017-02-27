package com.dyman.zhihudaily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 *  可实现滑动监听的 WebView
 *
 * Created by dyman on 2017/2/27.
 */

public class ScrollWebView extends WebView{
    private static final String TAG = ScrollWebView.class.getSimpleName();

    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public OnMyScrollChangeListener listener;
    public void setOnMyScrollChangeListener(OnMyScrollChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float webContent = getContentHeight() * getScale(); // webView的高度
        float webNow = getHeight() + getScrollY();  //  当前 webView 的高度
        Log.i(TAG, "webView.getScrollY() =====>> " + getScrollY());

        if (Math.abs(webContent - webNow) < 1) {
            //  处于底部
            Log.i(TAG, "-- 已经处于底部 --");
            listener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() == 0) {
            Log.i(TAG, "-- 已经处于顶端 --");
            listener.onPageTop(l, t, oldl, oldt);
        } else {
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }


    public interface OnMyScrollChangeListener {
        public void onPageEnd(int l, int t, int oldl, int oldt);
        public void onPageTop(int l, int t, int oldl, int oldt);
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

}
