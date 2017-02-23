package com.dyman.zhihudaily.widget;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.dyman.zhihudaily.entity.NewsLatestInfo;

/**
 * Created by dyman on 2017/2/20.
 */

public class MyImageTextLayoutHolderView implements Holder<NewsLatestInfo.TopStoriesBean> {

    private static final String TAG = MyImageTextLayoutHolderView.class.getSimpleName();

    private MyImageTextLayout imageTextLayout;

    @Override
    public View createView(Context context) {

        imageTextLayout = new MyImageTextLayout(context);
        return imageTextLayout;
    }


    @Override
    public void UpdateUI(Context context, int position, NewsLatestInfo.TopStoriesBean topStoriesBean) {

        Glide.with(context)
            .load(topStoriesBean.getImage())
            .into(imageTextLayout.getImageView());
        imageTextLayout.setTitle(topStoriesBean.getTitle());
    }
}
