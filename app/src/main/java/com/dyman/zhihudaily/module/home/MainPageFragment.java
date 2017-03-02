package com.dyman.zhihudaily.module.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.adapter.MainPageAdapter;
import com.dyman.zhihudaily.base.BaseFragment;
import com.dyman.zhihudaily.entity.NewsLatestInfo;
import com.dyman.zhihudaily.entity.StoryBean;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.utils.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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

        RetrofitHelper.getZhiHuAPI()
                .getNewsLatestList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsLatestInfo>() {
                               @Override
                               public void onCompleted() {
                                   Log.i(TAG, "onCompleted: load NewsLatestInfo data");
                               }

                               @Override
                               public void onError(Throwable e) {

                                   swipeRefreshLayout.setRefreshing(false);
                                   ToastUtil.ShortToast("数据加载失败");
                                   e.printStackTrace();
                               }

                               @Override
                               public void onNext(NewsLatestInfo newsLatestInfo) {

                                   swipeRefreshLayout.setRefreshing(false);
                                   ToastUtil.ShortToast("数据加载成功");
                                   adapter.updateData(dataToItems(newsLatestInfo));
                                   mDate = newsLatestInfo.getDate();
                               }
                           }
                );
    }


    private void loadMoreData() {

        Log.i(TAG, "loadMoreData: ------------------- 尝试加载更多数据");
        handler.sendEmptyMessageDelayed(LOAD_DATA_TIMEOUT, 8000);
        RetrofitHelper.getZhiHuAPI()
                .getNewsBeforeList(mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsLatestInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ------- 加载更多数据完成 ------");
                        IS_MORE_LOADING = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        IS_MORE_LOADING = false;
                    }

                    @Override
                    public void onNext(NewsLatestInfo info) {
                        ToastUtil.ShortToast("加载更多数据完成");
                        adapter.addData(dataToItems(info));
                        mDate = info.getDate();
                    }
                });
    }


    /**
     *  TODO： 这部分工作怎么移交 map() 解决？？？？
     * @param newsLatestInfo
     * @return
     */
    private List<MainPageAdapter.Item> dataToItems(NewsLatestInfo newsLatestInfo) {

        List<MainPageAdapter.Item> items = new ArrayList<>();

        if (newsLatestInfo.getTop_stories() != null) {
            MainPageAdapter.Item item1 = new MainPageAdapter.Item();
            item1.setTopStoriesList(newsLatestInfo.getTop_stories());
            item1.setType(MainPageAdapter.Type.TYPE_HEADER);
            items.add(item1);
        }

        MainPageAdapter.Item item2 = new MainPageAdapter.Item();
        item2.setTime(newsLatestInfo.getDate());
        item2.setType(MainPageAdapter.Type.TYPE_DATE);
        items.add(item2);

        for (StoryBean story : newsLatestInfo.getStories()) {

            MainPageAdapter.Item item = new MainPageAdapter.Item();
            item.setStory(story);
            item.setType(MainPageAdapter.Type.TYPE_STORY);
            items.add(item);
        }
        return items;
    }


    private int lastTitlePosition = -1;
    /**
     *  改变ActionBar标题
     * @param dy
     */
    private void changeTitle(int dy) {

        LinearLayoutManager llManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int position = llManager.findFirstVisibleItemPosition();
        if (lastTitlePosition == position) {
            return;
        }
        MainPageAdapter.Item item = adapter.getItem(position);
        if (item.getType() == MainPageAdapter.Type.TYPE_HEADER) {
            title = getString(R.string.app_name);
        } else if (dy > 0 && item.getType() == MainPageAdapter.Type.TYPE_DATE) {
            title = item.getTime();
        }
//        getSupportActionBar().setTitle(title);
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
