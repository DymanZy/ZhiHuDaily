package com.dyman.zhihudaily.entity;

import java.util.List;

/**
 *  某专栏之前的新闻信息列表
 *
 * Created by dyman on 2017/2/19.
 */

public class SectionBeforeInfo {


    /**
     * timestamp : 1463958000
     * stories : [{
     *      "images":["http://pic3.zhimg.com/c79c02e0c9570bc5e5d109bc76230d4a.jpg"],
     *      "date":"20160612",
     *      "display_date":"6 月 12 日",
     *      "id":8430626,
     *      "title":"读读日报 24 小时热门 TOP 5 · 一张唱片卖 400 万的「超级怪胎」"},
     *   ......
     * ]
     * name : 读读日报 24 小时热门
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
         * images : ["http://pic3.zhimg.com/c79c02e0c9570bc5e5d109bc76230d4a.jpg"]
         * date : 20160612
         * display_date : 6 月 12 日
         * id : 8430626
         * title : 读读日报 24 小时热门 TOP 5 · 一张唱片卖 400 万的「超级怪胎」
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
