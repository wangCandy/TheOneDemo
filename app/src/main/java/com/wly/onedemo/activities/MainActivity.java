package com.wly.onedemo.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wly.onedemo.R;
import com.wly.onedemo.base.BaseActivity;
import com.wly.onedemo.fragment.MainFragment;
import com.wly.onedemo.fragment.MovieFragment;
import com.wly.onedemo.fragment.MusicFragment;
import com.wly.onedemo.fragment.ReadFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private RadioButton rb_home , rb_read , rb_music , rb_camera;
    private RadioGroup bottom;

    private Fragment mMain , mMusic , mMovie , mRead;

    public static final int MAIN = 0;
    public static final int MUSIC = 1;
    public static final int MOVIE = 2;
    public static final int READ = 3;
    private int currentIndex = MAIN;

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent() != null){
            currentIndex = getIntent().getIntExtra("index" , 0);
            Log.w(TAG, "onResume: currentIndex=" + currentIndex);
            selectItem(currentIndex);
        }else{
            selectItem(MAIN);
        }
    }

    public void selectItem(int index){
        if(bottom != null){
            bottom.getChildAt(index).performClick();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {
        bottom = (RadioGroup) findViewById(R.id.tabs_bottom);
        rb_home = (RadioButton) findViewById(R.id.tabs_home);
        rb_camera = (RadioButton) findViewById(R.id.tabs_camera);
        rb_music = (RadioButton) findViewById(R.id.tabs_music);
        rb_read = (RadioButton) findViewById(R.id.tabs_read);
    }

    @Override
    protected void setListener() {
        rb_music.setOnClickListener(this);
        rb_read.setOnClickListener(this);
        rb_camera.setOnClickListener(this);
        rb_home.setOnClickListener(this);
    }

    @Override
    protected void initialize() {

        initLeftSearchAction();
        initRightMineAction();
        hideBackAction();

        if(getIntent() != null){
            currentIndex = getIntent().getIntExtra("index" , 0);
            Log.w(TAG, "onResume: currentIndex=" + currentIndex);
            selectItem(currentIndex);
        }else{
            selectItem(MAIN);
        }
    }

    @Override
    protected boolean doBeforeClose() {
        return false;
    }

    @Override
    public void onClick(View view) {
        onSelected(view);
    }

    private void onSelected(View view){

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.tabs_home:
                hideFragments(ft , mRead , mMovie , mMusic);
                setTitle("ONE" , 24);
                setTitleStyle(true);
                if(mMain == null){
                    mMain = new MainFragment();
                    ft.add(R.id.fragment_contain , mMain , "MainFragment");
                }else{
                    ft.show(mMain);
                }
                break;
            case R.id.tabs_read:
                hideFragments(ft , mMain , mMusic , mMovie);
                setTitle("阅读" , 20);
                setTitleStyle(false);
                if(mRead == null){
                    mRead = new ReadFragment();
                    ft.add(R.id.fragment_contain , mRead , "ReadFragment");
                }else{
                    ft.show(mRead);
                }
                break;
            case R.id.tabs_music:
                hideFragments(ft , mMain , mMovie , mRead);
                setTitle("音乐" , 20);
                setTitleStyle(false);
                if(mMusic == null){
                    mMusic = new MusicFragment();
                    ft.add(R.id.fragment_contain , mMusic , "MusicFragment");
                }else{
                    ft.show(mMusic);
                }
                break;
            case R.id.tabs_camera:
                hideFragments(ft , mMain , mRead , mMusic);
                setTitle("影片" , 20);
                setTitleStyle(false);
                if(mMovie == null){
                    mMovie = new MovieFragment();
                    ft.add(R.id.fragment_contain , mMovie , "MovieFragment");
                }else{
                    ft.show(mMovie);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.w(TAG, "onNewIntent");
        super.onNewIntent(intent);
        if(getIntent() != null){
            currentIndex = getIntent().getIntExtra("index" , 0);
            selectItem(currentIndex);
        }
    }

    /**
     * 隐藏fragment
     *
     * @param transaction
     * @param hideragment 所有要隐藏的fragment
     */
    private void hideFragments(FragmentTransaction transaction, Fragment... hideragment) {
        for (Fragment fm : hideragment) {
            if (fm != null) {
                transaction.hide(fm);
            }
        }
    }
}
