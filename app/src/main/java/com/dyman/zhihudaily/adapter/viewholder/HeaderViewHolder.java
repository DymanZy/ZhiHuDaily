package com.dyman.zhihudaily.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.base.IntentKeys;
import com.dyman.zhihudaily.entity.NewsLatestInfo;
import com.dyman.zhihudaily.module.news.NewsDetailActivity;
import com.dyman.zhihudaily.widget.MyImageTextLayoutHolderView;

import java.util.List;

/**
 * Created by dyman on 2017/3/2.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener{

    private static final String TAG = HeaderViewHolder.class.getSimpleName();
    private ConvenientBanner convenientBanner;

    private List<NewsLatestInfo.TopStoriesBean> list;


    public HeaderViewHolder(View itemView) {
        super(itemView);
        convenientBanner = (ConvenientBanner) itemView.findViewById(R.id.convenientBanner_item_recycler_header_view);
    }


    public void bindHeaderView(List<NewsLatestInfo.TopStoriesBean> topStoriesList) {

        list = topStoriesList;
        convenientBanner.setOnItemClickListener(this);
        convenientBanner.setPages(new CBViewHolderCreator<MyImageTextLayoutHolderView>() {
            @Override
            public MyImageTextLayoutHolderView createHolder() {
                return new MyImageTextLayoutHolderView();
            }
        }, topStoriesList)
                .setPageIndicator(new int[] {R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        convenientBanner.startTurning(4000);
    }


    @Override
    public void onItemClick(int position) {
        // TODO： 转跳文章页面
        Log.i(TAG, "------  转跳文章页面  ------");

        Intent it = new Intent(convenientBanner.getContext(), NewsDetailActivity.class);
        it.putExtra(IntentKeys.NEWS_ID, list.get(position).getId());
        convenientBanner.getContext().startActivity(it);
    }
}
