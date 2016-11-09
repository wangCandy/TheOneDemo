package com.wly.onedemo.http;

import com.wly.onedemo.BuildConfig;

/**
 * Created by Candy on 2016/10/20.
 */
public class UrlContant {

    //BaseUrl = "http://v3.wufazhuce.com:8000/api/"

    public static final String ONE_NEWS = BuildConfig.BASE_URL + "hp/bymonth/";

    public static final String END_URL = "?platform=android&version=3.5.0";

    public static final String HP_ID_LIST = BuildConfig.BASE_URL + "hp/idlist/0" + END_URL;

    public static final String HP_NEWS_DETAIL = BuildConfig.BASE_URL + "hp/detail/";

    public static final String HP_UPDATE = BuildConfig.BASE_URL + "hp/update/";

    public static final String MOVIE_BASE = BuildConfig.BASE_URL + "movie/";

    public static final String MOVIE_LIST =  MOVIE_BASE + "list/";

    public static final String MOVIE_DETAIL = MOVIE_BASE + "detail/";

    public static final String MOVIE_STORY_AUTHOR = "/story/1/0" + END_URL;

    public static final String READING_BASE = BuildConfig.BASE_URL + "reading/";

    public static final String READING_INDEX = READING_BASE + "index/" + END_URL;

    public static final String READING_CAROUSEL = READING_BASE + "carousel/" + END_URL;

    public static final String COMMENT = BuildConfig.BASE_URL + "comment/praiseandtime/";
}