package com.example.marko.myoptionalproject.module;

import com.example.marko.myoptionalproject.module.api.BaiduImgApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Marko
 * @date 2018/4/17
 */

public class NetWork {

    private static BaiduImgApi baiduImgApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static BaiduImgApi getBaiduImgApi(){
        if (baiduImgApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://image.baidu.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            baiduImgApi = retrofit.create(BaiduImgApi.class);
        }
        return baiduImgApi;
    }
}
