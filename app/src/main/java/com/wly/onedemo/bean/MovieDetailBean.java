package com.wly.onedemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Candy on 2016/10/26.
 */
public class MovieDetailBean implements Serializable{


    /**
     * id : 145
     * title : 侠探杰克：永不回头
     * indexcover : http://image.wufazhuce.com/FrN3ohkTuop315TPw8_3JR3-Sj4u
     * detailcover : http://image.wufazhuce.com/FhgeR8_8LowCbw4rC9USLReCsuKo
     * video : http://music.wufazhuce.com/luZ6mb1WYpzcAwHOPJk28WKs1C7K
     * verse :
     * verse_en :
     * score : 73
     * revisedscore : 0
     * review : 当前用户评分：73,
     * keywords : 中国人民的好朋友;阿汤哥;罗宾阿姨;系列小说;女跟班
     * movie_id : 308231362
     * info : 导演: 爱德华·兹威克
     编剧: 马歇尔·赫斯科维兹/理查德·温克/爱德华·兹威克/李查德
     主演: 汤姆·克鲁斯/寇碧·史莫德斯/罗伯特·克耐普/达妮卡·亚罗什
     类型: 剧情/动作/悬疑/惊悚/犯罪
     制片国家/地区: 美国
     * officialstory : 杰克重回弗吉尼亚州的军事基地和苏珊·特纳少校会面，要解决一些个人问题。此时，特纳却意外被捕。杰克同时也陷入谜团，因为他被诬告犯有重罪，而且牵涉到一个自己完全不知道的小孩。这些，杰克都记不起来了。他要客服重重困难，去寻找特纳，最终解决问题。
     * charge_edt : （责任编辑：王素）
     * web_url : http://m.wufazhuce.com/movie/145
     * praisenum : 0
     * sort : 38
     * releasetime : 2016-10-21 00:00:00
     * scoretime : 2016-10-22 00:00:00
     * maketime : 2016-10-20 17:21:00
     * last_update_date : 2016-10-21 09:06:53
     * read_num : 21001
     * photo : ["http://image.wufazhuce.com/FsdptFjOzOLYuU96fzbh3NYeTNv3","http://image.wufazhuce.com/Fgtqqgkq2rE_3zunvOCoP7ArEJXw","http://image.wufazhuce.com/Fqt3KzoEzXg1QPCbPhbJgHD9b90p","http://image.wufazhuce.com/FvqKsALVpmJJSKe3HcqFhIuVPA8-","http://image.wufazhuce.com/FpuDQtHWmisn4DJrb4dvfEqfkKMz"]
     * sharenum : 18
     * commentnum : 26
     * servertime : 1477452825
     */

    private String id;
    private String title;
    private String indexcover;
    private String detailcover;
    private String video;
    private String verse;
    private String verse_en;
    private String score;
    private String revisedscore;
    private String review;
    private String keywords;
    private String movie_id;
    private String info;
    private String officialstory;
    private String charge_edt;
    private String web_url;
    private int praisenum;
    private String sort;
    private String releasetime;
    private String scoretime;
    private String maketime;
    private String last_update_date;
    private String read_num;
    private int sharenum;
    private int commentnum;
    private int servertime;
    private List<String> photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndexcover() {
        return indexcover;
    }

    public void setIndexcover(String indexcover) {
        this.indexcover = indexcover;
    }

    public String getDetailcover() {
        return detailcover;
    }

    public void setDetailcover(String detailcover) {
        this.detailcover = detailcover;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getVerse_en() {
        return verse_en;
    }

    public void setVerse_en(String verse_en) {
        this.verse_en = verse_en;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRevisedscore() {
        return revisedscore;
    }

    public void setRevisedscore(String revisedscore) {
        this.revisedscore = revisedscore;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOfficialstory() {
        return officialstory;
    }

    public void setOfficialstory(String officialstory) {
        this.officialstory = officialstory;
    }

    public String getCharge_edt() {
        return charge_edt;
    }

    public void setCharge_edt(String charge_edt) {
        this.charge_edt = charge_edt;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }

    public String getScoretime() {
        return scoretime;
    }

    public void setScoretime(String scoretime) {
        this.scoretime = scoretime;
    }

    public String getMaketime() {
        return maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public int getSharenum() {
        return sharenum;
    }

    public void setSharenum(int sharenum) {
        this.sharenum = sharenum;
    }

    public int getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }

    public int getServertime() {
        return servertime;
    }

    public void setServertime(int servertime) {
        this.servertime = servertime;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }
}
