package com.example.marko.myoptionalproject.view;

import com.example.marko.myoptionalproject.base.BaseView;

import java.util.ArrayList;

/**
 * @author Marko
 * @date 2018/4/20
 */

public interface ImgView extends BaseView{

    void setImgData(ArrayList<String> imgUrls);
    void setImgDataFailure(String message);
}
