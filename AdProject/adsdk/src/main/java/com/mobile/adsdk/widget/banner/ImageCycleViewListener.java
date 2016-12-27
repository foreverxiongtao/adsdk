package com.mobile.adsdk.widget.banner;

import android.view.View;
import android.widget.ImageView;

import ks.AdProto;

/**
 * Created by desperado on 2016/2/22.
 * 轮播监听的回调事件
 */
public interface ImageCycleViewListener {
    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */

    /**
     * 加载图片资源
     *
     * @param ad
     * @param imageView
     */
     void displayImage(AdProto.Ad ad, ImageView imageView);

    /**
     * 单击图片事件
     *
     * @param position
     * @param imageView
     */
     void onImageClick(int position, AdProto.Ad Ad, View imageView);
}
