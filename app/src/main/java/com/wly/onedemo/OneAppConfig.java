package com.wly.onedemo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wly.onedemo.utils.Gloables;

/**
 * Created by Candy on 2016/11/7.
 */

public class OneAppConfig extends Application {

    public static OneAppConfig mInstance;

    /**
     * 配置缓存
     */
    private SharedPreferences mShare;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mShare = getSharedPreferences(Gloables.SHARE_PRE , 0);
    }

    public boolean isFirst(){
        Boolean isFirst = mShare.getBoolean(Gloables.IS_FIRST , true);
        if (isFirst) {// 第一次
            mShare.edit().putBoolean(Gloables.IS_FIRST , false).commit();
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    public static boolean isWifiConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static String getVersionName(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName() , 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName() , 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
