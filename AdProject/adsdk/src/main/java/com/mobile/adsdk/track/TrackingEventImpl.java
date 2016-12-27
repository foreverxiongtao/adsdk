package com.mobile.adsdk.track;

import android.text.TextUtils;

import com.mobile.adsdk.global.API;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.inf.IEventTrackingListener;
import com.mobile.adsdk.mvp.model.AdModel;
import com.mobile.adsdk.mvp.presenter.AdPresenter;
import com.mobile.adsdk.mvp.view.base.BaseView;
import com.mobile.adsdk.utils.AbLogUtil;
import com.mobile.adsdk.utils.AdSdkDownlaoder;
import com.mobile.adsdk.utils.NetWorkManager;
import com.mobile.adsdk.utils.StringUtils;

import java.util.List;

import ks.AdProto;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by azkf-XT on 2016/12/23.
 */

public class TrackingEventImpl implements IEventTrackingListener, BaseView {

    private List<AdProto.Tracking> mTrackListing;
    private API mApi;
    private AdPresenter mPresenter;
    private String mClickId;


    private TrackingEventImpl(Builder builder) {
        this.mTrackListing = builder.mTrackingList;
        this.mClickId = builder.mClickId;
        this.mApi = NetWorkManager.getManager().getAPI();
        mPresenter = new AdPresenter();
        mPresenter.attachView(this);
        mPresenter.attachModel(new AdModel());
    }


    @Override
    public void resetTrackListEvent(List<AdProto.Tracking> trackingList) {
        this.mTrackListing = trackingList;
    }

    @Override
    public void trackClickEvent() {
        for (int i = 0; i < mTrackListing.size(); i++) {
            AdProto.Tracking tracking = mTrackListing.get(i);
            if (tracking != null && tracking.getTrackingEvent() != null) {
                if (tracking.getTrackingEvent().getNumber() == AdProto.Tracking.TrackingEvent.AD_CLICK.getNumber()) {
                    String trackingUrl = tracking.getTrackingUrl(0);
                    if (!TextUtils.isEmpty(trackingUrl)) {
                        String target = trackingUrl;
                        if (!TextUtils.isEmpty(mClickId)) {
                            target = StringUtils.replaceSpecialStr(target, LocaalConstant.APK_CLICK_ID_SYGNAL, mClickId);
                        }
                        mPresenter.uploadTracking(target);
                        AbLogUtil.e("trackClickEvent", "trackClickEvent track is null");
                        return;
                    }
                }
            } else {
                AbLogUtil.e("TrackingEventImpl", "trackClickEvent track is null");
            }
        }
    }

    @Override
    public void trackCloseEvent() {
        for (int i = 0; i < mTrackListing.size(); i++) {
            AdProto.Tracking tracking = mTrackListing.get(i);
            if (tracking != null && tracking.getTrackingEvent() != null) {
                if (tracking.getTrackingEvent().getNumber() == AdProto.Tracking.TrackingEvent.AD_CLOSE.getNumber()) {
                    String trackingUrl = tracking.getTrackingUrl(0);
                    if (!TextUtils.isEmpty(trackingUrl)) {
                        String target = trackingUrl;
                        if (!TextUtils.isEmpty(mClickId)) {
                            target = StringUtils.replaceSpecialStr(target, LocaalConstant.APK_CLICK_ID_SYGNAL, mClickId);
                        }
                        mPresenter.uploadTracking(target);
                        AbLogUtil.e("trackCloseEvent", "trackCloseEvent is successful");
                        return;
                    }
                }
            } else {
                AbLogUtil.e("TrackingEventImpl", "trackCloseEvent tracking is null");
            }
        }
    }

    @Override
    public void trackDisplayEvent() {
        for (int i = 0; i < mTrackListing.size(); i++) {
            AdProto.Tracking tracking = mTrackListing.get(i);
            if (tracking != null && tracking.getTrackingEvent() != null) {
                if (tracking.getTrackingEvent().getNumber() == AdProto.Tracking.TrackingEvent.AD_EXPOSURE.getNumber()) {
                    String trackingUrl = tracking.getTrackingUrl(0);
                    if (!TextUtils.isEmpty(trackingUrl)) {
                        String target = trackingUrl;
                        if (!TextUtils.isEmpty(mClickId)) {
                            target = StringUtils.replaceSpecialStr(target, LocaalConstant.APK_CLICK_ID_SYGNAL, mClickId);
                        }
                        mPresenter.uploadTracking(target);
                        AbLogUtil.e("trackDisplayEvent", "trackDisplayEvent is successful");
                        return;
                    }
                }
            } else {
                AbLogUtil.e("TrackingEventImpl", "trackDisplayEvent tracking is null");
            }
        }
    }

    @Override
    public void trackDownloadStartEvent() {
        for (int i = 0; i < mTrackListing.size(); i++) {
            AdProto.Tracking tracking = mTrackListing.get(i);
            if (tracking != null && tracking.getTrackingEvent() != null) {
                if (tracking.getTrackingEvent().getNumber() == AdProto.Tracking.TrackingEvent.APP_AD_START_DOWNLOAD.getNumber()) {
                    String trackingUrl = tracking.getTrackingUrl(0);
                    if (!TextUtils.isEmpty(trackingUrl)) {
                        String target = trackingUrl;
                        if (!TextUtils.isEmpty(mClickId)) {
                            target = StringUtils.replaceSpecialStr(target, LocaalConstant.APK_CLICK_ID_SYGNAL, mClickId);
                        }
                        mPresenter.uploadTracking(target);
                        AbLogUtil.e("trackDownloadStartEvent", "trackDownloadStartEvent is successful");
                        return;
                    }
                }
            } else {
                AbLogUtil.e("TrackingEventImpl", "trackDownloadStartEvent tracking is null");
            }
        }
    }

    @Override
    public void trackDownloadCompletedEvent() {
        for (int i = 0; i < mTrackListing.size(); i++) {
            AdProto.Tracking tracking = mTrackListing.get(i);
            if (tracking != null && tracking.getTrackingEvent() != null) {
                if (tracking.getTrackingEvent().getNumber() == AdProto.Tracking.TrackingEvent.APP_AD_DOWNLOAD.getNumber()) {
                    String trackingUrl = tracking.getTrackingUrl(0);
                    if (!TextUtils.isEmpty(trackingUrl)) {
                        String target = trackingUrl;
                        if (!TextUtils.isEmpty(mClickId)) {
                            target = StringUtils.replaceSpecialStr(target, LocaalConstant.APK_CLICK_ID_SYGNAL, mClickId);
                        }
                        mPresenter.uploadTracking(target);
                        AbLogUtil.e("trackDownloadCompletedEvent", "trackDownloadCompletedEvent is successful");
                        return;
                    }
                }
            } else {
                AbLogUtil.e("TrackingEventImpl", "trackDownloadCompletedEvent tracking is null");
            }
        }
    }

    @Override
    public void tranckDownloadInstallEvent() {
        for (int i = 0; i < mTrackListing.size(); i++) {
            AdProto.Tracking tracking = mTrackListing.get(i);
            if (tracking != null && tracking.getTrackingEvent() != null) {
                if (tracking.getTrackingEvent().getNumber() == AdProto.Tracking.TrackingEvent.APP_AD_INSTALL.getNumber()) {
                    String trackingUrl = tracking.getTrackingUrl(0);
                    if (!TextUtils.isEmpty(trackingUrl)) {
                        String target = trackingUrl;
                        if (!TextUtils.isEmpty(mClickId)) {
                            target = StringUtils.replaceSpecialStr(target, LocaalConstant.APK_CLICK_ID_SYGNAL, mClickId);
                        }
                        mPresenter.uploadTracking(target);
                        AbLogUtil.e("tranckDownloadInstallEvent", "tranckDownloadInstallEvent is successful");
                        return;
                    }
                }
            } else {
                AbLogUtil.e("TrackingEventImpl", "tranckDownloadInstallEvent tracking is null");
            }
        }
    }

    @Override
    public void handleData(Object o) {

    }

    @Override
    public void loadDataCompleted() {

    }

    @Override
    public void startToLoadData() {

    }

    @Override
    public void laodDataError(String errMsg) {

    }

    @Override
    public void uploadTracking() {

    }

    public static class Builder {
        private List<AdProto.Tracking> mTrackingList;
        private String mClickId;

        public Builder() {

        }

        public Builder trackinglList(List<AdProto.Tracking> trackingList) {
            this.mTrackingList = trackingList;
            return this;
        }

        public Builder clickid(String clickId) {
            this.mClickId = clickId;
            return this;
        }

        public TrackingEventImpl build() {
            return new TrackingEventImpl(this);
        }
    }
}
