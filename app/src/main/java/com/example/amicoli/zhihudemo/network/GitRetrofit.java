package com.example.amicoli.zhihudemo.network;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Administrator on 2017/1/17.
 */

public class GitRetrofit {

    public static final String BASE_URL = "http://news-at.zhihu.com/api/4/";
    private static GitClient gitClient ;
    private static Retrofit retrofit;
    static {
        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public GitClient getGitClient(){
        if(gitClient == null){
            gitClient = retrofit.create(GitClient.class);
        }
        return gitClient;
    }
}
