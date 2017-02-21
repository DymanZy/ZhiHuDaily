package com.dyman.zhihudaily.entity;

import java.util.List;

/**
 *  栏目具体消息
 *
 * Created by dyman on 2017/2/19.
 */

public class SectionDetailInfo {


    /**
     * timestamp : 1463148001
     * stories : [{
     *      "images":["http://pic3.zhimg.com/91125c9aebcab1c84f58ce4f8779551e.jpg"],
     *      "date":"20160601",
     *      "display_date":"6 月 1 日",
     *      "id":8387524,
     *      "title":"深夜惊奇 · 要穿内衣"},
     *   ......
     * ]
     * name : 深夜惊奇
     */

    private int timestamp;
    private String name;
    private List<StoriesBean> stories;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean {
        /**
         * images : ["http://pic3.zhimg.com/91125c9aebcab1c84f58ce4f8779551e.jpg"]
         * date : 20160601
         * display_date : 6 月 1 日
         * id : 8387524
         * title : 深夜惊奇 · 要穿内衣
         * multipic : true
         */

        private String date;
        private String display_date;
        private int id;
        private String title;
        private List<String> images;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDisplay_date() {
            return display_date;
        }

        public void setDisplay_date(String display_date) {
            this.display_date = display_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
