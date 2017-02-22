package com.dyman.zhihudaily.module.news;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.ZhiHuDailyApp;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.utils.DividerItemDecoration;


public class CommentActivity extends BaseActivity {

    private static final String TAG = CommentActivity.class.getSimpleName();

    private Toolbar toolbar;

    private RecyclerView longCommentRv;

    private RecyclerView shortCommentRv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initToolbar();
        initView();


    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("xx条点评");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initView() {
        longCommentRv = (RecyclerView) findViewById(R.id.longComment_rv_activity_comment);
        longCommentRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        longCommentRv.setItemAnimator(new DefaultItemAnimator());
        longCommentRv.addItemDecoration(new DividerItemDecoration(ZhiHuDailyApp.getInstance(),
                DividerItemDecoration.HORIZONTAL_LIST));

        shortCommentRv = (RecyclerView) findViewById(R.id.shortComment_rv_activity_comment);
        longCommentRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        longCommentRv.setItemAnimator(new DefaultItemAnimator());
        longCommentRv.addItemDecoration(new DividerItemDecoration(ZhiHuDailyApp.getInstance(),
                DividerItemDecoration.HORIZONTAL_LIST));
    }

}
