package com.mobile.adsdk.widget.nav;

import android.content.Context;
import android.graphics.Region;
import android.media.MediaCodec;
import android.text.TextUtils;

import com.facebook.stetho.common.StringUtil;
import com.mobile.adsdk.entity.OptionalRequestType;
import com.mobile.adsdk.global.LocaalConstant;
import com.mobile.adsdk.mvp.model.AdModel;
import com.mobile.adsdk.mvp.presenter.AdPresenter;
import com.mobile.adsdk.mvp.view.base.BaseView;
import com.mobile.adsdk.update.ServiceProvider;
import com.mobile.adsdk.utils.AbLogUtil;
import com.mobile.adsdk.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import ks.AdProto;

/**
 * Created by azkf-XT on 2016/12/26.
 */

public class OptionalRequestAd implements BaseView<String> {

    private AdProto.Ad mAd;
    private String clickId;
    private String mClickUrl;
    private AdPresenter mPresenter;

    private static OptionalRequestAd mInstance;
    private String mDstlink;
    private String mClickid;

    private Context mContext;

    public static OptionalRequestAd getmInstance() {
        if (mInstance == null) {
            synchronized (OptionalRequestAd.class) {
                if (mInstance == null) {
                    mInstance = new OptionalRequestAd();
                }
            }
        }
        return mInstance;
    }

    private OptionalRequestAd() {
        mPresenter = new AdPresenter();
        mPresenter.attachModel(new AdModel());
        mPresenter.attachView(this);
    }

    public void handleOptionalRequest(Context context, AdProto.Ad ad, OptionalRequestType requestAdType) {
        if (requestAdType.getValue() == OptionalRequestType.AD_REQUEST.getValue()) {
            if (ad != null) {
                mContext = context;
                mAd = ad;
                AdProto.MaterialMeta metaGroup = ad.getMetaGroup(0);
                if (metaGroup != null) {
                    String clickUrl = metaGroup.getClickUrl();
                    if (!TextUtils.isEmpty(clickUrl)) {
                        AbLogUtil.e("clickUrl:", clickUrl);
//                        StringUtils.replace(clickUrl,'${ACCT_TYPE}',OptionalRequestType.AD_REQUEST.getValue());
//                        String decorateUrl = clickUrl.replaceAll("\\$\\{" + "ACCT_TYPE\\}", OptionalRequestType.AD_REQUEST.getValue());
                        String decorateUrl = StringUtils.replaceSpecialStr(clickUrl, LocaalConstant.APK_TYPE_SYGNAL, OptionalRequestType.AD_REQUEST.getValue());
                        AbLogUtil.e("clickUrl:", decorateUrl);
                        mPresenter.getOptionalAdRequestUrl(decorateUrl);
                    }
                }
            }
//            mPresenter.getOptionalAdRequestUrl();
        }
    }

    @Override
    public void handleData(String response) {
        try {
            JSONObject object = new JSONObject(response);
            if (object.getInt("ret") == 0) {
                JSONObject data = object.getJSONObject("data");
                if (data != null) {
                    mDstlink = data.getString("dstlink");
                    mClickid = data.getString("clickid");
                    AbLogUtil.e("dstlink", mDstlink + "");
                    AbLogUtil.e("clickid", mClickid + "");
                    if (!TextUtils.isEmpty(mDstlink)) {
                        AdProto.MaterialMeta metaGroup = mAd.getMetaGroup(0);
                        if (metaGroup != null) {
                            ServiceProvider.getInstance(mContext).downlaodOrInstalled(mDstlink, metaGroup.getAppPackage() + "", mAd, mClickid);
                        }
                    }
                }
            }
            AbLogUtil.e("ret", "ret is not zero ");
        } catch (JSONException e) {
            e.printStackTrace();

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

    }

    @Override
    public void uploadTracking() {

    }
}
