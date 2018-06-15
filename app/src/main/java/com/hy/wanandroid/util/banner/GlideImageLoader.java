package com.hy.wanandroid.util.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hy.wanandroid.bean.BannerBean;
import com.youth.banner.loader.ImageLoader;

/**
 * author: huyin
 * date: 2018/6/15
 * <p>
 * banner的图片加载类
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        BannerBean bannerBean = (BannerBean) path;
        Glide.with(context).load(bannerBean.getImagePath()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClickListener != null) {
                    onImageClickListener.onImageClick(bannerBean.getUrl());
                }
            }
        });
    }

    public interface OnImageClickListener {
        void onImageClick(String link);
    }

    OnImageClickListener onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }
}
