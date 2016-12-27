package com.mobile.adsdk.inf;
/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/19       12:20
 *
 * 描 述 :sdk入口协议
 *
 * 修订日期 :
 */
public interface ISdkAPI {
    /**
     * 注册客户端app
     *
     * @param apikey
     * @return
     */
    void registerApp(String apikey);

    /**
     * 注销客户端app
     */
    void unregisterApp();

    /**
     * 获取当前的sdk版本号
     */
    int getAdSdkVersion();

}
