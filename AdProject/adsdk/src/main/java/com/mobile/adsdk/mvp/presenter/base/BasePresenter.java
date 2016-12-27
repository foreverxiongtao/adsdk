package com.mobile.adsdk.mvp.presenter.base;


import com.mobile.adsdk.mvp.model.base.BaseModel;
import com.mobile.adsdk.mvp.view.base.BaseView;
import com.mobile.adsdk.utils.AbLogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/20       13:21
 *
 * 描 述 :业务处理基类
 *
 * 修订日期 :
 */
public abstract class BasePresenter<V extends BaseView, M extends BaseModel> implements PresenterHelper<V, M> {
    private List<Subscriber> mSubscribers = new ArrayList<>();//集合用于添加订阅对象
    private V mView;
    private M mModel;

    public BasePresenter() {
    }

    public void unsubcrib() {
        try {
            if (mSubscribers != null && !mSubscribers.isEmpty()) {
                for (int i = 0; i < mSubscribers.size(); i++) {
                    Subscriber mSubscriber = mSubscribers.get(i);
                    if (mSubscriber != null && !mSubscriber.isUnsubscribed()) {
                        mSubscriber.unsubscribe();
                        AbLogUtil.i("basePresenter", mSubscriber.getClass().getSimpleName() + "取消订阅成功");
                    }
                }
            }
        } catch (Exception e) {
            AbLogUtil.i("basePresenter", "取消订阅失败....");
        }
    }

    public void addSubscrib(Subscriber _subscriber) {
        mSubscribers.add(_subscriber);
    }


    @Override
    public void attachView(V _view) {
        this.mView = _view;
    }

    @Override
    public void attachModel(M _model) {
        this.mModel = _model;
    }


    @Override
    public void detachView() {
        this.mView = null;
    }


    public V getView() {
        return mView;
    }

    public M getModel() {
        return mModel;
    }
}
