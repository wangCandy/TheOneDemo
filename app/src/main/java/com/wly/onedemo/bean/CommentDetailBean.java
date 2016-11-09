package com.wly.onedemo.bean;

import java.io.Serializable;

/**
 * Created by Candy on 2016/10/28.
 */
public class CommentDetailBean implements Serializable {


    /**
     * id : 34249
     * quote : 上午刚看完 还不错 一直喜欢杰森 男人都能被他崇拜 别说女人
     * content : 语文是数学老师教的
     * praisenum : 0
     * device_token :
     * del_flag : 0
     * reviewed : 1
     * user_info_id : 10089
     * input_date : 2016-11-01 09:01:56
     * created_at : 2016-11-01 09:01:56
     * updated_at : 2016-11-01 11:24:41
     * user : {"user_id":"5673102","user_name":"索朗蔚","web_url":"http://q.qlogo.cn/qqapp/1104596227/B056D31E90467218DEB29EC2798DC38E/40"}
     * touser : {"user_id":"7302209","user_name":"小董ddddddddddddx","web_url":"http://tva2.sinaimg.cn/crop.0.0.100.100.180/0067B41Ojw8es5unyoy4wj302s02sq2q.jpg"}
     * score : null
     * type : 1  0表示热门评论。 1 为普通评论。
     */

    private String id;
    private String quote;
    private String content;
    private int praisenum;
    private String device_token;
    private String del_flag;
    private String reviewed;
    private String user_info_id;
    private String input_date;
    private String created_at;
    private String updated_at;
    /**
     * user_id : 5673102
     * user_name : 索朗蔚
     * web_url : http://q.qlogo.cn/qqapp/1104596227/B056D31E90467218DEB29EC2798DC38E/40
     */

    private UserBean user;
    /**
     * user_id : 7302209
     * user_name : 小董ddddddddddddx
     * web_url : http://tva2.sinaimg.cn/crop.0.0.100.100.180/0067B41Ojw8es5unyoy4wj302s02sq2q.jpg
     */

    private UserBean touser;
    private Object score;
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getReviewed() {
        return reviewed;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public String getUser_info_id() {
        return user_info_id;
    }

    public void setUser_info_id(String user_info_id) {
        this.user_info_id = user_info_id;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserBean getTouser() {
        return touser;
    }

    public void setTouser(UserBean touser) {
        this.touser = touser;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
