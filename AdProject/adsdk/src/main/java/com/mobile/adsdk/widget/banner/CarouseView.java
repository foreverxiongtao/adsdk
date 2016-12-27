package com.mobile.adsdk.widget.banner;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.mobile.adsdk.R;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.inf.BannerAdListener;
import com.mobile.adsdk.inf.IEventTrackingListener;
import com.mobile.adsdk.mvp.model.AdModel;
import com.mobile.adsdk.mvp.presenter.AdPresenter;
import com.mobile.adsdk.mvp.view.base.BaseView;
import com.mobile.adsdk.track.TrackingEventImpl;
import com.mobile.adsdk.utils.AbLogUtil;
import com.mobile.adsdk.utils.RxBus;
import com.mobile.adsdk.utils.SDKUtils;
import com.mobile.adsdk.utils.SPUtils;

import java.lang.reflect.Field;
import java.util.List;

import ks.AdProto;
import rx.Observable;
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
 * 创建日期 :2016/12/20       15:20
 *
 * 描 述 :轮播图
 *
 * 修订日期 :
 */
final class CarouseView extends LinearLayout implements BaseView<AdProto.MobadsResponse> {


    private AdConfig mConfig;
    /**
     * 上下文
     */
    private Activity mContext;
    /*
    * 无限录播的viewPager
    * */
    private InfiniteViewPager mViewPager;
    /*
    * 圆点指示器
    * */
    private CirclePageIndicator mIndicator;
    /*
    * 设置轮播图是否支持自动轮播
    * */
    private boolean isCarouseAutoPlay;
    /*
    * 轮播图片展示集合
    * */
    private ImageView[] mImageViews;
    /*
    * 轮播图片适配器
    * */
    private MockPagerAdapter mMPAdapter;
    /*
    * 轮播图滑动监听
    * */
    private ViewPager.OnPageChangeListener mListener;
    /*viewpager滑动的速度 默认是500*/
    private int mSlideSpeed = 400;
    /*轮播图轮播间隔的时间 默认是4秒*/
    private long mDelayTime = 4000;
    private ImageView mIvCarouseView;
    private BannerAdListener mAdListener;
    private AdPresenter presenter;
    private String mAdId;
    private ImageView iv_banner_close;
    private IEventTrackingListener mTrackingListener;

    private CarouseView(Context context) {
        super(context);
    }


    public CarouseView(Context context, BannerAdListener listener, AdConfig config) {
        this(context);
        this.mContext = (Activity) context;
        this.mAdListener = listener;
        this.mConfig = config;
        init();
    }


    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.carouse_view, this);
        mViewPager = (InfiniteViewPager) findViewById(R.id.ivp_pager);
        mIvCarouseView = (ImageView) findViewById(R.id.iv_carouse_view);
        iv_banner_close = (ImageView) findViewById(R.id.iv_banner_close);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        presenter = new AdPresenter();
        presenter.attachModel(new AdModel());
        presenter.attachView(this);
        iv_banner_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAd();
            }
        });
        Observable<AdProto.Tracking> trackingObservable = RxBus.getDefault().toObservable(AdProto.Tracking.class);
        trackingObservable.subscribe(new Subscriber<AdProto.Tracking>() {
            @Override
            public void onCompleted() {
                AbLogUtil.e("onCompleted", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                AbLogUtil.e("onError", "onError");
            }

            @Override
            public void onNext(AdProto.Tracking tracking) {
                AbLogUtil.e("onNext", "onNext");
            }
        });
        load(mConfig);
    }

    public void load(AdConfig config) {
        if (presenter != null) {
            presenter.def(config);
        }
    }


    public void showAd() {
        if (getVisibility() != VISIBLE) {
            if (presenter != null) {
                presenter.def(mConfig);
            }
        }
    }

    public void hideAd() {
        setVisibility(View.GONE);
        if (presenter != null) {
            if (mTrackingListener != null) {
                mTrackingListener.trackCloseEvent();
            }
            presenter.unsubcrib();
        }
        if (mAdListener != null) {
            mAdListener.onAdDismissed();
        }
    }

    public void destroy() {
        setVisibility(View.GONE);
        if (presenter != null) {
            presenter.unsubcrib();
            presenter = null;
            mContext = null;
        }
    }

    /**
     * 开始轮播(手动控制自动轮播与否，便于资源控制)
     */
    public void startImageCycle() {
        if (isCarouseAutoPlay)
            mViewPager.startAutoScroll();
    }

    /*
    * 设置轮播图轮动速度
    * */
    public void setCarouseCycleSpeed(int speed) {
        this.mSlideSpeed = speed;
    }

    /*
    * 设置轮播图切换动画
    * */
    public void setCarouseTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * 开始轮播(手动控制自动轮播与否，便于资源控制)，并设置间隔时间
     */
    public void startImageCycle(long delayTime) {
        if (isCarouseAutoPlay)
            mViewPager.startAutoScroll(delayTime);
    }

    /**
     * 暂停轮播——用于节省资源
     */
    public void stopImageCycle() {
        if (isCarouseAutoPlay)
            mViewPager.stopAutoScroll();
    }

    /**
     * 设定轮播图是否能自动轮播
     */
    public void setIsCarouseAutoPlay(boolean isCarouseAutoPlay) {
        this.isCarouseAutoPlay = isCarouseAutoPlay;
    }

    /*
    * 设置轮播图轮播间隔的时间
    * */
    public void setCarouseDelayTime(long time) {
        this.mDelayTime = time;
    }


    /**
     * 设置轮播滑动监听
     *
     * @param listner 轮播滑动监听
     */

    public void setCarousePageChangeListner(ViewPager.OnPageChangeListener listner) {
        this.mListener = listner;
    }


    /**
     * 设置轮播滑动的速度
     */
    private void setScrollSpeed(int speed) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            LinearInterpolator interpolator = new LinearInterpolator();
            FixedSpeedScroller myScroller = new FixedSpeedScroller(mContext, interpolator, speed);
            mScroller.set(mViewPager, myScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 设置图片集合
    * */
    public void setImageResources(final List<AdProto.Ad> ads, final ImageCycleViewListener imageCycleViewListener) {
        // 图片广告数量
        if (ads == null || ads.isEmpty()) {
            return;
        }
        final int imageCount = ads.size();
        mViewPager.setVisibility(View.VISIBLE);
          /*
        * */
        setScrollSpeed(mSlideSpeed);
          /*
        * 设置默认的viewpager切换动画
        * */
        mViewPager.setAutoScrollTime(mDelayTime);
        if (mListener != null) {
            mViewPager.setOnPageChangeListener(mListener);
        }
        if (imageCount > 0) {
            if (imageCount == 1) {
                /** 如果只有一张图片那么禁止LoopViewPager的滑动并且禁止自动轮播,不显示小圆点 */
                mIvCarouseView.setVisibility(View.VISIBLE);
                mIvCarouseView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageCycleViewListener.onImageClick(0, ads.get(0), mIvCarouseView);
                    }
                });
                mIndicator.setVisibility(View.GONE);
                mViewPager.setVisibility(View.GONE);
                imageCycleViewListener.displayImage(ads.get(0), mIvCarouseView);
                return;
            } else {
                mMPAdapter = new MockPagerAdapter(mContext, imageCycleViewListener, mViewPager, isCarouseAutoPlay);
                mMPAdapter.setDataList(ads);
                mViewPager.setAdapter(mMPAdapter);
                /** 大于一张图片,显示小圆点*/
                mIndicator.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
                mIvCarouseView.setVisibility(View.GONE);
                mIndicator.setViewPager(mViewPager);
            }
        }
    }

    @Override
    public void handleData(final AdProto.MobadsResponse result) {
        if (result != null) {
            AbLogUtil.e("result=", result.toString());
            if (result.getErrorCode() == LocaalConstant.RESPONSE_CODE_SUCCESS) {
                AbLogUtil.e("code=", "success");
                final List<AdProto.Ad> adsList = result.getAdsList();
                iv_banner_close.setVisibility(View.VISIBLE);
                if (adsList != null && !adsList.isEmpty()) {
                    setImageResources(adsList, new ImageCycleViewListener() {
                        @Override
                        public void displayImage(AdProto.Ad ad, final ImageView imageView) {
                            AdProto.MaterialMeta metaGroup = ad.getMetaGroup(0);
                            if (metaGroup != null && !TextUtils.isEmpty(metaGroup.getImageSrc(0))) {
                                if (ad.getAdTrackingList() != null && !ad.getAdTrackingList().isEmpty()) {
                                    mTrackingListener = new TrackingEventImpl.Builder().trackinglList(ad.getAdTrackingList()).build();
                                }
                                Glide.with(mContext).load(metaGroup.getImageSrc(0)).error(R.drawable.ic_launcher).placeholder(R.drawable.ic_launcher).into(imageView);
                            }
                        }

                        @Override
                        public void onImageClick(int position, AdProto.Ad ad, View imageView) {
                            AdProto.MaterialMeta metaGroup = ad.getMetaGroup(0);
                            if (metaGroup != null) {
                                List<AdProto.Tracking> adTrackingList = ad.getAdTrackingList();
                                if (adTrackingList != null && !adTrackingList.isEmpty() && mTrackingListener != null) {
                                    mTrackingListener.trackClickEvent();
                                }
                                SDKUtils.clickAction(mContext, metaGroup.getInteractionType(), metaGroup.getClickUrl(), metaGroup.getAppPackage(), ad);
                                if (mAdListener != null) {
                                    mAdListener.onAdClick();
                                }
                            }
                        }
                    });
                }
                setVisibility(View.VISIBLE);
            } else {
                AbLogUtil.e("code=", result.getErrorCode() + "");
            }
            if (mAdListener != null) {
                mAdListener.onAdPresent();
            }
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
        if (mAdListener != null) {
            AbLogUtil.e("carouseView", errMsg + "");
            mAdListener.onAdFailed(errMsg + "");
        }
    }

    @Override
    public void uploadTracking() {
        AbLogUtil.e("carouseView", "tracking successfully");
    }
}

