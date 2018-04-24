package com.example.marko.myoptionalproject.util;

import android.app.Activity;
import android.util.Log;

import com.example.marko.myoptionalproject.model.RecyclerImgResult;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Marko
 * @date 2018/4/21
 */

public class OkHttpUtils {

    private OkHttpClient okHttpClient;
    private static OkHttpUtils okHttpUtils;

    //私有构造方法
    private OkHttpUtils(){

        //创建okHttpClient对象只在创建OkHttpClient对象时创建一次
        okHttpClient = new OkHttpClient();
    }
    //单例设计模式让外部始终获得的是一个OkHttpUtils对象
    public static OkHttpUtils getInstance(){
        //双重判断加上同步锁可以解决线程安全问题
        if (okHttpUtils == null){
            synchronized (OkHttpUtils.class){
                if (okHttpUtils == null){
                    okHttpUtils = new OkHttpUtils();
                    return okHttpUtils;
                }
            }
        }
        return okHttpUtils;
    }


    /**
     * 定义一个callback接口并定义泛型同于接口回调，适用于任意类的返回对象
     * @param <T>
     */
    public interface CallBack<T>{
        void getData(T t);
    }

    /**
     * 创建万能数据请求类
     * @param activity
     * @param url  网址
     * @param tClass 得到数据的类
     * @param callBack
     * @param <T>
     */
    public <T extends Object>void getDataByOk(final Activity activity, String url, final Class<T> tClass,
                                              final CallBack<T> callBack ){
        //创建request请求对象，设置其方式为get
        Request request = new Request.Builder().get().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                //Gson解析json字符串
                final T t = new Gson().fromJson(json,tClass);

                //由于子线程不能更新UI，所以用activity对象调用方法回到主线程
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (t != null){
                            callBack.getData(t);
                        }
                    }
                });
            }
        });
    }

}
