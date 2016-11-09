package com.wly.onedemo.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wly.onedemo.OneAppConfig;
import com.wly.onedemo.base.BaseActivity;
import com.wly.onedemo.R;
import com.wly.onedemo.bean.MovieDetailBean;
import com.wly.onedemo.fragment.MovieInfoFragment;
import com.wly.onedemo.bean.MovieStoryBean;
import com.wly.onedemo.bean.UserBean;
import com.wly.onedemo.fragment.CommentFragment;
import com.wly.onedemo.http.ApiHandler;
import com.wly.onedemo.http.DownLoadBitmapCache;
import com.wly.onedemo.http.UrlApiClient;
import com.wly.onedemo.http.UrlContant;
import com.wly.onedemo.utils.DisplayUtil;
import com.wly.onedemo.utils.ImageUtil;
import com.wly.onedemo.utils.JsonUtil;
import com.wly.onedemo.view.BottomScrollView;
import com.wly.onedemo.view.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends BaseActivity{

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private ImageView mTitleImage;
    private ImageView mShareImage;
    private TextView mGradeScore;
    private LinearLayout mShareTypeContain;
    private TextView mScoreShare , mOtherShare;
    private TextView mScore;

    private CircleImageView mAuthorAvatar;
    private TextView mAuthorName;
    private TextView mEssayDate;
    private TextView mAuthorDetail;

    private TextView mFavoriteCount;

    private TextView mMovieStory;
    private TextView mMovieStoryTitle;

    private String id;
    private String title;

    private DownLoadBitmapCache cache;
    private BottomScrollView mMovieDetailContain;

    private RelativeLayout mLoadMore;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void findView() {

        mMovieDetailContain = (BottomScrollView) findViewById(R.id.scroll_movie_detail);

        mLoadMore = (RelativeLayout) findViewById(R.id.rl_loadMore);

        mTitleImage = (ImageView) findViewById(R.id.iv_movie_title_image);
        mShareImage = (ImageView) findViewById(R.id.iv_movie_share);
        mShareTypeContain = (LinearLayout) findViewById(R.id.ll_movie_share_method);

        mGradeScore = (TextView) findViewById(R.id.tv_grade_score);

        mScoreShare = (TextView) findViewById(R.id.tv_grade_score);
        mOtherShare = (TextView) findViewById(R.id.tv_movie_other_share);
        mScore = (TextView) findViewById(R.id.tv_movie_score);

        mAuthorAvatar = (CircleImageView) findViewById(R.id.iv_author_avatar);
        mAuthorName = (TextView) findViewById(R.id.tv_author_name);
        mAuthorDetail = (TextView) findViewById(R.id.tv_author_detail);
        mEssayDate = (TextView) findViewById(R.id.tv_essay_date);
        mFavoriteCount = (TextView) findViewById(R.id.tv_essay_favorite);

        mMovieStory = (TextView) findViewById(R.id.tv_movie_story_detail);
        mMovieStoryTitle = (TextView) findViewById(R.id.tv_movie_story_title);
    }

    @Override
    protected void setListener() {
        mShareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareImage.setVisibility(View.GONE);

                Animation animator2 = AnimationUtils.loadAnimation(MovieDetailActivity.this , R.anim.movie_share_animation);
                mGradeScore.setAnimation(animator2);
                animator2.start();

                Animation animator = AnimationUtils.loadAnimation(MovieDetailActivity.this , R.anim.movie_share_animation);
                mShareTypeContain.setAnimation(animator);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();
                mShareTypeContain.setVisibility(View.VISIBLE);
            }
        });

        mMovieDetailContain.setOnScrollToBottom(new BottomScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollToBottom(boolean scrollToBottom) {
                if (scrollToBottom) {
                    if(OneAppConfig.isNetworkAvailable(MovieDetailActivity.this)){
                        mLoadMore.setVisibility(View.VISIBLE);
                    }else{
                        mLoadMore.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected void initialize() {

        cache = DownLoadBitmapCache.getInstance();

        id = getIntent().getStringExtra("movie_id");
        title = getIntent().getStringExtra("movie_title");

        hideSearchAction();
        hideRightMineAction();

        initLeftBackAction();

        setTitle(title , 20);
        setTitleStyle(false);

        getDate();
        getStoryData();
    }

    @Override
    protected boolean doBeforeClose() {

        Log.w(TAG, "doBeforeClose");
        goHome(3);
        return true;
    }

    private void getDate(){
        String url = UrlContant.MOVIE_DETAIL + id + UrlContant.END_URL;
        UrlApiClient.sendRequest(this, url, null, new ApiHandler() {
            @Override
            public void onSuccess(String date) {
                MovieDetailBean item = (MovieDetailBean) JsonUtil.fromJson(date , MovieDetailBean.class);
                initDate(item);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void getStoryData(){
        String url = UrlContant.MOVIE_BASE + id + UrlContant.MOVIE_STORY_AUTHOR;
        Log.d(TAG, "getStoryData: url=" + url);
        UrlApiClient.sendRequest(this, url, null, new ApiHandler() {
            @Override
            public void onSuccess(String date) {
                try {
                    JSONObject object = new JSONObject(date);
                    JSONArray array = object.getJSONArray("data");
                    MovieStoryBean bean = (MovieStoryBean) JsonUtil.fromJson(array.get(0).toString() , MovieStoryBean.class);
                    initMovieStory(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void initMovieStory(MovieStoryBean item){

        UserBean userInfo = item.getUser();
        Bitmap bitmap = cache.LoadBitmap(userInfo.getWeb_url());
        if(bitmap != null){
            mAuthorAvatar.setImageBitmap(bitmap);
        }else{
            cache.DownLoadBitmap(this , userInfo.getWeb_url() , mAuthorAvatar);
        }
        mAuthorName.setText(userInfo.getUser_name());

        mAuthorDetail.setVisibility(View.GONE);

        SimpleDateFormat format = new SimpleDateFormat("MMM dd.yyyy" , Locale.ENGLISH);
        Date date = DisplayUtil.String2Date(item.getInput_date());
        mEssayDate.setText(format.format(date));

        mMovieStory.setText(Html.fromHtml(item.getContent()));
        mMovieStoryTitle.setText(item.getTitle());

        mFavoriteCount.setText(String.valueOf(item.getPraisenum()));
    }

    public void initDate(MovieDetailBean item){

        //加载图片
        Bitmap bitmap = cache.LoadBitmap(item.getDetailcover());
        if(bitmap != null){
            mTitleImage.setImageBitmap(bitmap);
        }else{
            cache.DownLoadBitmap(this , item.getDetailcover() , mTitleImage);
        }

        mScore.setText(item.getScore());
        mScore.setTextColor(Color.RED);
        Typeface face = Typeface.createFromAsset(getAssets() , "fonts/segoepr.ttf");
        mScore.setTypeface(face);

        //设置电影信息
        mFragmentManager.beginTransaction().replace(R.id.fragment_movie_info , MovieInfoFragment.newInstance(item)).commitAllowingStateLoss();
    }
}
