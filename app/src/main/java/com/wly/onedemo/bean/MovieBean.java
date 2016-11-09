package com.wly.onedemo.bean;

import java.io.Serializable;

/**
 * Created by Candy on 2016/10/25.
 */
public class MovieBean implements Serializable {


    /**
     * id : 145
     * title : 侠探杰克：永不回头
     * verse :
     * verse_en :
     * score : 73
     * revisedscore : 0
     * releasetime : 2016-10-21 00:00:00
     * scoretime : 2016-10-22 00:00:00
     * cover : http://image.wufazhuce.com/FrN3ohkTuop315TPw8_3JR3-Sj4u
     * servertime : 1477360277
     */

    private String id;
    private String title;
    private String verse;
    private String verse_en;
    private String score;
    private String revisedscore;
    private String releasetime;
    private String scoretime;
    private String cover;
    private int servertime;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getServertime() {
        return servertime;
    }

    public void setServertime(int servertime) {
        this.servertime = servertime;
    }
}
