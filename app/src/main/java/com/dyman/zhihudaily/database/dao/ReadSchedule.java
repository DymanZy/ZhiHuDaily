package com.dyman.zhihudaily.database.dao;

/**
 * Created by dyman on 2017/2/27.
 */

public class ReadSchedule {

    private String articleID;
    private String readTime;
    private String readRatio;


    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public String getReadRatio() {
        return readRatio;
    }

    public void setReadRatio(String readRatio) {
        this.readRatio = readRatio;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }
}
