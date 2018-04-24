package com.example.marko.myoptionalproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.marko.myoptionalproject.MyApplication;
import com.example.marko.myoptionalproject.R;
import com.example.marko.myoptionalproject.base.BaseActivity;
import com.example.marko.myoptionalproject.view.PictureView;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class PictureActivity extends BaseActivity implements PictureView{


    private Bitmap bitmap = null;

    @BindView(R.id.picture_toolbar)
    Toolbar pictureToolbar;
    @BindView(R.id.picture_app_bar)
    AppBarLayout pictureAppBar;
    @BindView(R.id.photo_view_img)
    PhotoView photoViewImg;
    @BindView(R.id.fab_button)
    FloatingActionButton fabButton;
    @BindView(R.id.picture_progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_picture;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showProgress();
        setSupportActionBar(pictureToolbar);
        pictureToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        Intent intent = getIntent();
        final String imgUrl = intent.getStringExtra("extra_data");
        Log.e("TAG", "initView: " + imgUrl );
        Glide.with(MyApplication.getContext())
                .load(imgUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        hideProgress();
                        bitmap = resource;
                        photoViewImg.setImageBitmap(bitmap);

                    }
                });
    }


    @Override
    protected void initEvent() {

    }


    @OnClick(R.id.fab_button)
    public void onViewClicked() {
        onSaveBitmap(bitmap,this);
        Snackbar.make(this.photoViewImg,"保存成功",Snackbar.LENGTH_SHORT)
                .show();
    }

    @OnClick(R.id.photo_view_img)
    public void onPhotoViewClicked(){
        if (getSupportActionBar() != null){
            if (getSupportActionBar().isShowing()){
                getSupportActionBar().hide();
            }else {
                getSupportActionBar().show();
            }
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void savePicture(String url, Bitmap bitmap) {

    }

    private void onSaveBitmap(Bitmap bitmap, Context context){
        //将Bitmap保存到指定路径下/sdcard/option,文件名以当前系统时间名来命名
        File appDir = new File(Environment.getExternalStorageDirectory(),"option");
        if (!appDir.exists()){
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir,fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if (bitmap != null){
                bitmap.compress(CompressFormat.JPEG,100,fos);
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* //把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(),fileName,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        Uri uri = Uri.fromFile(file);
        //通知图库更新
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri);
        context.sendBroadcast(scannerIntent);
    }
}
