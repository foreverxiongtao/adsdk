package com.example.azkf_xt.adproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.mobile.adsdk.entity.NativeErrorCode;
import com.mobile.adsdk.inf.AdNativeNetworkListener;
import com.mobile.adsdk.inf.NativeResponse;
import com.mobile.adsdk.widget.nav.AdNative;

import java.util.ArrayList;
import java.util.List;


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
 * 描 述 :
 *
 * 修订日期 :
 */
public class NativeListActivity extends AppCompatActivity {
    ListView listView;
    MyAdapter adapter;
    List<NativeResponse> nrAdList = new ArrayList<NativeResponse>();
    private static String YOUR_AD_PLACE_ID = "fdej1xay"; // 双引号中填写自己的广告位ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_listview);
        listView = (ListView) this.findViewById(R.id.native_list_view);
        listView.setCacheColorHint(Color.WHITE);
        adapter = new MyAdapter(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("demo", "ListViewActivity.onItemClick");
                NativeResponse nrAd = nrAdList.get(position);
                nrAd.handleClick(view);
            }
        });
        fetchAd(this);
    }

    public void showAdList() {
        listView.setAdapter(adapter);
    }

    public void fetchAd(Activity activity) {
        AdNative adNative = new AdNative(activity, YOUR_AD_PLACE_ID, new AdNativeNetworkListener() {

            @Override
            public void onNativeFail(NativeErrorCode arg0) {
                Log.w("ListViewActivity", "onNativeFail reason:" + arg0.name());
            }

            @Override
            public void onNativeLoad(List<NativeResponse> arg0) {
                // 一个广告只允许展现一次，多次展现、点击只会计入一次
                if (arg0 != null && arg0.size() > 0) {
                    nrAdList = arg0;
                    showAdList();
                }
            }

        });
        adNative.loadNativeAd();
    }

    class MyAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public MyAdapter(Context context) {
            super();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return nrAdList.size();
        }

        @Override
        public NativeResponse getItem(int position) {
            return nrAdList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NativeResponse nrAd = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.native_ad_row, null);
            }
            AQuery aq = new AQuery(convertView);
            aq.id(R.id.native_icon_image).image(nrAd.getIconUrl(), false, true);
            aq.id(R.id.native_main_image).image(nrAd.getImageUrl(), false, true);
            aq.id(R.id.native_text).text(nrAd.getDesc());
            aq.id(R.id.native_title).text(nrAd.getTitle());
            aq.id(R.id.native_brand_name).text(nrAd.getBrandName());
//            aq.id(R.id.native_adlogo).image(nrAd.getIconUrl(), false, true);
//            aq.id(R.id.native_baidulogo).image(nrAd.getBaiduLogoUrl(), false, true);
            String text = nrAd.isDownloadApp() ? "下载" : "查看";
            aq.id(R.id.native_cta).text(text);
            nrAd.recordImpression(convertView);
            return convertView;
        }

    }
}
