package com.example.marko.myoptionalproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marko.myoptionalproject.R;
import com.example.marko.myoptionalproject.adapter.ViewPagerAdapter;
import com.example.marko.myoptionalproject.base.BaseActivity;
import com.example.marko.myoptionalproject.config.ImageUrl;
import com.example.marko.myoptionalproject.util.PointUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Marko
 * @date 2018/4/14
 */

public class ViewPagerActivity extends BaseActivity {

    private Handler mHandler;
    private int initPosition = 0;
    private int preIndex = 0;
    private final int WHAT_START_PLAY = 1;
    private ArrayList<String> imgUrls;
    private PointUtil pointUtil;
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
        initHandler();
        imgUrls = new ArrayList<>();
        for (String url : ImageUrl.TRANSITION_URLS) {
            imgUrls.add(url);
        }
        initViewPager(imgUrls);
        pointUtil = new PointUtil(radioGroup);
        pointUtil.initRadioButton(imgUrls.size());
        Message message = mHandler.obtainMessage(WHAT_START_PLAY, initPosition + 1, 0);
        mHandler.sendMessageDelayed(message, 3000);
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    // 开始轮播
                    case WHAT_START_PLAY: {
                        mViewPager.setCurrentItem(msg.arg1, true);
                        break;
                    }
                }
            }
        };
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
            Glide.with(this).load(urlStr).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);
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
}
