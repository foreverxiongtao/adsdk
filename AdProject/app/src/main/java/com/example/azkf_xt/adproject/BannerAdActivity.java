package com.example.azkf_xt.adproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mobile.adsdk.Texter;
import com.mobile.adsdk.inf.BannerAdListener;
import com.mobile.adsdk.utils.SDKUtils;
import com.mobile.adsdk.widget.banner.BannerAd;


import ks.AdProto;

/*
 *
 *
 * 版 权 :@Copyright desperado版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :1.0
 *
 * 创建日期 :2016/12/21       19:41
 *
 * 描 述 :banner广告
 *
 * 修订日期 :
 */
public class BannerAdActivity extends Activity {

    String api_url, android_appid, android_slotid, ios_appid, ios_slotid, s;
    final static String _NAME = "KsMSSPTester";//vtt8ro1o
    final static String _VERSION = "V0.9.4";
    private LinearLayout ll_main_container;
    private BannerAd bannerAd;


//
//
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg != null) {
//                Bundle data = msg.getData();
//                if (data != null) {
//                    String res = data.getString("res");
//                    tv_display_data.setText(res + "");
//                }
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AdSdk.init(this);
        initView();
        Context context = SDKUtils.getmAdSdkConfig().getmContext();
        if(context==null){
            Log.e("context","null");
        }
    }

    private void initView() {
        ll_main_container = (LinearLayout) findViewById(R.id.ll_main_container);
        BannerAd bannerAd = new BannerAd(this, "vtt8ro1o", 800, 300, new BannerAdListener() {
            @Override
            public void onAdPresent() {
                Log.i("ManActivity", "onAdPresent");

            }

            @Override
            public void onAdDismissed() {
                Log.i("ManActivity", "onAdDismissed");

            }

            @Override
            public void onAdFailed(String var1) {
                Log.i("ManActivity", "onAdFailed");

            }

            @Override
            public void onAdClick() {
                Log.i("ManActivity", "onAdClick");

            }
        });
        this.bannerAd = bannerAd;
//        bannerAd.setAdSize(800, 300);
//        bannerAd.setAdListener(new IAdListener() {
//            @Override
//            public void onAdvertisementDataDidLoadSuccess() {
//                Log.i("ManActivity", "Success");
//            }
//
//            @Override
//            public void onAdvertisementDataDidLoadFailure() {
//                Log.i("ManActivity", "Success");
//            }
//
//            @Override
//            public void onAdVertisementDataDidLoadStart() {
//                Log.i("ManActivity", "Start");
//            }
//
//            @Override
//            public void onAdVertisementDataDidLoadCompleted() {
//                Log.i("ManActivity", "Completed");
//            }
//
//            @Override
//            public void onAdvertisementViewDidShow() {
//                Log.i("ManActivity", "Show");
//            }
//
//            @Override
//            public void onAdvertisementViewDidClick() {
//                Log.i("ManActivity", "Click");
//            }
//
//            @Override
//            public void onAdvertisementViewWillStartNewIntent() {
//                Log.i("NewIntent", "NewIntent");
//            }
//        });
        ll_main_container.addView(this.bannerAd.getBannerView(), 0);
    }

    public void getData(View view) {
        requestData();
    }

    @Override
    protected void onDestroy() {
        if (bannerAd != null) {
            bannerAd.destroy();
        }
        super.onDestroy();
    }

    private void requestData() {
        bannerAd.showAd();
//        api_url = "http://120.92.44.245/api/def";                //测试环境
////    		api_url = "http://123.59.14.199:8084/api/test/9";		//开发环境
//        android_appid = "6bq3bpop";
//        android_slotid = "sx8d0lkj";
//        ios_appid = "j0g2phg7";
//        ios_slotid = "julx2mnw";
//        AdProto.MobadsRequest request = NetParams.getInstance().generateKsAndroidRequest(android_slotid, android_appid, 640, 960).build();
//        ReqAndRpd(api_url, request);
//        bannerAd.adLoad();

    }


    /**
     * <code>
     * (String url,com.ads.utils.KsMobadsApi53.MobadsRequest req)
     * 服务器的地址，构造的请求，本方法用于发送广告请求并获取服务器的反馈
     * </code>
     * <p>
     * <pre>
     * 向MSSP请求广告并获取反馈
     * </pre>
     */
    public void ReqAndRpd(String url, final AdProto.MobadsRequest req) {
        new Texter().text(this);
//        MobadsRequest adrequest = MobadsRequest.newBuilder().setRequestId(requestId).setAdslot(adslot).build();
//        byte[] content = adrequest.toByteArray();
//        HttpClient client = new HttpClient();
//        PostMethod postMethod = new PostMethod(URL);
//        postMethod.addRequestHeader("Content-Type", "application/octet-stream;charset=utf-8");
//        postMethod.setRequestEntity(new ByteArrayRequestEntity(content ));
//        client.executeMethod(postMethod);

//
//        //将请求序列化并发送
//        HandlerThread handlerThread = new HandlerThread("background", Process.THREAD_PRIORITY_BACKGROUND);
//        handlerThread.start();
//        final Handler handlerbg = new Handler(handlerThread.getLooper());
//        handlerbg.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

//        Request<byte[]> byteArrayRequest = NoHttp.createByteArrayRequest("http://120.92.44.245//api/def");


    }

//            // 建立请求连接并设置通用的请求属性
////            URL realUrl = new URL(url);
////            final URLConnection conn = realUrl.openConnection();
////            conn.setRequestProperty("
// ", "*/*");
////            conn.setRequestProperty("connection", "Keep-Alive");
////            conn.setRequestProperty("user-agent", _NAME + _VERSION + " (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
////            conn.setDoOutput(true);
////            conn.setDoInput(true);
////
////            //将请求序列化并发送
////            HandlerThread handlerThread = new HandlerThread("background", Process.THREAD_PRIORITY_BACKGROUND);
////            handlerThread.start();
////            final Handler handlerbg = new Handler(handlerThread.getLooper());
////            handlerbg.post(new Runnable() {
////                @Override
////                public void run() {
////                    try {
//                        req.writeTo(conn.getOutputStream());
//////                        conn.getOutputStream().flush();
//////                        //获取反馈信息并解序列化
//////                        A.MobadsResponse rpd = A.MobadsResponse.parseFrom(conn.getInputStream());
//////                        Bundle bundle = new Bundle();
//////                        bundle.putString("res", rpd.toString() + "");
//////                        Message message = Message.obtain();
//////                        message.setData(bundle);
//////                        handler.sendMessage(message);
////
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////            });
//
//
//        } catch (UnknownHostException e1) {
//
//            e1.printStackTrace();
//        } catch (IOException e1) {
//
//            e1.printStackTrace();
//        }
//    }

//
//    /**
//     * <code>
//     * (String slotId,String appId,int width,int height)
//     * 广告位id，应用id，广告位宽，广告位高
//     * 请根据所注册的广告位及应用正确填写，方法中的其他参数，请在后期根据实际情况填写
//     * </code>
//     * <p>
//     * <pre>
//     * 生成一个android终端的广告请求
//     * </pre>
//     */
//    public A.MobadsRequest.Builder generateKsAndroidRequest(String slotId, String appId, int width, int height) {
//        //广告位信息（广告位ID，广告位宽，广告位高）
//        A.AdSlot.Builder slot = A.AdSlot.newBuilder();
//        slot.setAdslotId(slotId).setAdslotSize(A.Size.newBuilder().setWidth(width).setHeight(height));
//
//        //APP信息（应用ID，包名，版本号）
//        A.App.Builder app = A.App.newBuilder();
//        app.setAppId(appId).setAppPackage("com.example.ANDROIDdebug");
//        app.setAppVersion(A.Version.newBuilder().setMajor(3).setMinor(4).setMicro(0));
//
//        //设备识别信息（MAC地址，IMEI，AndroidID）
//        A.UdId.Builder uId = A.UdId.newBuilder();
//        uId.setMac("d8:55:a3:ce:e4:40").setImei("866926020248380").setAndroidId("5b7e9e4f42a6635f");
//
//        //设备信息（设备类型，系统类型，设备厂商，设备型号，操作系统版本，设备识别信息，屏幕尺寸）
//        A.Device.Builder device = A.Device.newBuilder();
//        device.setDeviceType(A.Device.DeviceType.PHONE);
//        device.setOsType(A.Device.OsType.ANDROID);
//        device.setVendor(com.google.protobuf.ByteString.copyFrom("MEIZU".getBytes()));
//        device.setModel(com.google.protobuf.ByteString.copyFrom("MX5".getBytes()));
//        device.setOsVersion(A.Version.newBuilder().setMajor(3).setMinor(1).setMicro(0));
//        device.setUdid(uId).setScreenSize(A.Size.newBuilder().setWidth(1080).setHeight(1920));
//
//        //网络信息（网络连接类型，运营商类型，IP地址）
//        A.Network.Builder connType = A.Network.newBuilder();
//        connType.setConnectionType(A.Network.ConnectionType.WIFI);
//        connType.setOperatorType(A.Network.OperatorType.CHINA_MOBILE);
//        connType.setIpv4("114.255.44.132");
//
//        //GPS信息
//        A.Gps.Builder gps = A.Gps.newBuilder();
//        gps.setCoordinateType(A.Gps.CoordinateType.WGS84);
//        gps.setLatitude(40.7127).setLongitude(74.0059).setTimestamp(123456);
//
//        //请求信息（请求ID，协议版本号，广告位信息，APP信息，设备信息，网络信息，GPS信息，调试模式）
//        A.MobadsRequest.Builder request = A.MobadsRequest.newBuilder();
//        request.setRequestId(MD5Builder.getMD5(UUID.randomUUID().toString()));
//        request.setApiVersion(A.Version.newBuilder().setMajor(5).setMinor(0).setMicro(0));
//        request.setAdslot(slot).setApp(app).setDevice(device).setNetwork(connType).setGps(gps).setIsDebug(true);
//        return request;
//    }
}
