package com.wly.onedemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wly.onedemo.R;
import com.wly.onedemo.adapter.CommentAdapter;
import com.wly.onedemo.base.BaseFragment;
import com.wly.onedemo.bean.CommentDetailBean;
import com.wly.onedemo.bean.MovieDetailBean;
import com.wly.onedemo.http.ApiHandler;
import com.wly.onedemo.http.UrlApiClient;
import com.wly.onedemo.http.UrlContant;
import com.wly.onedemo.utils.JsonUtil;
import com.wly.onedemo.view.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Candy on 2016/10/28.
 */
public class CommentFragment extends BaseFragment {

    private XRecyclerView mCommentListLayout;

    private String type;
    private MovieDetailBean item;
    private LinearLayout mCommentContain;

    private List<Object> mComments;

    public static final CommentFragment newInstance(String type , MovieDetailBean item){
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie_detail" , item);
        bundle.putString("comment_type" , type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void findView() {

        mCommentContain = (LinearLayout) convertView.findViewById(R.id.ll_comment_layout);
        mCommentListLayout = (XRecyclerView) convertView.findViewById(R.id.recycler_comment_list);
        TextView textView = new TextView(getContext());
        textView.setText("评论列表");
        textView.setTextSize(18);
        mCommentListLayout.addHeaderView(textView);
        mCommentListLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommentListLayout.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void setOnListener() {

    }

    @Override
    protected void initialize() {
        type = getArguments().getString("comment_type");
        item = (MovieDetailBean) getArguments().getSerializable("movie_detail");

        initData();
    }

    private void initData(){
        switch (type){
            case "movie":
                String url = UrlContant.COMMENT + "movie/" + item.getId() + "/0" + UrlContant.END_URL;
                Log.d(TAG, "initUrl: url=" + url);
                getCommentData(url);
                break;
            default:
                break;

        }
    }

    private void getCommentData(String url){
        UrlApiClient.sendRequest(getContext(), url, null, new ApiHandler() {
            @Override
            public void onSuccess(String date) {
                if(date != null){
                    mCommentContain.setVisibility(View.VISIBLE);
                    mComments = new ArrayList<Object>();
                    try {
                        JSONObject object = new JSONObject(date);
                        mComments = JsonUtil.fromArrayJson(object.getJSONArray("data").toString() , CommentDetailBean.class);
                        CommentAdapter adapter = new CommentAdapter(mComments , getContext());
                        mCommentListLayout.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    mCommentContain.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
