package com.dyman.zhihudaily.entity;

import java.util.List;

/**
 * Created by dyman on 2017/2/24.
 */

public class ThemeStoryInfo {
    /**
     * body : <div class="main-wrap content-wrap">...</div>
     * image_source : Santiago Nicolau / CC BY-SA
     * title : 不许无聊在读读日报里等你哟
     * image : http://pic3.zhimg.com/0e51812e722c154c32d7946d6bd12ec2.jpg
     * share_url : http://daily.zhihu.com/story/7468668
     * js : []
     * theme : {"thumbnail":"http://pic4.zhimg.com/4aa8400ba46d3d46e34a9836744ea232.jpg","id":11,"name":"不许无聊"}
     * ga_prefix : 111823
     * images : ["http://pic1.zhimg.com/e3f596c7ed9e470733f0637adb6124e4.jpg"]
     * type : 0
     * id : 7468668
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private ThemeBean theme;
    private String ga_prefix;
    private int type;
    private int id;
    private List<?> js;
    private List<String> images;
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean hasHeaderImage() {
        return image != null ? true : false;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public ThemeBean getTheme() {
        return theme;
    }

    public void setTheme(ThemeBean theme) {
        this.theme = theme;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

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

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public static class ThemeBean {
        /**
         * thumbnail : http://pic4.zhimg.com/4aa8400ba46d3d46e34a9836744ea232.jpg
         * id : 11
         * name : 不许无聊
         */

        private String thumbnail;
        private int id;
        private String name;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
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


    /**
     * body : <div class="main-wrap content-wrap">......</div>
     * title : 达不到梅西和 C 罗的级别，但他有机会是世界第一
     * recommenders : [{"avatar":"http://pic3.zhimg.com/bbb689a7a_m.jpg"}]
     * share_url : http://daily.zhihu.com/story/7259643
     * js : []
     * theme : {"thumbnail":"http://pic1.zhimg.com/bcf7d594f126e5ceb22691be32b2650a.jpg","id":8,"name":"体育日报"}
     * ga_prefix : 101012
     * images : ["https://pic2.zhimg.com/3d90b8afcc55d07d626ab7f161634899_t.jpg"]
     * type : 0
     * id : 7259643
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */







}
