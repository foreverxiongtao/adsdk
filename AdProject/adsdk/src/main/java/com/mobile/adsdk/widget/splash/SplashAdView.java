package com.mobile.adsdk.widget.splash;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mobile.adsdk.R;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.inf.IEventTrackingListener;
import com.mobile.adsdk.inf.SplashAdListener;
import com.mobile.adsdk.mvp.model.AdModel;
import com.mobile.adsdk.mvp.presenter.AdPresenter;
import com.mobile.adsdk.mvp.view.base.BaseView;
import com.mobile.adsdk.track.TrackingEventImpl;
import com.mobile.adsdk.utils.AbLogUtil;
import com.mobile.adsdk.utils.SDKUtils;
import com.mobile.adsdk.utils.ThreadManager;
import com.mobile.adsdk.widget.CarouseButton;
import com.mobile.adsdk.widget.banner.AdConfig;

import ks.AdProto;


final class SplashAdView extends RelativeLayout {

    private AdConfig mConfig;
    private boolean mSupprotClick = false;
    private Activity mContext;
    private ImageView mContentView;
    private CarouseButton mCountdown;
    private CarouseButton mIgnoreView;
    private CarouseButton mPassView;
    private SplashAdListener mListener;
    private AdPresenter mPresenter;
    private SplashView mSplashView;
    private AdProto.MobadsResponse mAd;
    private int mCountTime = LocaalConstant.SPLASH_DEFAULT_COUNT_DOWN;
    private CountDownTask mTask;
    private IEventTrackingListener mTrackingLister;
    private SplashAdView(Context context) {
        super(context);
    }

    public SplashAdView(Activity context, AdConfig config, SplashAdListener listener, boolean supportedClick) {
        this(context);
        this.mContext = context;
        this.mListener = listener;
        this.mConfig = config;
        this.mSupprotClick = supportedClick;
        initView();
        setEventListener();
        initData();
    }

    private void initView() {
        mContentView = new ImageView(mContext);
        mContentView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(640, 960);
        params.addRule(CENTER_IN_PARENT);
        mContentView.setLayoutParams(params);
        addView(mContentView);

        mCountdown = new CarouseButton(mContext);
        mCountdown.setTextSize(14);
        mCountdown.setBackGround(R.color.transparent, R.color.transparent, R.color.black, 20, false);
        RelativeLayout.LayoutParams countDownParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        countDownParams.leftMargin = 20;
        countDownParams.topMargin = 20;
        countDownParams.addRule(ALIGN_PARENT_TOP);
        countDownParams.addRule(ALIGN_PARENT_LEFT);
        addView(mCountdown, countDownParams);


        mIgnoreView = new CarouseButton(mContext);
        mIgnoreView.setTextSize(14);
        mIgnoreView.setTextColor(Color.argb(100, 100, 100, 100));
        mIgnoreView.setText("跳过");
        mIgnoreView.setBackGround(R.color.transparent, R.color.transparent, R.color.black, 20, false);
        RelativeLayout.LayoutParams ignoreViewParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ignoreViewParams.rightMargin = 20;
        ignoreViewParams.topMargin = 20;
        ignoreViewParams.addRule(ALIGN_PARENT_TOP);
        ignoreViewParams.addRule(ALIGN_PARENT_RIGHT);
        addView(mIgnoreView, ignoreViewParams);

        mPassView = new CarouseButton(mContext);
        mPassView.setTextSize(14);
        mPassView.setText("广告");
        mPassView.setBackGround(R.color.transparent, R.color.transparent, R.color.black, 20, false);
        RelativeLayout.LayoutParams mPassViewParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mPassViewParams.leftMargin = 20;
        mPassViewParams.bottomMargin = 20;
        mPassViewParams.addRule(ALIGN_PARENT_BOTTOM);
        mPassViewParams.addRule(ALIGN_PARENT_LEFT);
        addView(mPassView, mPassViewParams);
    }

    private void initData() {
        mSplashView = new SplashView();
        mPresenter = new AdPresenter();
        mPresenter.attachView(mSplashView);
        mPresenter.attachModel(new AdModel());
        loadAd();
    }


    private void loadAd() {
        if (mPresenter != null) {
            mPresenter.def(mConfig);
        }
        ThreadManager.init();
        mTask = new CountDownTask();
        ThreadManager.getUIHandler().post(mTask);
    }


    private void setEventListener() {
        if (!mSupprotClick) {
            return;
        }
        mContentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null && mAd != null && mAd.getAds(0) != null) {
                    AdProto.MaterialMeta metaGroup = mAd.getAds(0).getMetaGroup(0);
                    if (metaGroup != null) {
//                        if (metaGroup.getInteractionType().getNumber() == AdProto.MaterialMeta.InteractionType.DOWNLOAD.getNumber() && !TextUtils.isEmpty(metaGroup.getClickUrl())) {
//                            Downloader.getInstance(mContext).downlaod(metaGroup.getClickUrl(), metaGroup.getTitle() + "");
//                        } else if (metaGroup.getInteractionType().getNumber() == AdProto.MaterialMeta.InteractionType.SURFING.getNumber()) {
//                            Intent intent = new Intent(mContext, AdActivity.class);
//                            intent.putExtra(LocaalConstant.INTENT_WEB_URL, metaGroup.getClickUrl() + "");
//                            mContext.startActivity(intent);
//                        }
                        if (mTrackingLister != null) {
                            mTrackingLister.trackClickEvent();
                        }
                        SDKUtils.clickAction(mContext, metaGroup.getInteractionType(), metaGroup.getClickUrl(), metaGroup.getAppPackage(), mAd.getAds(0));
                    }
                    mListener.onAdClick();
                }
            }
        });
        mIgnoreView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTrackingLister != null) {
                    mTrackingLister.trackCloseEvent();
                }
                if (mListener != null) {
                    mListener.onAdDismissed();
                }
            }
        });
    }

    public void destroy() {
        if (mPresenter != null) {
            mPresenter.unsubcrib();
        }
        ThreadManager.getUIHandler().removeCallbacks(mTask);
        mAd = null;
        mContext = null;
    }

    private class CountDownTask implements Runnable {
        @Override
        public void run() {
            mCountdown.setText(mCountTime + "秒");
            if (mCountTime <= 0) {
                ThreadManager.getUIHandler().removeCallbacks(this);
                mCountTime = LocaalConstant.SPLASH_DEFAULT_COUNT_DOWN;
                setVisibility(View.GONE);
                if (mListener != null) {
                    mListener.onAdDismissed();
                }
                return;
            }
            mCountTime--;
            ThreadManager.getUIHandler().postDelayed(this, 1000);
        }
    }

    private class SplashView implements BaseView<AdProto.MobadsResponse> {
        @Override
        public void handleData(AdProto.MobadsResponse data) {
            mAd = data;
            if (data != null) {
                AbLogUtil.e("result=", data.toString() + "");
                if (data.getErrorCode() == LocaalConstant.RESPONSE_CODE_SUCCESS) {
                    if (data.getAds(0) != null) {
                        AdProto.Ad ad = data.getAds(0);
                        if (mTrackingLister == null) {
                            mTrackingLister = new TrackingEventImpl.Builder().trackinglList(ad.getAdTrackingList()).build();
                        } else {
                            mTrackingLister.resetTrackListEvent(ad.getAdTrackingList());
                        }
                        AdProto.MaterialMeta metaData = ad.getMetaGroup(0);
                        if (metaData != null) {
                            Glide.with(mContext).load(metaData.getImageSrc(0)).error(R.drawable.ic_launcher).placeholder(R.drawable.ic_launcher).into(mContentView);
                        }
                    }
                } else {
                    AbLogUtil.e("errorCode=", data.getErrorCode() + "");
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
            if (mListener != null) {
                mListener.onAdFailed(errMsg);
            }
        }

        @Override
        public void uploadTracking() {

        }
    }
}
