package com.mobile.adsdk.mvp.presenter;

import android.widget.RelativeLayout;

import com.mobile.adsdk.mvp.model.AdModel;
import com.mobile.adsdk.mvp.presenter.base.BasePresenter;
import com.mobile.adsdk.mvp.view.base.BaseView;
import com.mobile.adsdk.widget.banner.AdConfig;

import java.io.IOException;

import ks.AdProto;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/20       13:42
 *
 * 描 述 :广告请求业务流程控制
 *
 * 修订日期 :
 */

public class AdPresenter extends BasePresenter<BaseView, AdModel> {

    /**
     * 请求广告
     *
     * @param config
     */
    public void def(AdConfig config) {
        Observable<AdProto.MobadsResponse> codeEntityObservable = getModel().def(config);
        Subscriber<AdProto.MobadsResponse> subscriber = new Subscriber<AdProto.MobadsResponse>() {
            @Override
            public void onCompleted() {
                getView().loadDataCompleted();
            }

            @Override
            public void onError(Throwable e) {
                getView().laodDataError(e.getMessage() + "");
            }

            @Override
            public void onNext(AdProto.MobadsResponse result) {
                getView().handleData(result);
            }
        };
        codeEntityObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().startToLoadData();
            }
        }).subscribe(subscriber);
        addSubscrib(subscriber);
    }


    public void uploadTracking(String url) {
        Observable<ResponseBody> codeEntityObservable = getModel().uploadTracking(url);
        Subscriber<ResponseBody> subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody result) {
                getView().uploadTracking();
            }
        };
        codeEntityObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe(subscriber);
        addSubscrib(subscriber);
    }


    public void getOptionalAdRequestUrl(String requiredType) {
        Observable<ResponseBody> codeEntityObservable = getModel().getOptionalAdRequest(requiredType);
        Subscriber<ResponseBody> subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody result) {
                if (result != null) {
                    String target = "";
                    try {
                        target = result.string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    getView().handleData(target);
                }
            }
        };
        codeEntityObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe(subscriber);
        addSubscrib(subscriber);
    }
}
