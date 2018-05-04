package com.example.marko.myoptionalproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import com.example.marko.myoptionalproject.R;

import butterknife.ButterKnife;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Marko
 * @date 2018/4/14
 * 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
 */

public abstract class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate{

    protected BGASwipeBackHelper mSwipeBackHelper;
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
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        if (getContentLayout() != 0){
            setContentView(getContentLayout());
            ButterKnife.bind(this);
            initView(savedInstanceState);
            initEvent();
        }
    }

    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this,this);

        //设置滑动返回是否可用，默认值为true
        mSwipeBackHelper.setSwipeBackEnable(true);
        //设置是否仅仅跟踪左侧边缘的滑动返回，默认值为true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        //设置是否是微信滑动返回样式。默认值为true
        mSwipeBackHelper.setIsWeChatStyle(true);
        //设置阴影资源id。默认值为R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        //设置是否显示滑动返回的阴影效果，默认值为true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        //设置阴影区域的透明度是否根据滑动的距离渐变。默认值为true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        //设置触发释放后自动滑动返回的阈值，默认值为0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        //设置底部导航条是否悬浮在内容上，默认值为false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里再父类中默认返回true来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回false即可
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {

    }

    /**
     * 滑动返回执行完毕，销毁当前activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        //正在滑动返回的时候取消返回按钮时间
        if (mSwipeBackHelper.isSliding()){
            return;
        }
        mSwipeBackHelper.backward();
    }
}
