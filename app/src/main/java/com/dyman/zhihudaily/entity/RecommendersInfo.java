package com.dyman.zhihudaily.entity;

import java.util.List;

/**
 *  新闻推荐者的信息
 *
 * Created by dyman on 2017/2/19.
 */

public class RecommendersInfo {


    /**
     * items : []
     * editors : [{"bio":"树上的女爵","title":"主编","id":79,"avatar":"http://pic1.zhimg.com/0a6456810_m.jpg","name":"刘柯"}]
     * item_count : 1
     */

    private int item_count;
    private List<?> items;
    private List<EditorsBean> editors;

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public List<EditorsBean> getEditors() {
        return editors;
    }

    public void setEditors(List<EditorsBean> editors) {
        this.editors = editors;
    }

    public static class EditorsBean {
        /**
         * bio : 树上的女爵
         * title : 主编
         * id : 79
         * avatar : http://pic1.zhimg.com/0a6456810_m.jpg
         * name : 刘柯
         */

        private String bio;
        private String title;
        private int id;
        private String avatar;
        private String name;

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
