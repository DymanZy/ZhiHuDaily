package com.dyman.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.entity.NewsLatestInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by dyman on 2017/2/20.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder>{
    private static final String TAG = HomePageAdapter.class.getSimpleName();

    /** item 的点击监听 */
    private AdapterItemClickListener listener;
    public void setAdapterListener(AdapterItemClickListener listener) {
        this.listener = listener;
    }

    private Context context;
    private List<NewsLatestInfo.StoriesBean> storiesBeanList;


    public HomePageAdapter(Context context) {
        this.context = context;
        storiesBeanList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_home_page_fragment, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(storiesBeanList.get(position).getTitle());
        Glide.with(context)
                .load(storiesBeanList.get(position).getImages().get(0))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return storiesBeanList.size();
    }


    /**
     *  更新 adapter 中的信息
     * @param storiesList
     */
    public void updateAdapter(List<NewsLatestInfo.StoriesBean> storiesList) {
        if (storiesBeanList != null && !storiesList.isEmpty()) {
            storiesBeanList.clear();
        }
        for (int i = 0, len = storiesList.size(); i < len; i++) {
            storiesBeanList.add(storiesList.get(i));
        }
        Log.i(TAG, "updateAdapter: --------storiesBeanList.size = " + storiesBeanList.size());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private TextView title;
        private ImageView image;

        public MyViewHolder(View itemView) {

            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.storyBody_cv_item_home_page);
            title = (TextView) itemView.findViewById(R.id.titleStory_tv_item_home_page);
            image = (ImageView) itemView.findViewById(R.id.imageStory_iv_item_home_page);

            if (listener != null) {
                mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onAdapterItemClick(getLayoutPosition());
                    }
                });
            }
        }
    }
}
