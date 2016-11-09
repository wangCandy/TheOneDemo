package com.wly.onedemo.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Candy on 2016/10/25.
 */
public class JsonUtil {

    /**
     * 解析数组数据
     *
     * @param json
     * @param itemClazz
     * @return
     */
    public static List<Object> fromArrayJson(String json, Class<?> itemClazz) {
        List<Object> items = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            Log.e("JsonUtil-->>", e.toString());
        }
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = null;
                try {
                    item = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    Log.e("JsonUtil-->>", e.toString());
                }
                if (item != null) {
                    Object obj = null;
                    Gson gson = new GsonBuilder()
                            .disableHtmlEscaping()
                            .create();
                    try {
                        obj = gson.fromJson(item.toString(), itemClazz);
                        items.add(obj);
                    } catch (JsonSyntaxException e) {
                        Log.e("JsonUtil-->>", e.toString());
                        try {
                            obj = itemClazz.newInstance();
                        } catch (InstantiationException e1) {
                        } catch (IllegalAccessException e1) {
                        }
                    }

                }
            }

        }
        return items;
    }


    /**
     * 解析实体数据
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object fromJson(String json, Class<?> clazz) {

        Object obj = null;
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .create();
        try {
            obj = gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            Log.e("JsonUtil-->>", e.toString());
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException e1) {
            } catch (IllegalAccessException e1) {
            }
            return obj;
        }
        return obj;

    }
}
