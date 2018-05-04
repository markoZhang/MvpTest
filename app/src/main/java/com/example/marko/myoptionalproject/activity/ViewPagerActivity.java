package com.example.marko.myoptionalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marko.myoptionalproject.R;
import com.example.marko.myoptionalproject.adapter.ViewPagerAdapter;
import com.example.marko.myoptionalproject.base.BaseActivity;
import com.example.marko.myoptionalproject.base.BaseView;
import com.example.marko.myoptionalproject.presenter.ImgPresenter;
import com.example.marko.myoptionalproject.util.PointUtil;
import com.example.marko.myoptionalproject.view.ImgView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Marko
 * @date 2018/4/14
 * 1、ViewPager + RxJava + Retrofit + MVP
 * 2、Handler优化，防止内存泄漏
 */

public class ViewPagerActivity extends BaseActivity implements ImgView{

    private Handler mHandler;
    private int initPosition = 0;
    private int preIndex = 0;
    private final static int WHAT_START_PLAY = 1;
    private PointUtil pointUtil;
    private ImgPresenter imgPresenter;


    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mHandler = new MyHandler(this);
        imgPresenter = new ImgPresenter(this);
        imgPresenter.subscribe();
        Message message = mHandler.obtainMessage(WHAT_START_PLAY, initPosition + 1, 0);
        mHandler.sendMessageDelayed(message, 3000);
    }



    // 静态匿名内部类+弱引用 防止内存泄漏
    static class MyHandler extends Handler{
        WeakReference<ViewPagerActivity> activityWeakReference ;

        private MyHandler(ViewPagerActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final ViewPagerActivity activity = activityWeakReference.get();
            if (activity != null){
                switch (msg.what){
                    // 开始轮播
                    case WHAT_START_PLAY:
                        activity.mViewPager.setCurrentItem(msg.arg1, true);
                        break;
                }
            }
        }
    }

    @Override
    protected void initEvent() {

    }

    private void initViewPager(final ArrayList imageUrls) {
        ArrayList<View> imageViews = new ArrayList<>();
        for (Object url : imageUrls) {
            String urlStr = (String) url;
            // ImageView的初始化必须放在for-each循环内
            ImageView imageView = new ImageView(this);
            Glide.with(this)
                    .load(urlStr)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
            imageViews.add(imageView);
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(imageViews);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setCurrentItem(initPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // 自动播放时的下一页
            private int nextPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                nextPosition = position + 1;
                int count = position%imageUrls.size();
                if (radioGroup.getChildAt(count) != null){
                    radioGroup.getChildAt(count).setEnabled(false);
                }
                if (radioGroup.getChildAt(preIndex) != null){
                    radioGroup.getChildAt(preIndex).setEnabled(true);
                    preIndex = count;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    // viewpager处于拖拽状态,表示用户正在用手滑动.此时禁用掉轮播状态
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        if (mHandler.hasMessages(WHAT_START_PLAY)) {
                            mHandler.removeMessages(WHAT_START_PLAY);
                        }
                        break;
                    // ViewPager已经静止，开启轮播
                    case ViewPager.SCROLL_STATE_IDLE:
                        Message message = mHandler.obtainMessage(WHAT_START_PLAY, nextPosition, 0);
                        mHandler.sendMessageDelayed(message, 3000);
                        break;
                    // viewpager由拖拽状态进入弹性滑动状态
                    case ViewPager.SCROLL_STATE_SETTLING: {
                        break;
                    }
                }
            }
        });
    }


    @Override
    public void setImgData(ArrayList<String> imgUrls) {
        initViewPager(imgUrls);
        pointUtil = new PointUtil(radioGroup);
        pointUtil.initRadioButton(imgUrls.size());
    }

    @Override
    public void setImgDataFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imgPresenter != null){
            imgPresenter.unSubscribe();
        }
        mHandler.removeCallbacksAndMessages(null);
    }


}
