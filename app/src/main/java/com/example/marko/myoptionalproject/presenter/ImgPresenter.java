package com.example.marko.myoptionalproject.presenter;

import android.util.Log;

import com.example.marko.myoptionalproject.base.BasePresenter;
import com.example.marko.myoptionalproject.base.BaseView;
import com.example.marko.myoptionalproject.model.ImgResult;
import com.example.marko.myoptionalproject.module.NetWork;

import java.util.ArrayList;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Marko
 * @date 2018/4/17
 */

public class ImgPresenter implements BasePresenter {

    private BaseView baseView;
    private Subscription subscription;

    public ImgPresenter(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void subscribe() {
        getBaiDuImg();
    }

    @Override
    public void unSubscribe() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void getBaiDuImg() {
        subscription = NetWork.getBaiduImgApi()
                .getImgData(0, 5, "美女", "全部")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImgResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.setImgDataFailure("图片加载失败");
                        Log.e("TAG", "onError: " + e);
                    }

                    @Override
                    public void onNext(ImgResult imgResult) {
                        if (imgResult != null && imgResult.getData() != null && imgResult.getData().size() > 0) {
                            ArrayList<String> imgUrls = new ArrayList<>();
                            for (ImgResult.DataBean dataBean : imgResult.getData()) {
                                if (dataBean.getImage_url() != null&&!dataBean.getImage_url().isEmpty()) {
                                    imgUrls.add(dataBean.getImage_url());
                                }
                            }
                            baseView.setImgData(imgUrls);
                        } else {
                            baseView.setImgDataFailure("图片加载失败");
                        }
                    }
                });
    }
}
