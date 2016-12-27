package com.mobile.adsdk.widget.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.mobile.adsdk.R;

import java.util.List;

import ks.AdProto;

class MockPagerAdapter extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final InfiniteViewPager viewpager;
    private final boolean isauto;
    private List<AdProto.Ad> mList;

    /**
     * 广告图片点击监听器
     */
    private ImageCycleViewListener mImageCycleViewListener;


    public void setDataList(List<AdProto.Ad> list) {
        if (list == null || list.size() == 0)
            throw new IllegalArgumentException("list can not be null or has an empty size");
        this.mList = list;
        this.notifyDataSetChanged();
    }


    public MockPagerAdapter(Context context, ImageCycleViewListener imageCycleViewListener, InfiniteViewPager viewPager, boolean isAuto) {
        mContext = context;
        this.mImageCycleViewListener = imageCycleViewListener;
        mInflater = LayoutInflater.from(mContext);
        this.viewpager = viewPager;
        this.isauto = isAuto;
    }


    @Override
    public View getView(final int position, View view, ViewGroup container) {
        final ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.item_viewpager, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        final AdProto.Ad ad = mList.get(position);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageCycleViewListener.onImageClick(position, ad, v);
            }
        });
        holder.image.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        if (isauto) {
                            viewpager.startAutoScroll();
                        }
                        break;
                    default:
                        // 停止图片滚动
                        if (isauto) {
                            viewpager.stopAutoScroll();
                        }
                        break;
                }
                return false;
            }
        });
        mImageCycleViewListener.displayImage(ad, holder.image);
        return view;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private class ViewHolder {
        public int position;
        ImageView image;

        public ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.item_image);
        }
    }
}
