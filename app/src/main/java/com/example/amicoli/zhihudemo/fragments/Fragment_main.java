package com.example.amicoli.zhihudemo.fragments;



import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.example.amicoli.zhihudemo.R;
import com.example.amicoli.zhihudemo.activities.ArticleDetailActivity;
import com.example.amicoli.zhihudemo.adapter.StoriesAdapter;
import com.example.amicoli.zhihudemo.bean.BeforeDailyBean;
import com.example.amicoli.zhihudemo.bean.ContextBean;
import com.example.amicoli.zhihudemo.bean.StoriesBean;
import com.example.amicoli.zhihudemo.bean.StoriesContentBean;
import com.example.amicoli.zhihudemo.bean.StoryExtraBean;
import com.example.amicoli.zhihudemo.bean.TopStoriesBean;

import com.example.amicoli.zhihudemo.iview.MainInfoView;
import com.example.amicoli.zhihudemo.presenter.MainPresenter;
import com.example.amicoli.zhihudemo.utils.GlideImageLoader;
import com.example.amicoli.zhihudemo.utils.NetWorkStatus;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;


import es.dmoral.toasty.Toasty;


public class Fragment_main extends MvpFragment<MainInfoView,MainPresenter> implements
        MainInfoView,SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    private StoriesAdapter storiesadapter;
    private List<StoriesContentBean> storiesContentBean;
    private List<StoriesBean> storiesBean ;
    private ContextBean contextBeen;
    private List<TopStoriesBean> topStoriesBeen;
    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> titles = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private boolean isRefreshing = true;
    private TextView today_date,before_date;
    private String now_date;
    private View header;
    private View footer;
    private Handler mHandler;
    private RelativeLayout ry_image_no_net,ry_text_no_net;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onViewCreated(View view,Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);
        initSwipeRefreshLayout();
        getPresenter().setInfo();
        initView();
        initGridLayout(storiesBean);
        checkNetWork();
    }
    public void initView(){
        header = getActivity().getLayoutInflater().inflate(R.layout.header,null);
        footer = getActivity().getLayoutInflater().inflate(R.layout.footer,null);
        today_date = (TextView) getActivity().findViewById(R.id.today_date);
        before_date = (TextView)getActivity().findViewById(R.id.date_before);
        ry_image_no_net = (RelativeLayout) getActivity().findViewById(R.id.rl_no_net);
        ry_text_no_net = (RelativeLayout)getActivity().findViewById(R.id.ly_text_not_net);
        if(!NetWorkStatus.CheckNetworkState(getContext())){
            ry_image_no_net.setVisibility(View.VISIBLE);
            ry_text_no_net.setVisibility(View.VISIBLE);
        }else {
            ry_image_no_net.setVisibility(View.GONE);
            ry_text_no_net.setVisibility(View.GONE);
        }
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_widget);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light
        );
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!NetWorkStatus.CheckNetworkState(getContext())){
                    Toasty.error(getContext(), "刷新失败，请检查网络设置", Toast.LENGTH_SHORT, true).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    Log.v("network", "network free.");
                    getPresenter().setInfo();
                    swipeRefreshLayout.setRefreshing(false);
                    initView();
                    Toasty.success(getContext(),"刷新成功",Toast.LENGTH_SHORT,true).show();
                }
            }
        });
    }

    /**检测登录状态*/
    public void checkNetWork(){
        if(!NetWorkStatus.CheckNetworkState(getContext())){
            Toasty.error(getContext(), "网络连接失败，请检查网络设置", Toast.LENGTH_SHORT, true).show();
        }else {
            Toasty.success(getContext(),"获取成功",Toast.LENGTH_SHORT,true).show();
        }
    }
    private void initGridLayout(List<StoriesBean> storiesBean) {
        /**
         * 重写GridLayoutManager解决卡顿现象
         * */
         gridLayoutManager = new GridLayoutManager(this.getActivity(),3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return storiesadapter.isHeader(position)? gridLayoutManager
                        .getSpanCount():(storiesadapter.isFooter(position)?gridLayoutManager.getSpanCount():1);
            }
        });
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler);
        storiesadapter = new StoriesAdapter(header, footer, getContext(),storiesBean,gridLayoutManager,contextBeen);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setAdapter(storiesadapter);
        boolean isBottom = gridLayoutManager.findLastCompletelyVisibleItemPosition()>=storiesadapter.getItemCount()-1;
        if (isBottom){

        }
    }

    private void initBanner(List<String> images, List<String> titles,final List<TopStoriesBean> topStoriesBeen) {
        Banner banner = (Banner) getActivity().findViewById(R.id.my_banner);
        banner.setImageLoader(new GlideImageLoader());//设置图片加载器
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);//设置banner翻页样式
        banner.setImages(this.images);//设置图片集合
        banner.setBannerAnimation(Transformer.Default);//设置动画效果
        banner.setBannerTitles(this.titles);
        banner.setDelayTime(3000);
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                intent.putExtra("id",topStoriesBeen.get(position-1).getId());
                startActivity(intent);
            }
        });
        /**
         * 防止viewpager与下拉刷新事件冲突
         * */
        banner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        banner.start();
    }

    @Override
    public void setContextInfo(ContextBean contextInfo) {
        now_date = contextInfo.getDate();
        for (int i=0;i<5;i++){
            images.add(contextInfo.getTop_stories().get(i).getImage());
            titles.add(contextInfo.getTop_stories().get(i).getTitle());
        }
        getPresenter().setBeforeDaily(now_date);
        initBanner(images,titles,contextInfo.getTop_stories());
        storiesBean = contextInfo.getStories();
        contextBeen = contextInfo;
        initSwipeRefreshLayout();
        initGridLayout(storiesBean);
        Log.e("now_time",now_date);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setBeforeDailyInfo(final BeforeDailyBean beforeDailyInfo) {
//        mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg){
//                super.handleMessage(msg);
//            }
//        };
//        storiesadapter.setLoadMore(true);
//        storiesadapter.storiesBeen.clear();
//        storiesadapter.storiesBeen.addAll(beforeDailyInfo.getStories());
//        storiesadapter.addOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void loadMore() {
//                storiesadapter.storiesBeen.add(null);
//                storiesadapter.notifyItemInserted(storiesadapter.storiesBeen.size()-1);
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        storiesadapter.storiesBeen.remove(storiesadapter.storiesBeen.size()-1);
//                        storiesadapter.notifyItemRemoved(storiesadapter.storiesBeen.size());
//                        storiesadapter.storiesBeen.add(beforeDailyInfo.getStories().get(1));
//                        storiesadapter.notifyItemInserted(storiesadapter.storiesBeen.size());
//                        storiesadapter.setLoaded();
//                    }
//                },2*1000);
//            }
//        });
        Log.e("now_date=======?",beforeDailyInfo.getDate());
    }

    @Override
    public void setStoriesContentInfo(StoriesContentBean storiesContentInfo) {

    }

    @Override
    public void setStoryExtraInfo(StoryExtraBean storyExtraInfo) {

    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onRefresh() {
    }

}
