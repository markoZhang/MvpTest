package com.example.marko.myoptionalproject.module.api;

import com.example.marko.myoptionalproject.model.ImgResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Marko
 * @date 2018/4/17
 */

public interface BaiduImgApi {

    /**
     * http://image.baidu.com/channel/listjson?pn=0&rn=2&tag1=美女&tag2=全部
     * http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1=美女&tag2=全部&ftags=小清新&ie=utf8
     */
    @GET("channel/listjson")
    Observable<ImgResult> getImgData(@Query("pn") int pn,
                                     @Query("rn") int rn,
                                     @Query("tag1") String tag1,
                                     @Query("tag2") String tag2);
}
