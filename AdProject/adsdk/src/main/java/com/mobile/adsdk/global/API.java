package com.mobile.adsdk.global;


import ks.AdProto;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;
/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/20       13:13
 *
 * 描 述 :接口统一
 *
 * 修订日期 :
 */

public interface API {

    /**
     * 测试
     */
    String BASE_URL = "http://120.92.44.245";

    /**
     * 正式
     */
//    String BASE_URL = "http://api.ssp.ayang.com";

    /***
     * 广告接口
     *
     * @param request
     * @return
     */

    @POST("/api/def")
    Observable<AdProto.MobadsResponse> def(@Body AdProto.MobadsRequest request);


    /**
     * 下载
     *
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> download(@Url String url);


    /**
     * 上报事件
     *
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> uploadTracking(@Url String url);

    /**
     * 根据点击类型为optional的类型去获取广告url
     *
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> getOptionalAdRequest(@Url String url);
}
