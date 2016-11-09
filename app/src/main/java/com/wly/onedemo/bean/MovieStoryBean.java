package com.wly.onedemo.bean;

import java.io.Serializable;

/**
 * Created by Candy on 2016/10/27.
 */
public class MovieStoryBean implements Serializable{


    /**
     * id : 3128
     * movie_id : 145
     * title : 鸡汤喝多了会上火，靓汤看多了会上瘾
     * content : <br />
     2016年10月12日下午14点25分，我在上海电影博物馆见到了汤姆·克鲁斯。<br />
     <br />
     那一刻，我离阿汤哥只有不到30米的距离。<br />
     <br />
     那一天，发布会结束得很早，噪杂声中人群很快散去。大厅左侧的出口处，不到一米的距离，透过人群挤压后形成的狭小缝隙，我看到了阿汤哥标志性的笑容，他侧身搭着一个影迷在合影。<br />
     <br />
     <br />
     一秒钟不到的时间，一米不到的距离，这是我离巨星最近的一次。足以可以记一段时间了。<br />
     <br />
     <br />
     很多年前，久到阿汤哥来上海拍《碟中谍3》的时候。花絮里听到阿汤哥尚算标准的普通话，狂奔中的他边跑边喊着：让开、让开、走开、走开……<br />
     <br />
     那一年，他来上海拍戏，电视中看到一个小插曲。他随手拨通了一个工作人员他女朋友的手机号码，就这样那个女人糊里糊涂地跟好莱坞超级巨星打了通电话。<br />
     <br />
     羡慕这样的机会，也暗自觉得英语真的很重要，不然哪天真要接到了阿汤哥的电话，总不能光说Hi, Hello,It’s me吧。<br />
     <br />
     再后来，已经是去年的《碟中谍5》了。阿汤哥又一次来到中国，跟马云对谈，马云感叹了一句：一个男人怎么能帅到这个地步？是啊，连同性都认可的帅，那绝对是另一种程度的帅。<br />
     <br />
     其实，说了这么多，这些不过是我对上海这座城市的一段小小的记忆，碰巧细细想来，恰好有三件微小的事情与阿汤哥这位遥不可及的明星扯上了一星半点的关系。也正因为这些边角料，我去看了第一部《侠探杰克》。阿汤哥成功的商业动作片有很多，然而除了《碟中谍》系列，唯有这部《侠探杰克》获得了进一步的开发。为什么呢？直到看完，我依然没有搞明白。
     * user_id : 5670786
     * sort : 0
     * praisenum : 62
     * input_date : 2016-10-21 09:06:53
     * story_type : 1
     * user : {"user_id":"5670786","user_name":"正义联盟实习生","web_url":"http://image.wufazhuce.com/Fs1x1V7Ao1bTrB4tEm2WgTHVVu0Q"}
     */

    private String id;
    private String movie_id;
    private String title;
    private String content;
    private String user_id;
    private String sort;
    private int praisenum;
    private String input_date;
    private String story_type;
    private UserBean user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getStory_type() {
        return story_type;
    }

    public void setStory_type(String story_type) {
        this.story_type = story_type;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
