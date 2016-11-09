package com.wly.onedemo.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Candy on 2016/10/24.
 */
public class DisplayUtil {

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // last_update_date : 2016-10-21 09:06:53
    public static Date String2Date(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date currentDate = null;
        try {
            currentDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate;
    }
}
