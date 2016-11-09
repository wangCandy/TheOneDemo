package com.wly.onedemo.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wly.onedemo.base.BaseFragment;
import com.wly.onedemo.R;
import com.wly.onedemo.bean.NewsBean;
import com.wly.onedemo.http.ApiHandler;
import com.wly.onedemo.http.DownLoadBitmapCache;
import com.wly.onedemo.http.UrlApiClient;
import com.wly.onedemo.http.UrlContant;
import com.wly.onedemo.utils.DisplayUtil;
import com.wly.onedemo.utils.FileUtil;
import com.wly.onedemo.utils.Gloables;
import com.wly.onedemo.utils.ImageUtil;
import com.wly.onedemo.utils.JsonUtil;
import com.wly.onedemo.view.TitleImagePopupWindows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Candy on 2016/10/21.
 */
public class NewsFragment extends BaseFragment {

    private static final String TAG = NewsFragment.class.getSimpleName();

    private ImageView mTitleImage;

    private TextView mTitle;

    private TextView mFavorite;

    private TextView mPeriodical;

    private TextView mAuthor;

    private TextView mCurrentTime;

    private LinearLayout mContainer;

    private String id;

    public final static NewsFragment newsInstance(String id){

        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("position" , id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void findView() {
        mTitleImage = (ImageView) convertView.findViewById(R.id.iv_title_image);
        mTitle = (TextView) convertView.findViewById(R.id.tv_title);
        mFavorite = (TextView) convertView.findViewById(R.id.tv_favorite);
        mPeriodical = (TextView) convertView.findViewById(R.id.tv_periodical);
        mAuthor = (TextView) convertView.findViewById(R.id.tv_author);
        mCurrentTime = (TextView) convertView.findViewById(R.id.tv_current_date);
        mContainer = (LinearLayout) convertView.findViewById(R.id.ll_news_fragment);
    }

    @Override
    protected void setOnListener() {
        mTitleImage.setOnClickListener(this);
    }

    @Override
    protected void initialize() {

        id = getArguments().getString("position");
        Log.w(TAG, "initialize: position=" + id);
        getNewsDetail(id);
    }

    public void getNewsDetail(final String id){

        NewsBean item = new FileUtil<NewsBean>().readObjectFromLocal(mContext , Gloables.HP_NEWS_DETAIL + id);
        if(item != null){
            initDate(item);
        }else{
            String detail_url = UrlContant.HP_NEWS_DETAIL + id + UrlContant.END_URL;
            UrlApiClient.sendRequest(mContext, detail_url, null, new ApiHandler() {
                @Override
                public void onSuccess(String date) {
                    NewsBean item = (NewsBean) JsonUtil.fromJson(date , NewsBean.class);
                    initDate(item);
                    fileUtil.writeObjectIntoLocal(mContext , Gloables.HP_NEWS_DETAIL + id , item);
                }
                @Override
                public void onFailure() {

                }
            });
        }
    }

    public void initDate(NewsBean item){
        Bitmap bitmap = DownLoadBitmapCache.getInstance().LoadBitmap(item.getHp_img_url());
        if(bitmap != null){
            //表示已缓存
            mTitleImage.setImageBitmap(bitmap);
        }else{
            DownLoadBitmapCache.getInstance().DownLoadBitmap(mContext , item.getHp_img_url() , mTitleImage);
        }
        mTitle.setText(item.getHp_content());
        mPeriodical.setText(item.getHp_title());
        mFavorite.setText(String.valueOf(item.getPraisenum()));
        mAuthor.setText(item.getHp_author());

        Date date = DisplayUtil.String2Date(item.getHp_makettime());
        SimpleDateFormat format = new SimpleDateFormat("EEE dd MMM.yyyy" , Locale.ENGLISH);
        mCurrentTime.setText(format.format(date));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_title_image:
                Drawable drawable = mTitleImage.getDrawable();
                TitleImagePopupWindows popup = new TitleImagePopupWindows(getContext() , drawable);
                popup.showAtLocation(mContainer , Gravity.CENTER , 0 , 0);
                break;
        }
    }
}
