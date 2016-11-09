package com.wly.onedemo.bean;

import java.io.Serializable;

/**
 * Created by Candy on 2016/10/27.
 */
public class UserBean implements Serializable{


    /**
     * user_id : 5670786
     * user_name : 正义联盟实习生
     * web_url : http://image.wufazhuce.com/Fs1x1V7Ao1bTrB4tEm2WgTHVVu0Q
     */

    private String user_id;
    private String user_name;
    private String web_url;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }
}
