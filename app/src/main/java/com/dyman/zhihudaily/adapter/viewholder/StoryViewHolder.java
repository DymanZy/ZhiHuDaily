package com.dyman.zhihudaily.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.adapter.listener.AdapterItemClickListener;
import com.dyman.zhihudaily.entity.StoryBean;

import java.util.List;

/**
 * Created by dyman on 2017/3/2.
 */

public class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = StoryViewHolder.class.getSimpleName();

    private Context context;
    private CardView bodyCv;
    private TextView titleTv;
    private ImageView imageView;

    public StoryViewHolder(View itemView) {
        super(itemView);

        this.context = itemView.getContext();

        bodyCv = (CardView) itemView.findViewById(R.id.body_cv_item_recycler_story);
        titleTv = (TextView) itemView.findViewById(R.id.title_tv_item_recycler_story);
        imageView = (ImageView) itemView.findViewById(R.id.image_iv_item_recycler_story);

        bodyCv.setOnClickListener(this);
    }


    public void bindStoryView(StoryBean storyBean) {

        titleTv.setText(storyBean.getTitle());
        if (storyBean.getImages() != null) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(storyBean.getImages().get(0)).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        // TODO: 转跳到故事界面
        Log.i(TAG, "---------------------------");
        Log.i(TAG, "------  转跳文章页面  ------");
        Log.i(TAG, "---------------------------");
    }
}
