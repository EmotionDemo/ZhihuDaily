package com.example.amicoli.zhihudemo.presenter;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.example.amicoli.zhihudemo.bean.BeforeDailyBean;
import com.example.amicoli.zhihudemo.bean.ContextBean;
import com.example.amicoli.zhihudemo.bean.StoriesContentBean;
import com.example.amicoli.zhihudemo.bean.StoryExtraBean;
import com.example.amicoli.zhihudemo.iview.MainInfoView;
import com.example.amicoli.zhihudemo.network.GitClient;
import com.example.amicoli.zhihudemo.network.GitRetrofit;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Administrator on 2017/1/19.
 */

public class MainPresenter extends MvpBasePresenter<MainInfoView> {
    public void setInfo(){
        final MainInfoView view = getView();
        if (view != null) {
            GitRetrofit retrofit = new GitRetrofit();
            GitClient client = retrofit.getGitClient();
            /**
             * 获取最新列表信息
             * */
            Call<ContextBean> call = client.getContextBean();
                call.enqueue(new Callback<ContextBean>() {
                    @Override
                    public void onResponse(Response<ContextBean> response, Retrofit retrofit) {
                        Log.e("presenter_call_contextbean", "is called");
                        ContextBean contextBean = response.body();
                        view.setContextInfo(contextBean);
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Log.d("message_context_info", t.toString());
                    }

                });
        }else {
            Log.d("view=","null");
        }
    }
    /**
     * 获取历史消息
     * */
    public void setBeforeDaily(String date) {
        final MainInfoView view = getView();
        if (view != null) {
            GitRetrofit retrofit = new GitRetrofit();
            GitClient client = retrofit.getGitClient();
            Call<BeforeDailyBean> call_before = client.getBeforeDailyBean(date);
            call_before.enqueue(new Callback<BeforeDailyBean>() {
                @Override
                public void onResponse(Response<BeforeDailyBean> response, Retrofit retrofit) {
                    Log.e("presenter_call_BeforeDailyBean", "is called");
                    BeforeDailyBean beforeDailyBean = response.body();
                    view.setBeforeDailyInfo(beforeDailyBean);
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.d("message_BeforeDailyBean", t.toString());
                }
            });
        }else {
            Log.d("view=","null");
        }
    }
}

