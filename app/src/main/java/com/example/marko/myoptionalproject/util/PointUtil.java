package com.example.marko.myoptionalproject.util;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.marko.myoptionalproject.MyApplication;
import com.example.marko.myoptionalproject.R;

/**
 * @author Marko
 * @date 2018/4/16
 */

public class PointUtil {

    private RadioGroup group;
    private int preIndex = 0;

    public PointUtil(RadioGroup group) {
        this.group = group;
    }

    public void initRadioButton(int length){
        for (int i = 0;i < length;i++){
            ImageView imageView = new ImageView(MyApplication.getContext());
            imageView.setImageResource(R.drawable.rg_selector);
            // 设置每个按钮之间的间距
            imageView.setPadding(20,0,0,0);
            group.addView(imageView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            //默认选中第一个按钮
            group.getChildAt(0).setEnabled(false);
        }
    }

    /**
     * 设置对应位置按钮的状态
     * @param i 当前位置
     */
    public void setCurrentDot(int i){
        if (group.getChildAt(i) != null){
            // 当前位置选中
            group.getChildAt(i).setEnabled(false);
        }
        if (group.getChildAt(preIndex) != null){
            //上一个按钮取消选中
            group.getChildAt(i-1).setEnabled(true);
            group.getChildAt(i+1).setEnabled(true);
            preIndex = 1;
        }
    }
}
