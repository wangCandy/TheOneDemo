package com.wly.onedemo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wly.onedemo.R;
import com.wly.onedemo.base.BaseFragment;
import com.wly.onedemo.bean.MovieDetailBean;
import com.wly.onedemo.http.DownLoadBitmapCache;
import com.wly.onedemo.utils.DisplayUtil;
import com.wly.onedemo.utils.ImageUtil;
import com.wly.onedemo.view.MovieImagePopupWindows;

import java.util.List;

import me.next.tagview.TagCloudView;

/**
 * Created by Candy on 2016/10/28.
 */
public class MovieInfoFragment extends BaseFragment {

    private TextView mMovieInfoTitle;
    private RadioGroup rg;
    private RadioButton rb_Movie , rb_Album , rb_MovieInfo;
    private TextView mMovieInfo;

    private MovieDetailBean mMovieDetail;
    private TagCloudView mKeyWordContain;
    private HorizontalScrollView mAlbumContain;

    private LinearLayout mAlbum;

    public static final MovieInfoFragment newInstance(MovieDetailBean item){
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie_detail" , item);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_info;
    }

    @Override
    protected void findView() {

        mMovieInfoTitle = (TextView) convertView.findViewById(R.id.tv_movie_info_title);

        rg = (RadioGroup) convertView .findViewById(R.id.rg_movie_info);

        rb_Album = (RadioButton) convertView.findViewById(R.id.tabs_movie_album);
        rb_Movie = (RadioButton) convertView.findViewById(R.id.tabs_movie);
        rb_MovieInfo = (RadioButton) convertView.findViewById(R.id.tabs_movie_info);

        mMovieInfo = (TextView) convertView.findViewById(R.id.tv_movie_info);
        mKeyWordContain = (TagCloudView) convertView.findViewById(R.id.keyword_tags);
        mAlbumContain = (HorizontalScrollView) convertView.findViewById(R.id.scroll_album);
        mAlbum = (LinearLayout) convertView.findViewById(R.id.ll_album);
    }

    @Override
    protected void setOnListener() {
        rb_MovieInfo.setOnClickListener(this);
        rb_Movie.setOnClickListener(this);
        rb_Album.setOnClickListener(this);
    }

    @Override
    protected void initialize() {
        mMovieDetail = (MovieDetailBean) getArguments().getSerializable("movie_detail");
        selectItem(0);
    }

    public void selectItem(int index){
        if(rg != null){
            rg.getChildAt(index).performClick();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tabs_movie:
                //TODO:显示电影标签
                mMovieInfo.setVisibility(View.GONE);
                mAlbumContain.setVisibility(View.GONE);
                mKeyWordContain.setVisibility(View.VISIBLE);
                mMovieInfoTitle.setText(R.string.movie);
                String[] keyWords = getTags(mMovieDetail.getKeywords());
                setTags(keyWords);
                break;
            case R.id.tabs_movie_album:
                mMovieInfo.setVisibility(View.GONE);
                mKeyWordContain.setVisibility(View.GONE);
                mAlbumContain.setVisibility(View.VISIBLE);
                mMovieInfoTitle.setText(R.string.movie_album);
                //TODO: 显示剧照
                setAlbum(mMovieDetail.getPhoto());
                break;
            case R.id.tabs_movie_info:
                mKeyWordContain.setVisibility(View.GONE);
                mAlbumContain.setVisibility(View.GONE);
                mMovieInfo.setVisibility(View.VISIBLE);
                mMovieInfoTitle.setText(R.string.movie_info);
                mMovieInfo.setText(mMovieDetail.getInfo());
                break;
        }
    }

    private void setAlbum(final List<String> images){
        mAlbum.removeAllViews();
        if(images == null){
            return;
        }else{
            for (int i = 0 ; i < images.size() ; i++){
                ImageView photo = new ImageView(getContext());
                photo.setScaleType(ImageView.ScaleType.FIT_XY);
                photo.setAdjustViewBounds(true);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DisplayUtil.dip2px(getContext() , 120) , DisplayUtil.dip2px(getContext() , 120));
                lp.setMargins(DisplayUtil.dip2px(getContext() , 2) , DisplayUtil.dip2px(getContext() , 2) , DisplayUtil.dip2px(getContext() , 2) , DisplayUtil.dip2px(getContext() , 2));
                photo.setLayoutParams(lp);
//                ImageUtil.loadImageUrl(getContext() , images.get(i) , photo);
                Bitmap bitmap = DownLoadBitmapCache.getInstance().LoadBitmap(images.get(i));
                if(bitmap != null){
                    photo.setImageBitmap(bitmap);
                }else{
                    DownLoadBitmapCache.getInstance().DownLoadBitmap(mContext , images.get(i) , photo);
                }
                mAlbum.addView(photo);
            }
        }
    }

    private void setTags(String[] keywords){
        mKeyWordContain.removeAllViews();
        if(keywords != null){
            for (String keyword : keywords){
                TextView tag = new TextView(getContext());
                tag.setBackgroundResource(R.drawable.keywords_background);
                tag.setPadding(DisplayUtil.dip2px(getContext() , 25), DisplayUtil.dip2px(getContext() , 20) , DisplayUtil.dip2px(getContext() , 25) , DisplayUtil.dip2px(getContext() , 20));
                tag.setTextColor(getResources().getColor(R.color.tabs_selector_color));
                tag.setText(keyword);
                mKeyWordContain.addView(tag);
            }
        }
    }

    private String[] getTags(String tags){

        String[] mTags = null;
        if (tags == null) {
            return null;
        }else{
            mTags = tags.split("\\;");
            for (int i = 0; i < mTags.length; i++) {
                Log.w(TAG, "getTags: tag=" + mTags[i] );
            }
            return mTags;
        }
    }
}
