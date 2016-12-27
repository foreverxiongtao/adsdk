package com.mobile.adsdk.widget.nav;

import android.app.Activity;

import com.mobile.adsdk.entity.AdSlotType;
import com.mobile.adsdk.entity.NativeErrorCode;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.inf.AdNativeNetworkListener;
import com.mobile.adsdk.inf.IEventTrackingListener;
import com.mobile.adsdk.inf.NativeResponse;
import com.mobile.adsdk.mvp.model.AdModel;
import com.mobile.adsdk.mvp.presenter.AdPresenter;
import com.mobile.adsdk.mvp.view.base.BaseView;
import com.mobile.adsdk.track.TrackingEventImpl;
import com.mobile.adsdk.utils.AbLogUtil;
import com.mobile.adsdk.utils.SDKUtils;
import com.mobile.adsdk.widget.banner.AdConfig;

import java.util.ArrayList;
import java.util.List;

import ks.AdProto;

/**
 * Created by azkf-XT on 2016/12/22.
 */

final class AdNativeDataWrapper {

    private AdNativeNetworkListener mListener;
    private AdPresenter mPresenter;
    private Activity mActivity;
    private BaseView mView;
    private String mAdId;
    private AdConfig mConfig;

    public void loadNativeAd() {
        if (mPresenter != null) {
            String appid = SDKUtils.getAppId(mActivity);
            mConfig = new AdConfig.Builder().ad_Type(AdSlotType.AD_NATIVE.getValue()).appId(appid).adId(mAdId).build();
            mPresenter.def(mConfig);
        }
    }

    private AdNativeDataWrapper() {
        mPresenter = new AdPresenter();
        mPresenter.attachModel(new AdModel());
        mView = new AdNativeDataWrapperView();
        mPresenter.attachView(mView);
    }


    private void decorateData(List<AdProto.Ad> ads) {
        List<NativeResponse> list = new ArrayList<>();
        for (int i = 0; i < ads.size(); i++) {
            AdProto.Ad ad = ads.get(i);
            if (ad != null && ad.getMetaGroup(0) != null) {
                AdProto.MaterialMeta metaGroup = ad.getMetaGroup(0);
                NativeResponseImpl.Builder builder = new NativeResponseImpl.Builder();
                builder.activity(mActivity);
                builder.appPackage(metaGroup.getAppPackage() + "");
                builder.appSize(metaGroup.getAppSize());
                builder.brandname(metaGroup.getBrandName() + "");
                builder.desc(SDKUtils.ByStrToStr(metaGroup.getDescription(0)));
                builder.iconurl(metaGroup.getIconSrc(0));
                builder.imageurl(metaGroup.getImageSrc(0));
                builder.title(SDKUtils.ByStrToStr(metaGroup.getTitle()));
                builder.minpicheight(metaGroup.getMaterialHeight());
                builder.minpicwidth(metaGroup.getMaterialWidth());
                builder.interactType(metaGroup.getInteractionType());
                builder.clickUrl(metaGroup.getClickUrl());
                builder.ad(ad);
                IEventTrackingListener listener = new TrackingEventImpl.Builder().trackinglList(ad.getAdTrackingList()).build();
                builder.tracklistener(listener);
                NativeResponseImpl response = builder.build();
                list.add(response);
            }
        }
        if (mListener != null) {
            mListener.onNativeLoad(list);
        }
    }


    public AdNativeDataWrapper(Activity activity, String adId, AdNativeNetworkListener listener) {
        this();
        this.mActivity = activity;
        this.mAdId = adId;
        this.mListener = listener;
    }

    class AdNativeDataWrapperView implements BaseView<AdProto.MobadsResponse> {

        @Override
        public void handleData(AdProto.MobadsResponse mobadsResponse) {
            if (mobadsResponse != null) {
                AbLogUtil.e("result=", mobadsResponse.toString());
                if (mobadsResponse.getErrorCode() == LocaalConstant.RESPONSE_CODE_SUCCESS) {
                    List<AdProto.Ad> adsList = mobadsResponse.getAdsList();
                    if (adsList != null & !adsList.isEmpty()) {
                        decorateData(adsList);
                    }
                } else {
                    AbLogUtil.e("result=", mobadsResponse.getErrorCode() + "");
                    mListener.onNativeFail(NativeErrorCode.LOAD_AD_FAILED);
                }
            } else {
                mListener.onNativeFail(NativeErrorCode.LOAD_AD_FAILED);
            }
        }

        @Override
        public void loadDataCompleted() {

        }

        @Override
        public void startToLoadData() {

        }

        @Override
        public void laodDataError(String errMsg) {
            if (mListener != null) {
                mListener.onNativeFail(NativeErrorCode.NET_WORK_ERROR);
            }
        }

        @Override
        public void uploadTracking() {

        }
    }
}
