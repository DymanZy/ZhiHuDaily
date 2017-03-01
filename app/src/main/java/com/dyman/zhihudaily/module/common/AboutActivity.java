package com.dyman.zhihudaily.module.common;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.base.BaseActivity;

import us.feras.mdv.MarkdownView;

public class AboutActivity extends BaseActivity {

    private static final String TAG = AboutActivity.class.getSimpleName();

    private MarkdownView markdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initToolbar();
        initView();
    }


    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("关于");
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initView() {

        markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdownFile("file:///android_asset/README.md");
    }



}
