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
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean {
        /**
         * images : ["http://pic4.zhimg.com/17b77253610a43308e6ef1d25977454f.jpg"]
         * type : 0
         * id : 8977328
         * ga_prefix : 111822
         * title : 小事 · 来吧，骗子
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
