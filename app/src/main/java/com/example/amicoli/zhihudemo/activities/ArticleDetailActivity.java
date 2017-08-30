package com.example.amicoli.zhihudemo.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;


import com.example.amicoli.zhihudemo.R;
import com.example.amicoli.zhihudemo.bean.StoriesContentBean;
import com.example.amicoli.zhihudemo.network.GitClient;
import com.example.amicoli.zhihudemo.utils.HtmlUtils;
import com.example.amicoli.zhihudemo.utils.NetWorkStatus;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class ArticleDetailActivity extends AppCompatActivity {

    private WebView webView;
    private RelativeLayout rl_image_no_net,rl_text_no_net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        initViews();
        initWebView();
    }

    public void initWebView() {
        Intent intent = getIntent();
        String baseUrl="http://news-at.zhihu.com";
        int id=intent.getIntExtra("id",0);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GitClient gitClientApi = retrofit.create(GitClient.class);
        Log.d("gitClientApi====",gitClientApi.toString());
        gitClientApi.getNewsDetails(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<StoriesContentBean, String>() {
                    @Override
                    public String call(StoriesContentBean storiesContentBean ) {
                        return  HtmlUtils.structHtml(storiesContentBean);
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error",e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        //加载asset里的css
                        webView.loadDataWithBaseURL("file:///android_asset/", s, "text/html", "UTF-8", null);
                    }
                });
    }

    private void initViews() {
        rl_image_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);
        rl_text_no_net = (RelativeLayout)findViewById(R.id.ly_text_not_net);
        webView = (WebView) findViewById(R.id.wv_detail_content);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(!NetWorkStatus.CheckNetworkState(this)){
            rl_image_no_net.setVisibility(View.VISIBLE);
            rl_text_no_net.setVisibility(View.VISIBLE);
        }else {
            rl_image_no_net.setVisibility(View.GONE);
            rl_text_no_net.setVisibility(View.GONE);
        }
    }
}
