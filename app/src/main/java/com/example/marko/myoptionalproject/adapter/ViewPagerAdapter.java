package com.example.marko.myoptionalproject.adapter;


import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author Marko
 * @date 2018/4/14
 */

public class ViewPagerAdapter extends PagerAdapter{

    private ArrayList<View> mViewList;


    public ViewPagerAdapter(ArrayList<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {
        //返回一个无穷大的数值
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 生产ViewPager中子view的回调方法
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //设置循环生成子view时的view
        int listPosition = position % mViewList.size();
        if (listPosition < 0){
            listPosition = listPosition + mViewList.size();
        }
       View itemView = mViewList.get(listPosition);
        // 如果该子view上次生成时已经添加了父组件,就remove掉.防止多次添加父组件而抛出IllegalStateException
        ViewGroup viewGroup = (ViewGroup) itemView.getParent();
        if (viewGroup != null){
            viewGroup.removeView(itemView);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeAllViews();
        System.gc();
    }
}
