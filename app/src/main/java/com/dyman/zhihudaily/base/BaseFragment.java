package com.dyman.zhihudaily.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dyman on 2017/2/20.
 */

public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private View parentView;

    private FragmentActivity activity;

    // 标志位 标志已经初始化完成
    protected boolean isPrepared;

    public abstract
    @LayoutRes
    int getLayoutResId();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        return parentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Log.i(TAG, "onViewCreated is called");
        super.onViewCreated(view, savedInstanceState);
        finishCreateView(savedInstanceState);
        lazyInit();
        loadData();
    }

    public abstract void finishCreateView(Bundle state);

    public abstract void lazyInit();

    public abstract void loadData();


    public FragmentActivity getSupportActivity() {

        return super.getActivity();
    }


    public android.app.ActionBar getSupportActionBar() {

        return getSupportActivity().getActionBar();
    }


    public Context getApplicationContext() {

        return this.activity == null
                ? (getActivity() == null ? null :
                getActivity().getApplicationContext())
                : this.activity.getApplicationContext();
    }

}
