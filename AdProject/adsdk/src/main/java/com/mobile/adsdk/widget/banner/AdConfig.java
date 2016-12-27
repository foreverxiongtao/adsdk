package com.mobile.adsdk.widget.banner;

import com.mobile.adsdk.global.LocaalConstant;

public class AdConfig {
    public int mWidth;
    public int mHeigt;
    public String mAdId;
    public String mAppId;
    public int mAdType;

    private AdConfig(Builder builder) {
        this.mAdId = builder.mAdId;
        this.mHeigt = builder.mHeigt;
        this.mWidth = builder.mWidth;
        this.mAppId = builder.mAppId;
        this.mAdType = builder.mAdType;
    }

    public static class Builder {
        private int mWidth = LocaalConstant.BANNER_DEFAULT_WIDTH;
        private int mHeigt = LocaalConstant.BANNER_DEFAULT_HEIGHT;
        private String mAdId;
        private int mAdType;
        private String mAppId;

        public AdConfig.Builder adId(String adId) {
            this.mAdId = adId;
            return this;
        }

        public AdConfig.Builder ad_Type(int adType) {
            this.mAdType = adType;
            return this;
        }

        public AdConfig.Builder width(int width) {
            this.mWidth = width;
            return this;
        }

        public AdConfig.Builder height(int height) {
            this.mHeigt = height;
            return this;
        }

        public AdConfig.Builder appId(String appId) {
            this.mAppId = appId;
            return this;
        }

        public AdConfig build() {
            return new AdConfig(this);
        }
    }
}