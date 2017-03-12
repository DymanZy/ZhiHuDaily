package com.dyman.zhihudaily.module.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.ZhiHuDailyApp;
import com.dyman.zhihudaily.adapter.MainPageAdapter;
import com.dyman.zhihudaily.base.BaseFragment;
import com.dyman.zhihudaily.entity.NewsLatestInfo;
import com.dyman.zhihudaily.entity.StoryBean;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.network.func.FunctionNewsLatestInfo2Item;
import com.dyman.zhihudaily.utils.common.CommonUtil;
import com.dyman.zhihudaily.utils.common.DateUtil;
import com.dyman.zhihudaily.utils.common.ToastUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dyman on 2017/3/2.
 */

public class MainPageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = MainPageFragment.class.getSimpleName();
    private static final int REFRESH_DATA = 1001;
    private static final int LOAD_MORE_DATA = 1002;
    private static final int LOAD_DATA_TIMEOUT = 1003;
    private boolean IS_MORE_LOADING = false;

    private String title;
    private String mDate;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView mRecyclerView;

    private MainPageAdapter adapter;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_DATA:
                    loadData();
                    break;

                case LOAD_MORE_DATA:
                    loadMoreData();
                    break;

                case LOAD_DATA_TIMEOUT:
                    // 请求超时
                    IS_MORE_LOADING = false;
                    //  TODO： 断开请求

                    break;
            }

        }
    };



    public static MainPageFragment newInstance() {

        return new MainPageFragment();
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_page;
    }


    @Override
    public void finishCreateView(Bundle state) {

        isPrepared = true;
        lazyInit();
    }

    
    @Override
    public void lazyInit() {

        if (!isPrepared) {
            return;
        }

        initView();
        isPrepared = false;
    }


    private void initView() {

        swipeRefreshLayout = (SwipeRefreshLayout) getSupportActivity().findViewById(R.id.swipeRefresh_fragment_main_page);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, R.color.colorAccent,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) getSupportActivity().findViewById(R.id.recycleView_fragment_main_page);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                changeTitle(dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isVisBottom(mRecyclerView) && !IS_MORE_LOADING)
                    handler.sendEmptyMessageDelayed(LOAD_MORE_DATA, 500);
            }
        });

        adapter = new MainPageAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void loadData() {

        if (!CommonUtil.isNetworkAvailable(ZhiHuDailyApp.getInstance())) {
            ToastUtil.ShortToast(getString(R.string.str_network_not_available));
            return;
        }

        Log.i(TAG, "loadData: ---------------获取首页数据");
        RetrofitHelper.getZhiHuAPI()
                .getNewsLatestList()
                .map(new FunctionNewsLatestInfo2Item())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MainPageAdapter.Item>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        ToastUtil.ShortToast(getString(R.string.str_date_load_failure));
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<MainPageAdapter.Item> items) {
                        swipeRefreshLayout.setRefreshing(false);
                        ToastUtil.ShortToast(getString(R.string.str_date_load_success));
                        adapter.updateData(items);
                    }
                });
    }


    /**
     *  加载过往信息
     */
    private void loadMoreData() {

        if (!CommonUtil.isNetworkAvailable(ZhiHuDailyApp.getInstance())) {
            ToastUtil.ShortToast(getString(R.string.str_network_not_available));
            return;
        }

        Log.i(TAG, "loadMoreData: ------------------- 尝试加载更多数据");
        handler.sendEmptyMessageDelayed(LOAD_DATA_TIMEOUT, 8000);
        mDate = adapter.getOldestDate();
        if (mDate == null) {
            Log.i(TAG, "loadMoreData: ----------------------更新时间有误");
            return;
        }
        RetrofitHelper.getZhiHuAPI().getNewsBeforeList(mDate)
             .map(new FunctionNewsLatestInfo2Item())
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(new Observer<List<MainPageAdapter.Item>>() {
                 @Override
                 public void onCompleted() {
                     Log.i(TAG, "onCompleted: ------- 加载更多数据完成 ------");
                     IS_MORE_LOADING = false;
                 }

                 @Override
                 public void onError(Throwable e) {
                     e.printStackTrace();
                     ToastUtil.ShortToast(getString(R.string.str_date_load_failure_check_network));
                     IS_MORE_LOADING = false;
                 }

                 @Override
                 public void onNext(List<MainPageAdapter.Item> items) {
                     adapter.addData(items);
                 }
             });
    }


    private int lastTitlePosition = -1;
    /**
     *  改变ActionBar标题
     * @param dy
     */
    private void changeTitle(int dy) {

        Log.i(TAG, "changeTitle: --------------- dy = " + dy);
        LinearLayoutManager llManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int position = llManager.findFirstVisibleItemPosition();
        if (lastTitlePosition == position) {
            return;
        }
        MainPageAdapter.Item item = adapter.getItem(position);
        if (item.getType() == MainPageAdapter.Type.TYPE_HEADER) {
            title = getString(R.string.app_name);
        } else if (dy > 0 && item.getType() == MainPageAdapter.Type.TYPE_DATE) {
            title = DateUtil.getMainPageDate(item.getTime());
        } else if (dy < 0) {
            title = DateUtil.getMainPageDate(adapter.getPreviousTitle(position));
        }
        ((AppCompatActivity) getSupportActivity()).getSupportActionBar().setTitle(title);
        lastTitlePosition = position;
    }


    /**
     *  判断 RecyclerView 是否滑到了底部
     * @param recyclerView
     * @return
     */
    private boolean isVisBottom(RecyclerView recyclerView) {

        LinearLayoutManager layoutManaget = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItemPosition = layoutManaget.findLastVisibleItemPosition();
        int visibleItemCount = layoutManaget.getChildCount();
        int totalItemCount = layoutManaget.getItemCount();
        int state = recyclerView.getScrollState();

        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1
                && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(REFRESH_DATA);
    }

}
