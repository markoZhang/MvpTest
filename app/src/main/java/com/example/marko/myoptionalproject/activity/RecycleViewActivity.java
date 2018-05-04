package com.example.marko.myoptionalproject.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.marko.myoptionalproject.R;
import com.example.marko.myoptionalproject.adapter.RecyclerAdapter;
import com.example.marko.myoptionalproject.base.BaseActivity;
import com.example.marko.myoptionalproject.config.ImageUrl;
import com.example.marko.myoptionalproject.model.RecyclerImgResult;
import com.example.marko.myoptionalproject.util.OkHttpUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import butterknife.BindView;

/**
 * @author Marko
 * @date 2018/4/16
 * 1、RecyclerView + OkHttp3
 */
public class RecycleViewActivity extends BaseActivity {

    //获取的json数据中的数据集合
    private List<RecyclerImgResult.DataBean.WallpaperListInfoBean> list = new ArrayList<>();
    //创建一个list集合存储RecyclerView中的图片的高度
    private List<Integer> hights = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_recycle_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //设置RecyclerView要实现的类型为StaggeredGrid瀑布流类型
        //并用构造方法制定列数为3，纵向排列
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,RecyclerView.VERTICAL));
        startTask();
    }

    private void startTask() {
        OkHttpUtils.getInstance().getDataByOk(this, ImageUrl.recyclerUrl, RecyclerImgResult.class,
                new OkHttpUtils.CallBack<RecyclerImgResult>() {
                    @Override
                    public void getData(RecyclerImgResult recyclerImgResult) {
                        if (recyclerImgResult != null){
                            //如果不为空，用本地list接收
                            list.addAll(recyclerImgResult.getData().getWallpaperListInfo());
                            //数据一旦回调成功，初始化数据源和适配器
                            initHights();
                            initAdapter();
                        }
                    }
                });
    }

    private void initAdapter() {
        recyclerAdapter = new RecyclerAdapter(this,list);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

    private void initHights() {
        //设置随机数
        Random random = new Random();
        for (int i = 0;i < list.size();i++){
            hights.add(random.nextInt(200)+200);
        }
    }

    @Override
    protected void initEvent() {

    }


}
