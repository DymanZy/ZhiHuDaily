package com.dyman.zhihudaily.entity;

/**
 *  新闻的额外信息，如评论数量，所获的『赞』的数量
 *
 * Created by dyman on 2017/2/19.
 */

public class StoryExtraInfo {


    /**
     * long_comments : 2 (长评论总数)
     * popularity : 365 （点赞总数）
     * short_comments : 68 （短评论总数）
     * comments : 70 （评论总数）
     */

    private int long_comments;
    private int popularity;
    private int short_comments;
    private int comments;

    public int getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
