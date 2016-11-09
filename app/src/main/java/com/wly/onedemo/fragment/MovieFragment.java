package com.wly.onedemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wly.onedemo.activities.MovieDetailActivity;
import com.wly.onedemo.activities.TestActivity;
import com.wly.onedemo.base.BaseFragment;
import com.wly.onedemo.R;
import com.wly.onedemo.adapter.MovieRecyclerAdapter;
import com.wly.onedemo.bean.MovieBean;
import com.wly.onedemo.http.ApiHandler;
import com.wly.onedemo.http.DownLoadBitmapCache;
import com.wly.onedemo.http.UrlApiClient;
import com.wly.onedemo.http.UrlContant;
import com.wly.onedemo.utils.Gloables;
import com.wly.onedemo.utils.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.LogRecord;
import java.util.logging.SocketHandler;

/**
 * Created by Candy on 2016/10/24.
 */
public class MovieFragment extends BaseFragment implements XRecyclerView.LoadingListener{

    public XRecyclerView mMovieRecycler;

    public List<MovieBean> mMovies = new ArrayList<>();

    public MovieRecyclerAdapter mAdapter;

    public String movieId = null;

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<MovieBean> items = (List<MovieBean>)msg.obj;
            Log.w(TAG, "handleMessage: msg=" + msg.what);
            switch (msg.what){
                case 0:
                    //表示刷新
                    mMovies.clear();
                    mMovies.addAll(items);
                    break;
                case 1:
                    //表示第一次加载
                    mMovies.addAll(0 , items);
                    break;
                case 2:
                    //表示加载更多
                    mMovies.addAll(items);
                    break;
            }
            mMovieRecycler.reset();
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void findView() {
        mMovieRecycler = (XRecyclerView) convertView.findViewById(R.id.movie_list);
    }

    @Override
    protected void setOnListener() {

    }

    public void initRecycler(){
        mMovieRecycler.setItemAnimator(new DefaultItemAnimator());
        mMovieRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MovieRecyclerAdapter(mMovies , mContext);
        mAdapter.setItemClickListener(new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                MovieBean item = (MovieBean) mMovies.get(position - 1);
                Bundle bundle = new Bundle();
                bundle.putString("movie_id" , item.getId());
                bundle.putString("movie_title" , item.getTitle());
                Intent intent = new Intent(getContext() , MovieDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mMovieRecycler.setAdapter(mAdapter);
        mMovieRecycler.setLoadingListener(this);
//        mMovieRecycler.setRefreshing(true);
    }

    @Override
    protected void initialize() {
//        findMovieList("0" , 0);
        initRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        findMovieList("0" , 0);
    }

    public void findMovieList(final String id , final int UpOrDown){

        String mData = (String)fileUtil.readObjectFromLocal(mContext , Gloables.MOVIE_ID_LIST + id);
        if(mData != null){
            responseJsonData(mData , UpOrDown);
        }else{
            final String url = UrlContant.MOVIE_LIST + id + UrlContant.END_URL;
            UrlApiClient.sendRequest(getContext(), url , null, new ApiHandler() {
                @Override
                public void onSuccess(String date) {
                    responseJsonData(date , UpOrDown);
                    fileUtil.writeObjectIntoLocal(mContext , Gloables.MOVIE_ID_LIST + id, date);
                }
                @Override
                public void onFailure() {

                }
            });
        }
    }

    public void responseJsonData(String data , int UpOrDown){

        final List<Object> movieList = new ArrayList<Object>();
        try {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                MovieBean bean = (MovieBean) JsonUtil.fromJson(array.get(i).toString() , MovieBean.class);
                movieList.add(bean);
                if(i == array.length() - 1){
                    movieId = bean.getId();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Message msg = mHandler.obtainMessage();
        msg.what = UpOrDown;
        msg.obj = movieList;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {
        findMovieList("0" , 0);
        mMovieRecycler.reset();
    }

    @Override
    public void onLoadMore() {
        findMovieList(movieId , 2);
        mMovieRecycler.reset();
    }
}
