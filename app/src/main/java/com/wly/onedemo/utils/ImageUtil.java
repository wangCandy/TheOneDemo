package com.wly.onedemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wly.onedemo.R;

/**
 * Created by Candy on 2016/10/20.
 */
public class ImageUtil {

    public static void loadImageUrl(Context context , String url , ImageView view){

        Ion.with(context).load(url)
                .withBitmap()
                .intoImageView(view);
    }

    public static void loadAvatarImageUrl(Context context , String url , ImageView view){
        Ion.with(context).load(url)
                .withBitmap()
                .placeholder(R.mipmap.shopping_cart_not_logined)
                .error(R.mipmap.shopping_cart_not_logined)
                .intoImageView(view);
    }

    public static void loadOriginalImage(Context context , String url  , FutureCallback<Bitmap> callback){
        Ion.with(context).load(url)
                .asBitmap()
                .setCallback(callback);
    }
}
