package com.dyman.zhihudaily.module.section;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.adapter.NewListAdapter;
import com.dyman.zhihudaily.adapter.SectionListAdapter;
import com.dyman.zhihudaily.adapter.listener.AdapterItemClickListener;
import com.dyman.zhihudaily.base.BaseActivity;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.entity.SectionDetailInfo;
import com.dyman.zhihudaily.module.news.NewsDetailActivity;
import com.dyman.zhihudaily.network.RetrofitHelper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SectionActivity extends BaseActivity implements AdapterItemClickListener{

    private static final String TAG = SectionActivity.class.getSimpleName();

    private RecyclerView sectionContentRv;

    private SectionListAdapter adapter;

    private int sectionID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        initToolbar();
        initView();
        initData();
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

        sectionContentRv = (RecyclerView) findViewById(R.id.sectionContent_rv_activity_section);
        sectionContentRv.setItemAnimator(new DefaultItemAnimator());
        sectionContentRv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SectionListAdapter(this);
        adapter.setAdapterListener(this);
        sectionContentRv.setAdapter(adapter);
    }


    private void initData() {

        sectionID = getIntent().getIntExtra(IntentKeys.SECTION_ID, 0);
    }


    private void loadData() {

        RetrofitHelper.getZhiHuAPI()
                .getSection(String.valueOf(sectionID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SectionDetailInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: -----加载该栏目内容完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                         e.printStackTrace();
                    }

                    @Override
                    public void onNext(SectionDetailInfo sectionDetailInfo) {

                        adapter.updateAdapter(sectionDetailInfo.getStories());
                    }
                });
    }

    @Override
    public void onAdapterItemClick(int position) {

        int newID = adapter.getItem(position).getId();
        Intent it = new Intent(SectionActivity.this, NewsDetailActivity.class);
        it.putExtra(IntentKeys.NEWS_ID, newID);
        startActivity(it);
    }
}
