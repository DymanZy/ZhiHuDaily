package com.dyman.zhihudaily.module.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.entity.NewsDetailInfo;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.network.api.ZhiHuAppService;
import com.dyman.zhihudaily.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = NewsDetailActivity.class.getSimpleName();

    private int newsID;

    private SwipeRefreshLayout mSwipeRefresh;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initToolbar();
        init();
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("文章详情");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void init() {

        // 初始化数据
        newsID = getIntent().getIntExtra(IntentKeys.NEWS_ID, 0);
        //  初始化下拉刷新控件
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_activity_news_detail);
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, R.color.colorAccent,
                android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(this);
        //  初始化网页显示控件
        webView = (WebView) findViewById(R.id.webView_activity_news_detail);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setJavaScriptEnabled(true);// 设置支持JavaScript

        loadData(String.valueOf(newsID));
    }


    private void loadData(String newsID) {
        RetrofitHelper.getZhiHuAPI()
                .getNewsDetail(newsID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetailInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "-----加载文章数据完成-----");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefresh.setRefreshing(false);
                        ToastUtil.ShortToast("加载数据失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(NewsDetailInfo newsDetailInfo) {
                        mSwipeRefresh.setRefreshing(false);
                        // TODO: update UI
                        showHtml(newsDetailInfo.getBody());
                    }
                });
    }


    private void showHtml(String html) {
        Log.i(TAG, "showHtml: " + html);
        webView.loadDataWithBaseURL(null, html,"text/html", "utf-8", null);
    }


    @Override
    public void onRefresh() {
        loadData(String.valueOf(newsID));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
