package com.example.amicoli.zhihudemo.bean;

/**
 * Created by Administrator on 2017/2/26.
 */

public class StoryExtraBean {

    /**
     * long_comments : 0
     * popularity : 161
     * short_comments : 19
     * comments : 19
     */

    private int long_comments;  //长评论总数
    private int popularity;    //点赞总数
    private int short_comments;  // 短评论总数
    private int comments;  //评论总数

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
