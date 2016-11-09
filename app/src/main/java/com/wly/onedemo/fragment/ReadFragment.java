package com.wly.onedemo.fragment;

import android.view.View;

import com.wly.onedemo.base.BaseFragment;
import com.wly.onedemo.R;
import com.wly.onedemo.bean.ReadingCarousalBean;
import com.wly.onedemo.http.ApiHandler;
import com.wly.onedemo.http.UrlApiClient;
import com.wly.onedemo.http.UrlContant;
import com.wly.onedemo.utils.JsonUtil;
import com.wly.onedemo.view.LoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Candy on 2016/10/24.
 */
public class ReadFragment extends BaseFragment {

    private List<Object> mCarousalBeen;

    private LoadingView mLoading;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void findView() {
        mLoading = (LoadingView) convertView.findViewById(R.id.loading);
        mLoading.start();
    }

    @Override
    protected void setOnListener() {

    }

    @Override
    protected void initialize() {

        getCarousel();
    }

    //获取轮播图信息
    public void getCarousel(){
        UrlApiClient.sendRequest(mContext, UrlContant.READING_CAROUSEL , null, new ApiHandler() {
            @Override
            public void onSuccess(String date) {
                mCarousalBeen = new ArrayList<Object>();
                mCarousalBeen = JsonUtil.fromArrayJson(date , ReadingCarousalBean.class);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
