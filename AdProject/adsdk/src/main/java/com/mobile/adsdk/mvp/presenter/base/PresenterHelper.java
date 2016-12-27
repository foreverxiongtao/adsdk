package com.mobile.adsdk.mvp.presenter.base;

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
 * 描 述 :presenter辅助接口
 *
 * 修订日期 :
 */
public interface PresenterHelper<V, M> {
    void attachView(V _view);

    void detachView();

    void attachModel(M _model);

}
