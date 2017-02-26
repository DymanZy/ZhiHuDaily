package com.dyman.zhihudaily.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dyman on 2017/2/26.
 */

public class GuideViewPagerAdapter extends PagerAdapter {

    private List<View> views;

    public GuideViewPagerAdapter(List<View> views) {
        super();
        this.views = views;
    }


    @Override
    public int getCount() {

        if (views != null) {
            return views.size();
        }
        return 0;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ((ViewPager) container).removeView(views.get(position));
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }
}
