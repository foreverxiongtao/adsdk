package com.mobile.adsdk.mvp.model;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/20       13:39
 *
 * 描 述 :广告请求数据源
 *
 * 修订日期 :
 */

import com.mobile.adsdk.global.NetParams;
import com.mobile.adsdk.mvp.model.base.BaseModel;
import com.mobile.adsdk.widget.banner.AdConfig;

import ks.AdProto;
import okhttp3.ResponseBody;
import rx.Observable;

public class AdModel extends BaseModel {
    public Observable<AdProto.MobadsResponse> def(AdConfig config) {
        AdProto.MobadsRequest mobadsRequest = NetParams.getInstance().generateKsAndroidRequest(config);
        return getAPIService().def(mobadsRequest);
    }

    public Observable<ResponseBody> uploadTracking(String url) {
        return getAPIService().uploadTracking(url);
    }

    public Observable<ResponseBody> getOptionalAdRequest(String url) {
        return getAPIService().getOptionalAdRequest(url);
    }
}
