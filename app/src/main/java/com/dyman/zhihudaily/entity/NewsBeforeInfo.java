package com.dyman.zhihudaily.entity;

import java.util.List;

/**
 *  过往消息列表
 *
 * Created by dyman on 2017/2/19.
 */

public class NewsBeforeInfo {


    /**
     * date : 20161118
     * stories : [{"images":["http://pic4.zhimg.com/17b77253610a43308e6ef1d25977454f.jpg"],"type":0,"id":8977328,
     * "ga_prefix":"111822","title":"小事 · 来吧，骗子"},......]
     */

    private String date;
    private List<StoryBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }

}
