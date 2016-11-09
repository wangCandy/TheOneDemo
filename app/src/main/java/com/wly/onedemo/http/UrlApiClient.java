package com.wly.onedemo.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wly.onedemo.OneAppConfig;

import java.util.Map;

/**
 * Created by Candy on 2016/10/20.
 */
public class UrlApiClient {

    private static final String TAG = UrlApiClient.class.getSimpleName();
    private static final int TIME_OUT = 10 * 1000;
    private static final String METHOD = "GET";

    private static final String RES = "res";

    private static final String DATA = "data";

    private static final String CODE0 = "0";

    public static void sendRequest(Context context , String url , Map<String , String> map , final ApiHandler handler){

        if(!OneAppConfig.mInstance.isNetworkAvailable(context)){
            //无网络
            handler.onFailure();
        }else{
            Log.w(TAG, "sendRequest: 请求网址->>url=" + url);
            Ion.with(context)
                    .load( url)
                    .setTimeout(TIME_OUT)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            processData(result , handler);
                        }
                    });
        }
    }

    public static void processData(JsonObject result , ApiHandler handler){

        if(result == null){
            return;
        }
        String code;
        try{
            code = result.get(RES).getAsString();
        }catch (Exception e1){
            return;
        }
        switch (code){
            case CODE0:
                if(result.has(DATA)){
                    String data = result.get(DATA).toString();
                    Log.d(TAG, "processData: 响应数据->>" + data);
                    handler.onSuccess(data);
                }else{
                    return;
                }
                break;
            default:
                break;
        }
    }
}
