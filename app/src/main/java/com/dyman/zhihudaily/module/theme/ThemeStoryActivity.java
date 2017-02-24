package com.dyman.zhihudaily.module.theme;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.ZhiHuDailyApp;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.entity.NewsDetailInfo;
import com.dyman.zhihudaily.entity.StoryExtraInfo;
import com.dyman.zhihudaily.entity.ThemeStoryInfo;
import com.dyman.zhihudaily.module.news.CommentActivity;
import com.dyman.zhihudaily.module.news.NewsDetailActivity;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.utils.common.DisplayUtil;
import com.dyman.zhihudaily.utils.common.ToastUtil;
import com.dyman.zhihudaily.utils.common.WebUtils;
import com.dyman.zhihudaily.utils.helper.ScrollPullDownHelper;
import com.dyman.zhihudaily.widget.MyImageTextLayout;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  主题文章内容
 *
 *  @author dyman
 *  @since 2017/2/24 19:51
 */
public class ThemeStoryActivity extends BaseActivity implements View.OnClickListener, ViewTreeObserver.OnScrollChangedListener{

    private static final String TAG = ThemeStoryActivity.class.getSimpleName();

    private int storyID;
    /** 新闻背景控件 */
    private MyImageTextLayout imageTextLayout;
    private ScrollView mScrollView;
    private View maskHeaderView;
    private View maskToolbarView;
    private WebView webView;
    private Toolbar toolbar;
    private TextView markNumTv;
    private TextView commentNumTv;
    /** ScrollView 下滑监听帮助类 */
    private ScrollPullDownHelper mScrollPullDownHelper;
    /** 状态栏高度 */
    private float statusHeight = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_story);

        initToolbar();
        initData();
        initView();
        loadData();
    }


    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.story_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }


    private void initData() {

        storyID = getIntent().getIntExtra(IntentKeys.STORY_ID, 0);
        statusHeight = DisplayUtil.getStatusBarHeight(ZhiHuDailyApp.getInstance());
        // 实例化滑动监听帮助类
        mScrollPullDownHelper = new ScrollPullDownHelper();
    }


    private void initView() {

        //  实例化 Toolbar 部件的监听
        findViewById(R.id.share_iv_status).setOnClickListener(this);
        findViewById(R.id.collect_iv_status).setOnClickListener(this);
        findViewById(R.id.comment_iv_status).setOnClickListener(this);
        findViewById(R.id.mark_iv_status).setOnClickListener(this);
        markNumTv = (TextView) findViewById(R.id.markNum_tv_layout_story_toolbar);
        commentNumTv = (TextView) findViewById(R.id.commentNum_tv_layout_story_toolbar);
        //  头部控件
        imageTextLayout = (MyImageTextLayout) findViewById(R.id.container_header_activity_theme_story);
        imageTextLayout.isHideHeaderMask(false);
        //  初始化 ScrollView 及其滑动监听
        mScrollView = (ScrollView) findViewById(R.id.scrollView_activity_theme_story);
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(this);
        //  掩饰作用的View, 以填补HeaderView显示时的高度
        maskHeaderView = findViewById(R.id.maskHeaderView_activity_theme_story);
        maskToolbarView = findViewById(R.id.maskToolbarView_activity_theme_story);
        //  初始化网页显示控件
        webView = (WebView) findViewById(R.id.webView_activity_theme_story);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setJavaScriptEnabled(true);
    }


    private void loadData() {

        RetrofitHelper.getZhiHuAPI()
                .getThemeStoryInfo(String.valueOf(storyID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThemeStoryInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: -----加载主题文章内容完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ThemeStoryInfo story) {
                        showHtml(story);
                        bindHeaderViewIfHas(story);
                    }
                });

        RetrofitHelper.getZhiHuAPI()
                .getStoryExtra(String.valueOf(storyID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoryExtraInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "-------加载主题文章的额外信息完成-------");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.ShortToast("无法获取文章额外数据");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(StoryExtraInfo storyExtraInfo) {
                        markNumTv.setText(String.valueOf(storyExtraInfo.getPopularity()));
                        commentNumTv.setText(String.valueOf(storyExtraInfo.getComments()));
                    }
                });

    }


    private void showHtml(ThemeStoryInfo story) {
        String data = WebUtils.buildHtmlWithCss(story.getBody(), story.getCss(), false);
        webView.loadDataWithBaseURL(WebUtils.BASE_URL, data, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);
    }


    private void bindHeaderViewIfHas(ThemeStoryInfo story) {
        if (story.hasHeaderImage()) {
            imageTextLayout.setTitle(story.getTitle());
            Glide.with(ZhiHuDailyApp.getInstance())
                    .load(story.getImage())
                    .centerCrop()
                    .into(imageTextLayout.getImageView());
            imageTextLayout.setImageSourceInfo(story.getImage_source());
        } else {
            imageTextLayout.setVisibility(View.GONE);
            maskHeaderView.setVisibility(View.GONE);
            maskToolbarView.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.share_iv_status:
                ToastUtil.ShortToast("点击了分享");
                break;
            case R.id.collect_iv_status:

                break;
            case R.id.comment_iv_status:
                Intent it = new Intent(ThemeStoryActivity.this, CommentActivity.class);
                it.putExtra(IntentKeys.NEWS_ID, storyID);
                startActivity(it);
                break;
            case R.id.mark_iv_status:

                break;
        }
    }


    @Override
    public void onScrollChanged() {

        // TODO: 改变 headerView的位置
        int scrollY = mScrollView.getScrollY();
        int headerScrollY = (scrollY > 0) ? (scrollY / 2) : 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            imageTextLayout.setScrollY(headerScrollY);
            imageTextLayout.requestLayout();
        }


        //  改变 Toolbar 的透明度和位置
        int stroyHeaderViewHeight = getResources().getDimensionPixelSize(R.dimen.view_header_story_height);
        float toolbarHeight = toolbar.getHeight();
        float contentHeight = stroyHeaderViewHeight - toolbarHeight;
        float ratio = Math.min(scrollY / contentHeight, 1.0f);
        toolbar.setAlpha(1 - ratio);
        if (scrollY <= contentHeight) {// 未滑出置顶图片区域, 让toolbar继续显示

            toolbar.setY(statusHeight);
            return;
        }
        //  下滑显示toolbar, 上滑隐藏toolbar
        boolean isPullingDown = mScrollPullDownHelper.onScrollChange(scrollY);
        float toolBarPositionY = isPullingDown ? statusHeight : (contentHeight - scrollY);
        toolbar.setY(toolBarPositionY);
        toolbar.setAlpha(1f);
    }

}
