package com.example.amicoli.zhihudemo.network;

import com.example.amicoli.zhihudemo.bean.BeforeDailyBean;
import com.example.amicoli.zhihudemo.bean.ContextBean;
import com.example.amicoli.zhihudemo.bean.LongCommentsBean;
import com.example.amicoli.zhihudemo.bean.ShortCommentsBean;
import com.example.amicoli.zhihudemo.bean.StoriesContentBean;
import com.example.amicoli.zhihudemo.bean.StoryExtraBean;


import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/1/19.
 */

public interface GitClient {
    /**
     * 获取文章列表
     * */
    @GET("stories/latest")
    Call<ContextBean> getContextBean();

    /**
     * 获取以前的文章列表
     * */
    @GET("news/before/{date}")
    Call<BeforeDailyBean> getBeforeDailyBean(@Path("date") String date);

    /**
     * 获取对应文章内容
     * */
    @GET("news/{storyId}")
    Observable<StoriesContentBean> getStoriesContentBean(@Path("storyId") int storyId);

    /**
     * 获取点赞数评论数等额外信息
     * */
    @GET("story-extra/{storyId}")
    Observable<StoryExtraBean> getStoryExtraBean(@Path("storyId") int storyId);

    /**
     *获取文章长评论
     * */
    @GET("story/{storyId}/long-comments")
    Call<LongCommentsBean> getLongComments(@Path("storyId") int storyId);

    /**
     *获取文章短评论
     * */
    @GET("story/{storyId}/short-comments")
    Call<ShortCommentsBean> getShortComments(@Path("storyId") int storyId);

    //传入id查看详细信息
    @GET("/api/4/news/{id}")
    Observable<StoriesContentBean> getNewsDetails(@Path("id") int id);
}