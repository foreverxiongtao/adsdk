package com.mobile.adsdk.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


/*
 *
 *
 * 版 权 :@Copyright 北京四象联创科技有限公司版权所有
 *
 * 作 者 :desperado
 *
 * 版 本 :6.9
 *
 * 创建日期 :2016/10/28  11:34
 *
 * 描 述 :吐司工具类
 *
 * 修订日期 :
 */

public class ToastUtils {

    /**
     * 上下文.
     */
    private static Context mContext = null;

    /**
     * 显示Toast.
     */
    public static final int SHOW_TOAST = 0;
    public static Toast mToast;

    /**
     * 主要Handler类，在线程中可用 what：0.提示文本信息
     */
    private static Handler baseHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    showToast(mContext, msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 描述：Toast提示文本.
     *
     * @param text 文本
     */
    public static void showToast(Context context, String text) {
        mContext = context;
        if (mContext != null && !TextUtils.isEmpty(text)) {
            initToastInstance();
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
            mToast.show();
        }
    }


    /**
     * 描述：Toast提示文本.
     *
     * @param resId 文本的资源ID
     */
    public static void showToast(Context context, int resId) {
        mContext = context;
        if (mContext != null) {
            CharSequence text = mContext.getResources().getText(resId);
            showToast(mContext, text.toString());
        }
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param resId 要提示的字符串资源ID，消息what值为0,
     */
    public static void showToastInThread(Context context, int resId) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", context.getResources().getString(resId));
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param text 消息what值为0
     */
    public static void showToastInThread(Context context, String text) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", text);
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 初始化吐司,保证数据的唯一性
     */
    private static void initToastInstance() {

        if (mToast == null) {
            synchronized (ToastUtils.class) {
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    /**
     * 带文字和图片的Toast
     *
     * @param context  上下文
     * @param text     文字
     * @param resource 图片资源
     */
    public static void showToast(Context context, String text, int resource) {
        // TODO Auto-generated method stub
        mContext = context;
        Toast toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (toast.getView() instanceof RelativeLayout) {
            RelativeLayout toastView = (RelativeLayout) toast.getView();
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(resource);
            toastView.addView(imageView, 0);
        } else if (toast.getView() instanceof LinearLayout) {
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(resource);
            toastView.addView(imageView, 0);
        }
        toast.show();
    }
}
