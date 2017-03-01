package com.dyman.zhihudaily.module.user;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.network.RetrofitHelper;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditorInfoActivity extends BaseActivity {

    private static final String TAG = EditorInfoActivity.class.getSimpleName();

    private int editorID;

    private CircleImageView avatarCiv;

    private TextView nameTv;

    private TextView descriptionTv;

    private TextView zhihuTv;

    private TextView sinaTv;

    private TextView pageTv;

    private TextView emailTv;



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
        toolbar.setTitle("主编资料");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initView() {

        avatarCiv = (CircleImageView) findViewById(R.id.avatar_civ_activity_editor_info);
        nameTv = (TextView) findViewById(R.id.name_tv_activity_editor_info);
        descriptionTv = (TextView) findViewById(R.id.description_tv_activity_editor_info);
        zhihuTv = (TextView) findViewById(R.id.accountZhiHu_tv_activity_editor_info);
        sinaTv = (TextView) findViewById(R.id.accountSina_tv_activity_editor_info);
        pageTv = (TextView) findViewById(R.id.accountPage_tv_activity_editor_info);
        emailTv = (TextView) findViewById(R.id.accountEmail_tv_activity_editor_info);

    }


    private void initData() {

        editorID = getIntent().getIntExtra(IntentKeys.EDITOR_ID, 0);
    }


    private void loadData() {

        RetrofitHelper.getZhiHuAPI()
                .getEditorPage(String.valueOf(editorID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: --- 获取主编信息成功 ---");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(TAG, "onError: --- 获取主编信息失败 ---");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: "+s);
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
}
