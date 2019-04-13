package com.wly.onedemo.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wly.onedemo.R;
import com.wly.onedemo.utils.ImageUtil;

import java.util.List;

/**
 * Created by Candy on 2016/11/1.
 */
public class MovieImagePopupWindows extends PopupWindow {

    private Context mContext;

    private List<String> mImages;
    private int position;

    private View convertView;

    //测试添加

    private LinearLayout mContain;

    private ViewPager mMovieImageViewPager;

    public MovieImagePopupWindows(Context context , List<String> images , int position){
        this.mContext = context;
        this.mImages = images;
        this.position = position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.movie_image_popup  , null);

        initView();

        // 设置SelectPicPopupWindow的View
        this.setContentView(convertView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewPager.LayoutParams.MATCH_PARENT);

        this.setAnimationStyle(R.style.popup_movie_animation_style);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    public void initView(){
        mMovieImageViewPager = (ViewPager) convertView.findViewById(R.id.movie_images_view_pager);
        mContain = (LinearLayout) convertView.findViewById(R.id.ll_movie_images);
        mContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        MyViewPagerAdapter mAdapter = new MyViewPagerAdapter();
        mMovieImageViewPager.setAdapter(mAdapter);
        mMovieImageViewPager.setCurrentItem(position);
    }

    private class MyViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            View view = container.getChildAt(position);
            ((ViewPager)container).removeView(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView image = new ImageView(mContext);
            ImageUtil.loadImageUrl(mContext , mImages.get(position) , image);
            ((ViewPager) container).addView(image);
            return container;
        }
    }
}
