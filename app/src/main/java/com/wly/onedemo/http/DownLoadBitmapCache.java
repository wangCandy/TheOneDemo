package com.wly.onedemo.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by Candy on 2016/11/7.
 */

public class DownLoadBitmapCache {

    private static final String TAG = DownLoadBitmapCache.class.getSimpleName();

    private LruCache<String , Bitmap> mMemoryCache;

    private static DownLoadBitmapCache mInstance = new DownLoadBitmapCache();

    private Bitmap mBitmap;

    private String mData;

    @SuppressLint("NewApi")
    public DownLoadBitmapCache(){

        final int mMaxMemory = (int) Runtime.getRuntime().maxMemory();
        Log.w(TAG, "DownLoadBitmapCache: maxMemory=" + mMaxMemory);
        final int cacheSize = mMaxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public static DownLoadBitmapCache getInstance(){
        return mInstance;
    }

    private void AddBitmapToMemory(String key , Bitmap bitmap){
        if(getMemoryByKey(key) == null){
            mMemoryCache.put(key , bitmap);
        }
    }

    private Bitmap getMemoryByKey(String key){
        return mMemoryCache.get(key);
    }

    //先读取缓存，没有记录则使用后台异步加载
    public Bitmap LoadBitmap(String url){
        if(getMemoryByKey(url) != null){
            return mMemoryCache.get(url);
        }else{
            return null;
        }
    }

    public void DownLoadBitmap(Context context , final String url , final ImageView imageView){

        Ion.with(context).load(url)
                .asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(Exception e, Bitmap result) {
                        if(result != null){
                            AddBitmapToMemory(url , result);
                            imageView.setImageBitmap(result);
                        }
                    }
                });
    }
}
