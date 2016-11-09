package com.wly.onedemo.fragment;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.wly.onedemo.base.BaseFragment;
import com.wly.onedemo.R;
import com.wly.onedemo.adapter.NewsPagerAdapter;
import com.wly.onedemo.bean.NewsBean;
import com.wly.onedemo.http.ApiHandler;
import com.wly.onedemo.http.DownLoadBitmapCache;
import com.wly.onedemo.http.UrlApiClient;
import com.wly.onedemo.http.UrlContant;
import com.wly.onedemo.utils.FileUtil;
import com.wly.onedemo.utils.Gloables;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Candy on 2016/10/20.
 */
public class MainFragment extends BaseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private ViewPager mPager;
    private NewsPagerAdapter mAdapter;

    public List<NewsBean> newsData = new ArrayList<NewsBean>();
    public List<String> idList;
    public DownLoadBitmapCache cache;

    /**
     * 是否为第一页
     */
    public boolean isFirstPager = false;
    /**
     * 是否为最后一页
     */
    public boolean isLastPager = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void findView() {
        mPager = (ViewPager) convertView.findViewById(R.id.view_pager);
    }

    @Override
    protected void setOnListener() {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isFirstPager = false;
                        isLastPager = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isLastPager = true;
                        isFirstPager = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if(!isLastPager && )
                }
            }
        });
    }

    public void refresh(){

    }

    public void loadMore(){

    }

    @Override
    protected void initialize() {
        cache = DownLoadBitmapCache.getInstance();
        getNewsIDList();
    }

    public void getNewsIDList(){

        final String mData = new FileUtil<String>().readObjectFromLocal(mContext , Gloables.HP_NEWS_ID);
        if(!TextUtils.isEmpty(mData)){
            respondJsonData(mData);
        }else{
            UrlApiClient.sendRequest(getContext(), UrlContant.HP_ID_LIST , null, new ApiHandler() {
                @Override
                public void onSuccess(String date) {
                    respondJsonData(date);
                    fileUtil.writeObjectIntoLocal(mContext , Gloables.HP_NEWS_ID, date);
                }
                @Override
                public void onFailure() {

                }
            });
        }

    }

    public void respondJsonData(String mData){
        idList = new ArrayList<String>();
        try {
            JSONArray array = new JSONArray(mData);
            for (int i = 0; i < array.length(); i++) {
                idList.add(i , array.getString(i).trim());
            }
            mAdapter = new NewsPagerAdapter(getActivity().getSupportFragmentManager() , idList);
            mPager.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

    }
}
