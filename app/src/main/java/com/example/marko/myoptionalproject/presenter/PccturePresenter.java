package com.example.marko.myoptionalproject.presenter;

import com.example.marko.myoptionalproject.base.BasePresenter;
import com.example.marko.myoptionalproject.view.PictureView;

/**
 * @author Marko
 * @date 2018/4/20
 */

public class PccturePresenter implements BasePresenter{

    private PictureView pictureView;

    public PccturePresenter(PictureView pictureView) {
        this.pictureView = pictureView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


}
