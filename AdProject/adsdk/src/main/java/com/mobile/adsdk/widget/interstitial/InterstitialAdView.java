package com.mobile.adsdk.widget.interstitial;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mobile.adsdk.R;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.inf.IEventTrackingListener;
import com.mobile.adsdk.inf.InterstitialAdListener;
import com.mobile.adsdk.mvp.model.AdModel;
import com.mobile.adsdk.mvp.presenter.AdPresenter;
import com.mobile.adsdk.mvp.view.base.BaseView;
import com.mobile.adsdk.track.TrackingEventImpl;
import com.mobile.adsdk.utils.AbLogUtil;
import com.mobile.adsdk.utils.SDKUtils;
import com.mobile.adsdk.widget.banner.AdConfig;
import com.mobile.adsdk.widget.banner.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import ks.AdProto;

final class InterstitialAdView extends RelativeLayout {
    private AdConfig mConfig;
    private InterstitialAdListener mListener;
    private Activity mActivity;
    private ViewPager mVp;
    private InterstitialAdapter adapter;
    private AdPresenter mPresenter;
    private ImageView closeImage;
    private CirclePageIndicator mIndicator;
    private IEventTrackingListener mTrackListListener;

    private InterstitialAdView(Context context) {
        super(context);
    }

    public InterstitialAdView(Activity context, AdConfig config, InterstitialAdListener listener) {
        this(context);
        this.mActivity = context;
        this.mConfig = config;
        this.mListener = listener;
        initView();
        setEventListener();
        initData();
    }

    private void initView() {
        mVp = new ViewPager(mActivity);
        RelativeLayout.LayoutParams vpparams = new RelativeLayout.LayoutParams(600, 500);
        vpparams.addRule(CENTER_IN_PARENT);
        mVp.setLayoutParams(vpparams);
        mVp.setId(R.id.vp);
        addView(mVp);

        closeImage = new ImageView(mActivity);
        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(50, 50);
        closeImage.setScaleType(ImageView.ScaleType.FIT_XY);
        closeImage.setImageResource(R.drawable.ic_close_icon);
        closeParams.addRule(ALIGN_TOP, R.id.vp);
        closeParams.addRule(ALIGN_RIGHT, R.id.vp);
        closeImage.setLayoutParams(closeParams);
        closeImage.setVisibility(View.GONE);
        addView(closeImage);


        mIndicator = new CirclePageIndicator(mActivity);
        RelativeLayout.LayoutParams indicatorParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        indicatorParams.bottomMargin = 10;
        indicatorParams.addRule(ALIGN_BOTTOM, R.id.vp);
        indicatorParams.addRule(CENTER_HORIZONTAL);
        mIndicator.setLayoutParams(indicatorParams);
        mIndicator.setVisibility(View.GONE);
        addView(mIndicator);
    }

    public void hideAd() {
        setVisibility(View.GONE);
        if (mTrackListListener != null) {
            mTrackListListener.trackCloseEvent();
        }
        if (mPresenter != null) {
            mPresenter.unsubcrib();
        }
        if (mListener != null) {
            mListener.onAdDismissed();
        }
    }


    private void setEventListener() {
        closeImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAd();
            }
        });
    }


    private void initData() {
        mPresenter = new AdPresenter();
        mPresenter.attachModel(new AdModel());
        mPresenter.attachView(new InterstitialView());
        loadAd();
    }

    private void loadAd() {
        if (mPresenter != null) {
            mPresenter.def(mConfig);
        }
    }

    public void destroy() {
        setVisibility(View.GONE);
        if (mPresenter != null) {
            mPresenter.unsubcrib();
            mPresenter = null;
            mActivity = null;
        }
    }

    public boolean isAdClosed() {
        return getVisibility() == View.VISIBLE ? false : true;
    }

    public void showAd() {
        if (mPresenter != null) {
            mPresenter.def(mConfig);
        }
        if (mListener != null) {
            mListener.onAdPresent();
        }
        setVisibility(View.VISIBLE);
    }


    private class InterstitialAdapter extends PagerAdapter {
        private List<ImageView> mViews;

        public InterstitialAdapter(List<ImageView> views, List<AdProto.MaterialMeta> metaGroupList) {
            this.mViews = views;
            if (mViews == null) {
                throw new RuntimeException("mViews不能为空");
            }
            if (metaGroupList == null) {
                throw new RuntimeException("metaGroupList不能为空");
            }
        }

        @Override
        public int getCount() {
            return mViews == null ? 0 : mViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(mViews.get(position), 0);//添加页卡
            return mViews.get(position);
        }
    }


    private void dynamicInflateView(final List<AdProto.MaterialMeta> metaGroupList, final AdProto.Ad ad) {
        if (metaGroupList != null && !metaGroupList.isEmpty()) {
            List<ImageView> views = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                final AdProto.MaterialMeta materialMeta = metaGroupList.get(0);
                if (materialMeta != null && !TextUtils.isEmpty(materialMeta.getImageSrc(0))) {
                    ImageView view = new ImageView(mActivity);
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(mActivity).load(materialMeta.getImageSrc(0)).error(R.drawable.ic_launcher).placeholder(R.drawable.ic_launcher).into(view);
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (materialMeta != null) {
                                if (mTrackListListener != null) {
                                    mTrackListListener.trackClickEvent();
                                }
                                SDKUtils.clickAction(mActivity, materialMeta.getInteractionType(), materialMeta.getClickUrl(), materialMeta.getAppPackage(), ad);
//                                if (materialMeta.getInteractionType().getNumber() == AdProto.MaterialMeta.InteractionType.DOWNLOAD.getNumber() && !TextUtils.isEmpty(materialMeta.getClickUrl())) {
//                                    Downloader.getInstance(mActivity).downlaod(materialMeta.getClickUrl(), materialMeta.getTitle() + "");
//                                } else if (materialMeta.getInteractionType().getNumber() == AdProto.MaterialMeta.InteractionType.SURFING.getNumber()) {
//                                    Intent intent = new Intent(mActivity, AdActivity.class);
//                                    intent.putExtra(LocaalConstant.INTENT_WEB_URL, materialMeta.getClickUrl() + "");
//                                    mActivity.startActivity(intent);
//                                }
                            }
                        }
                    });
                    views.add(view);
                }
            }
            adapter = new InterstitialAdapter(views, metaGroupList);
            mVp.setAdapter(adapter);
            mIndicator.setViewPager(mVp);
            mIndicator.setVisibility(View.VISIBLE);
            closeImage.setVisibility(View.VISIBLE);
        }
    }


    private class InterstitialView implements BaseView<AdProto.MobadsResponse> {

        @Override
        public void handleData(AdProto.MobadsResponse data) {
            if (data != null) {
                AbLogUtil.e("result=", data.toString() + "");
                //success
                if (data.getErrorCode() == LocaalConstant.RESPONSE_CODE_SUCCESS) {
                    AdProto.Ad ad = data.getAds(0);
                    if (ad != null) {
                        if (mTrackListListener == null) {
                            mTrackListListener = new TrackingEventImpl.Builder().trackinglList(ad.getAdTrackingList()).build();
                        } else {
                            mTrackListListener.resetTrackListEvent(ad.getAdTrackingList());
                        }
                        List<AdProto.MaterialMeta> metaGroupList = ad.getMetaGroupList();
                        dynamicInflateView(metaGroupList, ad);
                        if (mListener != null) {
                            mListener.onAdPresent();
                        }
                    }

                } else {
                    AbLogUtil.e("err=", data.getErrorCode() + "");
                }
            }
        }

        @Override
        public void loadDataCompleted() {

        }

        @Override
        public void startToLoadData() {
            if (mListener != null) {
                mListener.onAdReady();
            }
        }

        @Override
        public void laodDataError(String errMsg) {
            if (mListener != null) {
                mListener.onAdFailed(errMsg + "");
            }
        }

        @Override
        public void uploadTracking() {

        }
    }
}
