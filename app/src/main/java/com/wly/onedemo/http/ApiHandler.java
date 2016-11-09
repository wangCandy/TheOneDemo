package com.wly.onedemo.http;

/**
 * Created by Candy on 2016/10/20.
 */
public interface ApiHandler {

    void onSuccess(String date);

    void onFailure();
}
