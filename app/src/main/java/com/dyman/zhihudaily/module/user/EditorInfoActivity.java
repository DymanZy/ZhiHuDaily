package com.dyman.zhihudaily.module.user;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.network.auxiliary.ApiConstants;
import com.dyman.zhihudaily.utils.common.WebUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditorInfoActivity extends BaseActivity {

    private static final String TAG = EditorInfoActivity.class.getSimpleName();

    private int editorID;

    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_info);

        initToolbar();
        initView();
        initData();
        loadData();
    }


    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.str_editor_info));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initView() {

        mWebView = (WebView) findViewById(R.id.webView_activity_editor_info);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setJavaScriptEnabled(true);  // 设置支持 JavaScript
    }


    private void initData() {

        editorID = getIntent().getIntExtra(IntentKeys.EDITOR_ID, 0);
    }


    private void loadData() {

        String url = ApiConstants.ZHIHU_BASE_URL + "api/4/editor/"+ editorID + "/profile-page/android";
        mWebView.loadUrl(url);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
