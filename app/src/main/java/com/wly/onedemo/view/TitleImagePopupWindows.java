package com.wly.onedemo.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ViewAnimator;

import com.wly.onedemo.R;

/**
 * Created by Candy on 2016/10/24.
 */
public class TitleImagePopupWindows extends PopupWindow{

    private Context mContext;

    private Drawable bitmap;

    private View convertView;

    private LinearLayout mContain;

    private ImageView mImage;

    public TitleImagePopupWindows(Context context , Drawable bitmap){
        this.mContext = context;
        this.bitmap = bitmap;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.title_image_poppup  , null);

        initView();


        Animation animation = AnimationUtils.loadAnimation(mContext , R.anim.popup_enter_animation);
        mImage.setAnimation(animation);
        animation.start();

        // 设置SelectPicPopupWindow的View
        this.setContentView(convertView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewPager.LayoutParams.MATCH_PARENT);

        this.setAnimationStyle(R.style.popup_animation_style);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    public void initView(){
        mImage = (ImageView) convertView.findViewById(R.id.iv_image);
        mImage.setImageDrawable(bitmap);

        mContain = (LinearLayout) convertView.findViewById(R.id.ll_image_contain);
        mContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
