package com.dyman.zhihudaily.module.theme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.adapter.NewListAdapter;
import com.dyman.zhihudaily.adapter.listener.AdapterItemClickListener;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.entity.ThemeInfo;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.utils.common.ToastUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  主题内容
 *
 *  @author dyman
 *  @since 2017/2/24 19:49
 */
public class ThemeActivity extends BaseActivity implements AdapterItemClickListener{

    private static final String TAG = ThemeActivity.class.getSimpleName();

    private RecyclerView contentRv;

    private NewListAdapter adapter;



    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        initToolbar();
        initView();
        loadData();
    }


    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initView() {

        contentRv = (RecyclerView) findViewById(R.id.themeContent_rv_activity_theme);
        contentRv.setLayoutManager(new LinearLayoutManager(this));
        contentRv.setItemAnimator(new DefaultItemAnimator());

        adapter = new NewListAdapter(this);
        adapter.setAdapterListener(this);
        contentRv.setAdapter(adapter);

    }


    private void loadData() {

        String themeID =  String.valueOf(getIntent().getIntExtra(IntentKeys.THEME_ID, 0));

        RetrofitHelper.getZhiHuAPI()
                .getThemeInfo(themeID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThemeInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: -----加载主题信息完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ThemeInfo themeInfo) {
                        adapter.updateAdapter(themeInfo.getStories());
                    }
                });
    }


    @Override
    public void onAdapterItemClick(int position) {

        int storyID = adapter.getItem(position).getId();
        Intent it = new Intent(ThemeActivity.this, ThemeStoryActivity.class);
        it.putExtra(IntentKeys.STORY_ID, storyID);
        startActivity(it);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
