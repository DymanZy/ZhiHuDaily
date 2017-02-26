package com.dyman.zhihudaily.module.common;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.utils.common.SystemUiVisibilityUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);
        setUpSplash();
    }


    private void setUpSplash() {

        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        finishTask();
                    }
                });
    }


    private void finishTask() {

        startActivity(new Intent(SplashActivity.this, GuideActivity.class));

        SplashActivity.this.finish();
    }


}
