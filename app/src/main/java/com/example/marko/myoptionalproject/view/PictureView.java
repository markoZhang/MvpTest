package com.example.marko.myoptionalproject.view;

import android.graphics.Bitmap;

import com.example.marko.myoptionalproject.base.BaseView;

/**
 * @author Marko
 * @date 2018/4/20
 */

public interface PictureView extends BaseView{

    void savePicture(String url, Bitmap bitmap);
}
