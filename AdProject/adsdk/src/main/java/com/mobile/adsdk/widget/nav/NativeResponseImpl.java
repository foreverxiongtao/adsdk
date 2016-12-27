package com.mobile.adsdk.widget.nav;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.mobile.adsdk.inf.IEventTrackingListener;
import com.mobile.adsdk.inf.NativeResponse;
import com.mobile.adsdk.utils.AbLogUtil;
import com.mobile.adsdk.utils.AdSdkDownlaoder;
import com.mobile.adsdk.utils.SDKUtils;

import java.util.List;
import java.util.Map;

import ks.AdProto;

/**
 * Created by azkf-XT on 2016/12/22.
 */

class NativeResponseImpl implements NativeResponse {
    private String mTitle;
    private String mDesc;
    private String mIconUrl;
    private String mImageUrl;
    private int mMinPicWidth;
    private int mMinPicHeight;
    private String mBrandName;
    private long mAppSize;
    private String mApppackage;
    private String mHtmlSnippet;
    private AdProto.MaterialMeta.InteractionType mInteractionType;
    private String mClickUrl;
    private List<AdProto.Tracking> mTrackings;
    private Activity mActivity;
    private IEventTrackingListener mListener;
    private AdProto.Ad mAd;

    private NativeResponseImpl(Builder builder) {
        this.mTitle = builder.mTitle;
        this.mDesc = builder.mDesc;
        this.mIconUrl = builder.mIconUrl;
        this.mImageUrl = builder.mImageUrl;
        this.mMinPicWidth = builder.mMinPicWidth;
        this.mMinPicHeight = builder.mMinPicHeight;
        this.mBrandName = builder.mBrandName;
        this.mAppSize = builder.mAppSize;
        this.mApppackage = builder.mApppackage;
        this.mHtmlSnippet = builder.mHtmlSnippet;
        this.mInteractionType = builder.mInteractionType;
        this.mTrackings = builder.mTrackings;
        this.mClickUrl = builder.mClickUrl;
        this.mActivity = builder.mActivity;
        this.mListener = builder.mTrackListener;
        this.mAd = builder.mAd;
    }


    @Override
    public void recordImpression(View var1) {

    }

    @Override
    public void handleClick(View target) {
        AbLogUtil.e("handleClick:", "nav click me");
        if (mListener != null) {
            mListener.trackClickEvent();
        }
        SDKUtils.clickAction(mActivity, mInteractionType, mClickUrl, mApppackage, mAd);
    }

    @Override
    public void handleClick(View var1, int var2) {

    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getDesc() {
        return mDesc;
    }

    @Override
    public String getIconUrl() {
        return mIconUrl;
    }

    @Override
    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    public int getMainPicWidth() {
        return mMinPicWidth;
    }

    @Override
    public int getMainPicHeight() {
        return mMinPicHeight;
    }

    @Override
    public String getBrandName() {
        return mBrandName;
    }


    @Override
    public boolean isDownloadApp() {
        return false;
    }

    @Override
    public boolean isAdAvailable(Context var1) {
        return false;
    }

    @Override
    public long getAppSize() {
        return mAppSize;
    }

    @Override
    public String getAppPackage() {
        return mApppackage;
    }

    @Override
    public List<String> getMultiPicUrls() {
        return null;
    }

    @Override
    public void onStart(Context var1) {

    }

    @Override
    public void onError(Context var1, int var2, int var3) {

    }

    @Override
    public void onComplete(Context var1) {

    }

    @Override
    public void onClose(Context var1, int var2) {

    }

    @Override
    public void onClickAd(Context var1) {

    }

    @Override
    public void onFullScreen(Context var1, int var2) {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public MaterialType getMaterialType() {
        return null;
    }

    @Override
    public String getHtmlSnippet() {
        return mHtmlSnippet;
    }


    public static class Builder {
        private Activity mActivity;
        private String mTitle;
        private String mDesc;
        private String mIconUrl;
        private String mImageUrl;
        private int mMinPicWidth;
        private int mMinPicHeight;
        private String mBrandName;
        private long mAppSize;
        private String mApppackage;
        private String mHtmlSnippet;
        private AdProto.MaterialMeta.InteractionType mInteractionType;
        private String mClickUrl;
        private List<AdProto.Tracking> mTrackings;
        private IEventTrackingListener mTrackListener;
        private AdProto.Ad mAd;

        public Builder() {

        }

        public Builder title(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder desc(String desc) {
            this.mDesc = desc;
            return this;
        }

        public Builder iconurl(String iconurl) {
            this.mIconUrl = iconurl;
            return this;
        }

        public Builder imageurl(String imageUrl) {
            this.mImageUrl = imageUrl;
            return this;
        }

        public Builder minpicwidth(int minPicWidth) {
            this.mMinPicWidth = minPicWidth;
            return this;
        }

        public Builder minpicheight(int minpicHeight) {
            this.mMinPicHeight = minpicHeight;
            return this;
        }

        public Builder brandname(String brandName) {
            this.mBrandName = brandName;
            return this;
        }

        public Builder appSize(long appSize) {
            this.mAppSize = appSize;
            return this;
        }

        public Builder appPackage(String appPackage) {
            this.mApppackage = appPackage;
            return this;
        }

        public Builder htmlSnippet(String htmlSnippet) {
            this.mHtmlSnippet = htmlSnippet;
            return this;
        }

        public Builder interactType(AdProto.MaterialMeta.InteractionType type) {
            this.mInteractionType = type;
            return this;
        }

        public Builder clickUrl(String url) {
            this.mClickUrl = url;
            return this;
        }

        public Builder tranckList(List<AdProto.Tracking> trackings) {
            this.mTrackings = trackings;
            return this;
        }

        public Builder activity(Activity activity) {
            this.mActivity = activity;
            return this;
        }

        public Builder tracklistener(IEventTrackingListener listener) {
            this.mTrackListener = listener;
            return this;
        }

        public NativeResponseImpl build() {
            return new NativeResponseImpl(this);
        }

        public Builder ad(AdProto.Ad ad) {
            this.mAd = ad;
            return this;
        }

    }
}
