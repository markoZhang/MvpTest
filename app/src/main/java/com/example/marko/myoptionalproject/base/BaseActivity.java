package com.example.marko.myoptionalproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * @author Marko
 * @date 2018/4/14
 */

public abstract class BaseActivity extends AppCompatActivity{

    /**
     * 获取布局
     * @return
     */
    protected abstract int getContentLayout();

    /**
     * 初始化布局
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentLayout() != 0){
            setContentView(getContentLayout());
            ButterKnife.bind(this);
            initView(savedInstanceState);
            initEvent();
        }
    }
}
