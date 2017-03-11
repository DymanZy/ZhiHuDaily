package com.dyman.zhihudaily.module.theme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.ZhiHuDailyApp;
import com.dyman.zhihudaily.adapter.ThemeAdapter;
import com.dyman.zhihudaily.adapter.listener.AdapterItemClickListener;
import com.dyman.zhihudaily.base.BaseFragment;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.entity.ThemeListInfo;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.utils.common.CommonUtil;
import com.dyman.zhihudaily.utils.common.ToastUtil;
import com.dyman.zhihudaily.widget.MyEmptyView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  主题类型列表
 *
 * Created by dyman on 2017/2/24.
 */

public class ThemeListFragment extends BaseFragment implements AdapterItemClickListener{

    private static final String TAG = ThemeListFragment.class.getSimpleName();

    private MyEmptyView emptyView;

    private RecyclerView themeListRv;

    private ThemeAdapter adapter;


    public static ThemeListFragment newInstance() {

        return new ThemeListFragment();
    }

    @Override
    public int getLayoutResId() {

        return R.layout.fragment_theme_list;
    }

    @Override
    public void finishCreateView(Bundle state) {

        isPrepared = true;
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

        emptyView = (MyEmptyView) getSupportActivity().findViewById(R.id.emptyView_fragment_theme_list);
        themeListRv = (RecyclerView) getSupportActivity().findViewById(R.id.themeList_rv_fragment_theme_list);
        themeListRv.setItemAnimator(new DefaultItemAnimator());
        themeListRv.setLayoutManager(new LinearLayoutManager(getSupportActivity()));

        adapter = new ThemeAdapter(getSupportActivity());
        adapter.setAdapterItemClickListener(this);
        themeListRv.setAdapter(adapter);
    }

    @Override
    public void loadData() {

        if (!CommonUtil.isNetworkAvailable(ZhiHuDailyApp.getInstance())) {
            ToastUtil.ShortToast(getString(R.string.str_network_not_available));
            return;
        }

        RetrofitHelper.getZhiHuAPI()
                .getThemesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThemeListInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: -----加载主题列表完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.ShortToast(getString(R.string.str_date_load_failure));
                        emptyView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(ThemeListInfo themeListInfo) {

                        adapter.updateAdapter(themeListInfo.getOthers());
                        if (themeListInfo.getOthers() == null) {
                            emptyView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     *  点击列表 Item 的监听
     * @param position
     */
    @Override
    public void onAdapterItemClick(int position) {

        int themeID = adapter.getItem(position).getId();
        Intent it = new Intent(getSupportActivity(), ThemeActivity.class);
        it.putExtra(IntentKeys.THEME_ID, themeID);
        getSupportActivity().startActivity(it);
    }
}
