package com.dyman.zhihudaily.module.section;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.ZhiHuDailyApp;
import com.dyman.zhihudaily.adapter.SectionTotalAdapter;
import com.dyman.zhihudaily.adapter.listener.AdapterItemClickListener;
import com.dyman.zhihudaily.base.BaseFragment;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.entity.SectionsInfo;
import com.dyman.zhihudaily.network.RetrofitHelper;
import com.dyman.zhihudaily.utils.common.CommonUtil;
import com.dyman.zhihudaily.utils.common.ToastUtil;
import com.dyman.zhihudaily.widget.MyEmptyView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;

/**
 * Created by dyman on 2017/2/25.
 */

public class SectionTotalFragment extends BaseFragment implements AdapterItemClickListener{

    private static final String TAG = SectionTotalFragment.class.getSimpleName();

    private MyEmptyView emptyView;

    private RecyclerView sectionsRv;

    private SectionTotalAdapter adapter;

    public static SectionTotalFragment newInstance() {

        return new SectionTotalFragment();
    }

    @Override
    public int getLayoutResId() {

        return R.layout.fragment_section_list;
    }

    @Override
    public void finishCreateView(Bundle state) {

        isPrepared = true;
    }

    @Override
    public void lazyInit() {

        if (!isPrepared) return;

        initView();
        isPrepared = false;
    }


    private void initView() {

        emptyView = (MyEmptyView) getSupportActivity().findViewById(R.id.emptyView_fragment_section_list);
        sectionsRv = (RecyclerView) getSupportActivity().findViewById(R.id.sectionsList_rv_fragment_section_list);
        sectionsRv.setItemAnimator(new DefaultItemAnimator());
        sectionsRv.setLayoutManager(new LinearLayoutManager(getSupportActivity()));

        adapter = new SectionTotalAdapter(getSupportActivity());
        adapter.setAdapterItemClickListener(this);
        sectionsRv.setAdapter(adapter);
    }


    @Override
    public void loadData() {

        if (!CommonUtil.isNetworkAvailable(ZhiHuDailyApp.getInstance())) {
            ToastUtil.ShortToast(getString(R.string.str_network_not_available));
            return;
        }

        RetrofitHelper.getZhiHuAPI()
                .getSectionList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SectionsInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: -----加载栏目列表信息完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                         e.printStackTrace();
                        emptyView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(SectionsInfo sectionsInfo) {

                        adapter.updateAdapter(sectionsInfo.getData());
                        if (sectionsInfo.getData() == null) {
                            emptyView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onAdapterItemClick(int position) {

        int sectionID = adapter.getItem(position).getId();
        Intent it = new Intent(getSupportActivity(), SectionActivity.class);
        it.putExtra(IntentKeys.SECTION_ID, sectionID);
        getSupportActivity().startActivity(it);
    }

}
