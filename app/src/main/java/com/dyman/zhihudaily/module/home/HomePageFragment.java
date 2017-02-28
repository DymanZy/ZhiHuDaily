package com.dyman.zhihudaily.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.ZhiHuDailyApp;
import com.dyman.zhihudaily.adapter.listener.AdapterItemClickListener;
import com.dyman.zhihudaily.adapter.NewListAdapter;
import com.dyman.zhihudaily.base.BaseFragment;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.entity.NewsLatestInfo;
import com.dyman.zhihudaily.module.news.NewsDetailActivity;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.utils.common.ToastUtil;
import com.dyman.zhihudaily.widget.MyImageTextLayoutHolderView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;

/**
 * Created by dyman on 2017/2/20.
 */

public class HomePageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterItemClickListener,
        OnItemClickListener{

    private static final String TAG = HomePageFragment.class.getSimpleName();

    private static final int HANDLER_WHAT_REFRESH = 1001;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ScrollView scrollView;

    private LinearLayout bodyLl;

    private ConvenientBanner mConvenientBanner;

    private RecyclerView mRecyclerView;

    private NewListAdapter adapter;

    private NewsLatestInfo newsInfo;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_WHAT_REFRESH:
                    // 更新数据
                    loadData();
                    break;
            }
        }
    };


    public static HomePageFragment newInstance() {

        return new HomePageFragment();
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
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

        initLayout();
        isPrepared = false;
    }


    private void initLayout() {

        //  设置下拉更新控件
        mSwipeRefreshLayout = (SwipeRefreshLayout) getSupportActivity().findViewById(R.id.swipeRefresh_fragment_home);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, R.color.colorAccent,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        scrollView = (ScrollView) getSupportActivity().findViewById(R.id.scrollView_fragment_home);
        bodyLl = (LinearLayout) getSupportActivity().findViewById(R.id.linearLayout_fragment_home);
        scrollView.setVisibility(GONE);
        //  设置轮循菜单
        mConvenientBanner = (ConvenientBanner) getSupportActivity().findViewById(R.id.convenientBanner_fragment_home);
        mConvenientBanner.setOnItemClickListener(this);

        //  实例化HomePageAdapter
        adapter = new NewListAdapter(ZhiHuDailyApp.getInstance());
        adapter.setAdapterListener(this);
        //  初始化RecyclerView
        mRecyclerView = (RecyclerView) getSupportActivity().findViewById(R.id.newsLatest_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        mRecyclerView.setAdapter(adapter);
    }


    /**
     *  加载数据
     */
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

                                   mSwipeRefreshLayout.setRefreshing(false);
                                   ToastUtil.ShortToast("数据加载失败");
                                   e.printStackTrace();
                               }

                               @Override
                               public void onNext(NewsLatestInfo newsLatestInfo) {

                                   mSwipeRefreshLayout.setRefreshing(false);
                                   ToastUtil.ShortToast("数据加载成功");
                                   newsInfo = newsLatestInfo;
                                   updateConvenientBanner(newsInfo.getTop_stories());
                                   adapter.updateAdapter(newsInfo.getStories());
                               }
                           }
                );
    }


    /**
     *  更新轮循菜单的消息
     * @param list
     */
    private void updateConvenientBanner(List<NewsLatestInfo.TopStoriesBean> list) {

        mConvenientBanner.setPages(
                new CBViewHolderCreator<MyImageTextLayoutHolderView>() {
                    @Override
                    public MyImageTextLayoutHolderView createHolder() {
                        return new MyImageTextLayoutHolderView();
                    }
                }, list)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        // 开始自动翻页
        mConvenientBanner.startTurning(4000);
    }


    /**
     *  跳转到文章详情页面
     * @param newID
     */
    private void intentToDetail(int newID) {
        Intent it = new Intent(getSupportActivity(), NewsDetailActivity.class);
        it.putExtra(IntentKeys.NEWS_ID, newID);
        getSupportActivity().startActivity(it);
    }


    /**
     *  adapter的item点击响应处理
     * @param position
     */
    @Override
    public void onAdapterItemClick(int position) {
        intentToDetail(newsInfo.getStories().get(position).getId());
    }


    /**
     *  convenientBanner的item点击响应
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        intentToDetail(newsInfo.getTop_stories().get(position).getId());
    }


    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(HANDLER_WHAT_REFRESH, 1000);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        //  停止翻页
        mConvenientBanner.stopTurning();
    }
}
