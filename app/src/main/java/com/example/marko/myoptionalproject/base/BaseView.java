package com.example.marko.myoptionalproject.base;

import java.util.ArrayList;

/**
 * @author Marko
 * @date 2018/4/17
 */

public interface BaseView extends View{

    void setImgData(ArrayList<String> imgUrls);
    void setImgDataFailure(String message);
}
