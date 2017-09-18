package com.dyman.zhihudaily.module.news;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.ZhiHuDailyApp;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.database.dao.ReadSchedule;
import com.dyman.zhihudaily.database.tool.TableOperate;
import com.dyman.zhihudaily.entity.NewsDetailInfo;
import com.dyman.zhihudaily.entity.StoryExtraInfo;
import com.dyman.zhihudaily.listener.OnMultiClickListener;
import com.dyman.zhihudaily.module.common.LoginActivity;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.utils.SPUtils;
import com.dyman.zhihudaily.utils.common.CommonUtil;
import com.dyman.zhihudaily.utils.common.DisplayUtil;
import com.dyman.zhihudaily.utils.common.WebUtils;
import com.dyman.zhihudaily.utils.helper.ScrollPullDownHelper;
import com.dyman.zhihudaily.utils.common.ToastUtil;
import com.dyman.zhihudaily.widget.MyImageTextLayout;
import com.dyman.zhihudaily.widget.ScrollWebView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StoryDetailActivity extends BaseActivity implements ViewTreeObserver
        .OnScrollChangedListener, View.OnClickListener{

    private static final String TAG = StoryDetailActivity.class.getSimpleName();
    /** 新闻ID */
    private int newsID;
    /** 新闻背景控件 */
    private MyImageTextLayout imageTextLayout;
    private ScrollView mScrollView;
    /** ScrollView 的子View */
    private View contentView;
    private WebView webView;
    private Toolbar toolbar;
    private TextView markNumTv;
    private TextView commentNumTv;
    /** ScrollView 下滑监听帮助类 */
    private ScrollPullDownHelper mScrollPullDownHelper;
    /** 状态栏高度 */
    private float statusHeight = 0f;
    /** 文章的阅读进度 */
    private double readRatio = 0;

    private NewsDetailInfo newInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initToolbar();
        initData();
        initView();
        loadData(String.valueOf(newsID));
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.story_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        toolbar.setOnTouchListener(new OnMultiClickListener(new OnMultiClickListener.MultiClickCallback() {
            @Override
            public void onDoubleClick(View v, MotionEvent event) {
                ToastUtil.ShortToast("点击了两下");
                Log.i(TAG, "onDoubleClick:    点击了两下");
                mScrollView.smoothScrollTo(0, 0);//   顺滑滚动

            }

            @Override
            public void onTripleClick(View v, MotionEvent event) {
                ToastUtil.ShortToast("点击了三下");
                Log.i(TAG, "onTripleClick:    点击了三下");
            }
        }));
    }


    private void initData() {

        // 初始化数据
        newsID = getIntent().getIntExtra(IntentKeys.NEWS_ID, 0);
        statusHeight = DisplayUtil.getStatusBarHeight(ZhiHuDailyApp.getInstance());
        // 实例化滑动监听帮助类
        mScrollPullDownHelper = new ScrollPullDownHelper();
    }


    private void initView() {

        //  实例化Toolbar部件的监听
        findViewById(R.id.share_iv_status).setOnClickListener(this);
        findViewById(R.id.collect_iv_status).setOnClickListener(this);
        findViewById(R.id.comment_iv_status).setOnClickListener(this);
        findViewById(R.id.mark_iv_status).setOnClickListener(this);
        markNumTv = (TextView) findViewById(R.id.markNum_tv_layout_story_toolbar);
        commentNumTv = (TextView) findViewById(R.id.commentNum_tv_layout_story_toolbar);
        //  头部控件
        imageTextLayout = (MyImageTextLayout) findViewById(R.id.container_header_activity_news_detail);
        imageTextLayout.isHideHeaderMask(false);
        //  初始化 ScrollView 及其滑动监听
        mScrollView = (ScrollView) findViewById(R.id.scrollView_activity_news_detail);
        mScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER); //去掉滑动到底部的蓝色阴影
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(this);
        contentView = mScrollView.getChildAt(0);
        //  初始化网页显示控件
        webView = (WebView) findViewById(R.id.webView_activity_news_detail);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setJavaScriptEnabled(true);// 设置支持JavaScript

    }


    public class DymanWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            if(!webView.getSettings().getLoadsImagesAutomatically()) {
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
        }
    }


    private void loadData(String newsID) {

        if (!CommonUtil.isNetworkAvailable(ZhiHuDailyApp.getInstance())) {
            ToastUtil.ShortToast(getString(R.string.str_network_not_available));
            return;
        }

        Log.i(TAG, "-----------loadData: newID=" + newsID);
        RetrofitHelper.getZhiHuAPI()
                .getNewsDetail(newsID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetailInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "-----加载文章数据完成-----");
                        readRecord();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.ShortToast(getString(R.string.str_date_load_failure));
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(NewsDetailInfo info) {
                        // TODO: update UI
                        newInfo = info;
                        showHtml(info);
                        bindHeaderViewData(info.getTitle(), info.getImage(), info.getImage_source());
                    }
                });

        RetrofitHelper.getZhiHuAPI()
                .getStoryExtra(newsID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoryExtraInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "-------加载文章额外信息完成-------");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.ShortToast(getString(R.string.str_get_story_extra_failure));
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(StoryExtraInfo storyExtraInfo) {
                        markNumTv.setText(String.valueOf(storyExtraInfo.getPopularity()));
                        commentNumTv.setText(String.valueOf(storyExtraInfo.getComments()));
                    }
                });
    }


    /**
     *  绑定头部展示的图片数据
     * @param title
     * @param imageUrl
     * @param imageSource
     */
    private void bindHeaderViewData(String title, String imageUrl, String imageSource) {
        Log.i(TAG, "----------------bindHeaderViewData is called");
        imageTextLayout.setVisibility(View.VISIBLE);
        imageTextLayout.setTitle(title);
        Glide.with(ZhiHuDailyApp.getInstance())
                .load(imageUrl)
                .centerCrop()
                .into(imageTextLayout.getImageView());
        imageTextLayout.setImageSourceInfo(imageSource);
    }


    /**
     *  显示html
     * @param newsDetailInfo
     */
    private void showHtml(NewsDetailInfo newsDetailInfo) {
        Log.i(TAG, "----------------showHtml is called");
        String data = WebUtils.buildHtmlWithCss(newsDetailInfo.getBody(), newsDetailInfo.getCss(), false);
        webView.loadDataWithBaseURL(WebUtils.BASE_URL, data, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);
    }


    /**
     *  获取文章的阅读进度记录, 并提示转跳
     */
    private void readRecord() {

        ReadSchedule readSchedule = TableOperate.getReadSchedule(String.valueOf(newsID));
        if (readSchedule != null) {

            readRatio = Double.parseDouble(readSchedule.getReadRatio());
            if (readRatio >= 0.98) return;

            Log.i(TAG, "setReadRecordTip: -------- 上一次阅读进度为: " + readRatio);
            Snackbar.make(mScrollView, getString(R.string.str_pre_read_schedule)+ readRatio*100+"%", Snackbar.LENGTH_LONG)
                    .setAction("转跳", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            double y = contentView.getMeasuredHeight() * readRatio;
                            int scrollY = (new Double(y)).intValue() - mScrollView.getHeight();
                            mScrollView.smoothScrollTo(0, scrollY);//   顺滑滚动
                            ToastUtil.ShortToast(getString(R.string.str_welcome_back));
                        }
                    }).show();
        } else {
            Log.i(TAG, "initData: -------- 第一次阅读");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_iv_status:

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(newInfo.getTitle()).append(" ")
                        .append("link:").append(newInfo.getShare_url()).append(" ")
                        .append("-分享自dyman的知乎日报");

                Intent it_share = new Intent(Intent.ACTION_SEND);
                it_share.setType("text/plain");
                it_share.putExtra(Intent.EXTRA_TITLE, newInfo.getTitle());
                it_share.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
                startActivity(it_share);
                break;

            case R.id.collect_iv_status:
                startActivity(new Intent(StoryDetailActivity.this, LoginActivity.class));
                break;

            case R.id.comment_iv_status:
                Intent it = new Intent(StoryDetailActivity.this, CommentActivity.class);
                it.putExtra(IntentKeys.NEWS_ID, newsID);
                startActivity(it);
                break;

            case R.id.mark_iv_status:
                startActivity(new Intent(StoryDetailActivity.this, LoginActivity.class));
                break;
        }
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

    @Override
    public void onScrollChanged() {

        changeHeaderViewPosition();

        changeToolbarAlphaAndPosition();

        saveReadSchedule();
    }


    /**
     *  改变 HeaderView 的位置
     */
    private void changeHeaderViewPosition() {

        int scrollY = mScrollView.getScrollY();
        int headerScrollY = (scrollY > 0) ? (scrollY / 2) : 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            imageTextLayout.setScrollY(headerScrollY);
            imageTextLayout.requestLayout();//view 的位置改变,请求重新绘制
        }
    }


    /**
     *  改变 Toolbar 的透明度和位置
     */
    private void changeToolbarAlphaAndPosition() {

        int scrollY = mScrollView.getScrollY();
        int storyHeaderViewHeight = getResources().getDimensionPixelSize(R.dimen.view_header_story_height);
        float toolbarHeight = toolbar.getHeight();
        float contentHeight = storyHeaderViewHeight - toolbarHeight;

        float ratio = Math.min(scrollY / contentHeight, 1.0f);
        toolbar.setAlpha(1-ratio);
        if (scrollY <= contentHeight) {

            toolbar.setY(statusHeight);
            return;
        }

        boolean isPullingDown = mScrollPullDownHelper.onScrollChange(scrollY);
        float toolBarPositionY = isPullingDown ? statusHeight : (contentHeight - scrollY);
        toolbar.setY(toolBarPositionY);
        toolbar.setAlpha(1f);
    }


    /**
     *  记录阅读进度, 在 onPause() 中再保存或更新到数据库
     */
    private void saveReadSchedule() {

        int totalSchedule = contentView.getMeasuredHeight();
        int currSchedule = mScrollView.getScrollY() + mScrollView.getHeight();
        float ratio = (float) currSchedule / totalSchedule;
        double currRatio = ((int)(ratio*1000))/1000.0;
        Log.i(TAG, "saveReadSchedule: ------------ readRatio = "+readRatio);
        if (currRatio > readRatio) readRatio = currRatio;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume is called");
    }


    /**
     *  退出或暂停时对阅读进度进行保存
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause is called");

        ReadSchedule readSchedule = new ReadSchedule();
        readSchedule.setArticleID(String.valueOf(newsID));
        readSchedule.setReadTime(String.valueOf(System.currentTimeMillis()));
        readSchedule.setReadRatio(String.valueOf(readRatio));
        TableOperate.insertReadSchedule(readSchedule);
    }
}
