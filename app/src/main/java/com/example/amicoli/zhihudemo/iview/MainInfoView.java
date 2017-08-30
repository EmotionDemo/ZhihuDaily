package com.example.amicoli.zhihudemo.iview;

import com.example.amicoli.zhihudemo.bean.BeforeDailyBean;
import com.example.amicoli.zhihudemo.bean.ContextBean;
import com.example.amicoli.zhihudemo.bean.StoriesContentBean;
import com.example.amicoli.zhihudemo.bean.StoryExtraBean;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2017/1/19.
 */

public interface MainInfoView extends MvpView {
    void setContextInfo(ContextBean contextInfo);
    void setBeforeDailyInfo(BeforeDailyBean beforeDailyInfo);
    void setStoriesContentInfo(StoriesContentBean storiesContentInfo);
    void setStoryExtraInfo(StoryExtraBean storyExtraInfo);
}
