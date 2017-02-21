package com.dyman.zhihudaily.entity;

import java.util.List;

/**
 *  主题日报列表信息
 *
 * Created by dyman on 2017/2/19.
 */

public class ThemeListInfo {


    /**
     * limit : 1000
     * subscribed : []
     * others : [{
     *      "color":15007,
     *      "thumbnail":"http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg",
     *      "description":"了解自己和别人，了解彼此的欲望和局限。",
     *      "id":13,
     *      "name":"日常心理学"},
     *   ......
     * ]
     */

    private int limit;
    private List<?> subscribed;
    private List<OthersBean> others;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<?> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<?> subscribed) {
        this.subscribed = subscribed;
    }

    public List<OthersBean> getOthers() {
        return others;
    }

    public void setOthers(List<OthersBean> others) {
        this.others = others;
    }

    public static class OthersBean {
        /**
         * color : 15007
         * thumbnail : http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg
         * description : 了解自己和别人，了解彼此的欲望和局限。
         * id : 13
         * name : 日常心理学
         */

        private int color;
        private String thumbnail;
        private String description;
        private int id;
        private String name;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
