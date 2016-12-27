package com.mobile.adsdk.mvp.view.base;

public interface BaseView<T> {

    void handleData(T t);


    void loadDataCompleted();


    void startToLoadData();


    void laodDataError(String errMsg);

    void uploadTracking();
}
