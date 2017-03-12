package com.dyman.zhihudaily.module.common;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.module.section.SectionActivity;
import com.dyman.zhihudaily.utils.SPUtils;
import com.dyman.zhihudaily.utils.common.ToastUtil;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = SectionActivity.class.getSimpleName();

    private CheckBox noImageModeCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initToolbar();
        initView();

    }


    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.str_setting));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initView() {

        findViewById(R.id.clearCache_rl_content_setting).setOnClickListener(this);
        findViewById(R.id.checkVersion_ll_content_setting).setOnClickListener(this);
        findViewById(R.id.feedback_ll_content_setting).setOnClickListener(this);

        noImageModeCb = (CheckBox) findViewById(R.id.noImageMode_cb_content_setting);
        noImageModeCb.setChecked(!SPUtils.isAutoLoadImage());

        noImageModeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.setAutoLoadImage(!isChecked);
            }
        });

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
            case R.id.clearCache_rl_content_setting:
                ToastUtil.ShortToast(getString(R.string.setting_clear_cache));
                break;
            case R.id.checkVersion_ll_content_setting:
                ToastUtil.ShortToast(getString(R.string.str_current_is_latest_version));
                break;
            case R.id.feedback_ll_content_setting:
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                break;
        }
    }
}
