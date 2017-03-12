package com.dyman.zhihudaily.network.func;


import com.dyman.zhihudaily.adapter.MainPageAdapter;
import com.dyman.zhihudaily.entity.NewsLatestInfo;
import com.dyman.zhihudaily.entity.StoryBean;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by dyman on 2017/3/12.
 */

public class FunctionNewsLatestInfo2Item implements Func1<NewsLatestInfo, List<MainPageAdapter.Item>>{

    @Override
    public List<MainPageAdapter.Item> call(NewsLatestInfo info) {

        List<MainPageAdapter.Item> items = new ArrayList<>();

        if (info.getTop_stories() != null) {
            MainPageAdapter.Item item1 = new MainPageAdapter.Item();
            item1.setTopStoriesList(info.getTop_stories());
            item1.setType(MainPageAdapter.Type.TYPE_HEADER);
            items.add(item1);
        }

        MainPageAdapter.Item item2 = new MainPageAdapter.Item();
        item2.setTime(info.getDate());
        item2.setType(MainPageAdapter.Type.TYPE_DATE);
        items.add(item2);

        for (StoryBean story : info.getStories()) {

            MainPageAdapter.Item item = new MainPageAdapter.Item();
            item.setStory(story);
            item.setType(MainPageAdapter.Type.TYPE_STORY);
            items.add(item);
        }
        return items;
    }


}
