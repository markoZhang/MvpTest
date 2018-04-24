package com.example.marko.myoptionalproject.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.marko.myoptionalproject.R;
import com.example.marko.myoptionalproject.activity.PictureActivity;
import com.example.marko.myoptionalproject.model.RecyclerImgResult;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * @author Marko
 * @date 2018/4/20
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<RecyclerImgResult.DataBean.WallpaperListInfoBean> list;
    private List<Integer> hights;
    private Context context;
    //用构造方法传入需要的参数
    public RecyclerAdapter(Context context, List<RecyclerImgResult.DataBean.WallpaperListInfoBean> list) {
        this.context = context;
        this.list = list;
//        this.hights = hights;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.recycler_img);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //通过ItemView得到每个图片的params对象
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        //将高度修改为传入的随机高度

//        params.height = hights.get(position);
        //设置修改参数
        holder.itemView.setLayoutParams(params);
        //用Picasso显示图片
        Picasso.with(context).load(list.get(position).getWallPaperMiddle())
                //缓冲期显示的图片
                .placeholder(R.mipmap.default_img)
                //下载失败显示的图片
                .error(R.mipmap.default_img)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                intent.putExtra("extra_data",list.get(holder.getAdapterPosition()).getWallPaperMiddle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
