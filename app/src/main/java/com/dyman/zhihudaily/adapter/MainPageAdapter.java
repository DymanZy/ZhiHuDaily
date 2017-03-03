package com.dyman.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyman.zhihudaily.R;
import com.dyman.zhihudaily.adapter.viewholder.DateViewHolder;
import com.dyman.zhihudaily.adapter.viewholder.HeaderViewHolder;
import com.dyman.zhihudaily.adapter.viewholder.StoryViewHolder;
import com.dyman.zhihudaily.entity.NewsLatestInfo;
import com.dyman.zhihudaily.entity.StoryBean;
import com.dyman.zhihudaily.module.home.MainPageFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dyman on 2017/3/2.
 */

public class MainPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = MainPageAdapter.class.getSimpleName();

    private Context context;
    private List<Item> datas;

    public static class Type {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_DATE = 1;
        public static final int TYPE_STORY = 2;
    }


    public MainPageAdapter(Context context) {

        this.context = context;
        datas = new ArrayList<>();
    }


    /**
     *  刷新时调用
     * @param items
     */
    public void updateData(List<Item> items) {
        datas.clear();
        for (Item item : items) {
            datas.add(item);
        }
        notifyDataSetChanged();
    }


    /**
     *  加载更多时调用
     * @param items
     */
    public void addData(List<Item> items) {

        int positionStart = datas.size();
        int itemCount = items.size();

        for (Item item : items) {
            datas.add(item);
        }
        notifyItemRangeChanged(positionStart, itemCount);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case Type.TYPE_HEADER:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_header_view, parent, false);
                return new HeaderViewHolder(itemView);
            case Type.TYPE_DATE:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_date, parent, false);
                return  new DateViewHolder(itemView);
            case Type.TYPE_STORY:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_story, parent, false);
                return new StoryViewHolder(itemView);
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);
        Item item = getItem(position);
        switch (viewType) {
            case Type.TYPE_HEADER:
                ((HeaderViewHolder) holder).bindHeaderView(item.getTopStoriesList());
                break;
            case Type.TYPE_DATE:
                ((DateViewHolder) holder).bindDate(item.getTime());
                break;
            case Type.TYPE_STORY:
                ((StoryViewHolder) holder).bindStoryView(item.getStory());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    public Item getItem(int position) {
        return datas.get(position);
    }


    /**
     *  获取该节点前得时间标题
     * @param position
     * @return
     */
    public String getPreviousTitle(int position) {
        List<Item> items = new ArrayList<>();
        items.addAll(datas.subList(0, position + 1));
        Collections.reverse(items);
        for (Item item : items) {
            if (item.getType() == Type.TYPE_DATE) {
                return item.getTime();
            }
        }
        return "";
    }


    public static class Item {

        private int type;
        private List<NewsLatestInfo.TopStoriesBean> topStoriesList;
        private String time;
        private StoryBean story;

        public StoryBean getStory() {
            return story;
        }

        public void setStory(StoryBean story) {
            this.story = story;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<NewsLatestInfo.TopStoriesBean> getTopStoriesList() {
            return topStoriesList;
        }

        public void setTopStoriesList(List<NewsLatestInfo.TopStoriesBean> topStoriesList) {
            this.topStoriesList = topStoriesList;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
