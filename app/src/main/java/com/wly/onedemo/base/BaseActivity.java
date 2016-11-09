package com.wly.onedemo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wly.onedemo.R;
import com.wly.onedemo.activities.MainActivity;

/**
 * Created by Candy on 2016/10/25.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private TextView mTitle;

    private ImageView mLeftSearchAction;

    private ImageView mLeftBackAction;

    private ImageView mRightMineAction;

    public FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(this.getLayoutId());

        mFragmentManager = getSupportFragmentManager();

        this.findView();
        this.setListener();
        this.initialize();
    }

    protected abstract int getLayoutId();

    protected abstract void findView();

    protected abstract void setListener();

    protected abstract void initialize();

    /**
     * 关闭页面要做的事情
     * retrun true 不关掉页面
     * false 关掉页面
     */
    protected abstract boolean doBeforeClose();

    //设置标题
    public void setTitle(String title , int textSize){

        if(mTitle == null){
            mTitle = (TextView) findViewById(R.id.tv_main_title);
        }
        mTitle.setText(title);
        mTitle.setTextSize(textSize);
    }

    //设置标题为粗体
    public void setTitleStyle(boolean isBold){
        if(mTitle == null){
            mTitle = (TextView) findViewById(R.id.tv_main_title);
        }
        TextPaint paint = mTitle.getPaint();
        paint.setFakeBoldText(isBold);
    }

    //初始化左边的搜索按钮
    public void initLeftSearchAction(){

        if(mLeftSearchAction == null){
            mLeftSearchAction = (ImageView) findViewById(R.id.iv_search);
        }
        mLeftSearchAction.setVisibility(View.VISIBLE);

        //TODO:进入搜索界面
        mLeftSearchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BaseActivity.this , "搜索" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    //隐藏搜索按钮
    public void hideSearchAction(){
        if(mLeftSearchAction != null){
            mLeftSearchAction.setVisibility(View.GONE);
        }else{
            mLeftSearchAction = (ImageView) findViewById(R.id.iv_search);
            mLeftSearchAction.setVisibility(View.GONE);
        }
    }

    //初始化右边个人信息按钮
    public void initRightMineAction(){

        if (mRightMineAction == null) {
            mRightMineAction = (ImageView) findViewById(R.id.iv_mine);
        }
        mRightMineAction.setVisibility(View.VISIBLE);
        //TODO:进入个人界面
        mRightMineAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BaseActivity.this , "个人中心" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    //隐藏个人信息按钮
    public void hideRightMineAction(){
        if (mRightMineAction != null) {
            mRightMineAction.setVisibility(View.GONE);
        }else{
            mRightMineAction = (ImageView) findViewById(R.id.iv_mine);
            mRightMineAction.setVisibility(View.GONE);
        }
    }

    //初始化返回按钮
    public void initLeftBackAction(){
        if(mLeftBackAction == null){
            mLeftBackAction = (ImageView) findViewById(R.id.iv_back);
        }
        mLeftBackAction.setVisibility(View.VISIBLE);
        mLeftBackAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!doBeforeClose()){
                    finish();
                }
            }
        });
    }

    //隐藏返回按钮
    public void hideBackAction(){
        if (mLeftBackAction != null) {
            mLeftBackAction.setVisibility(View.GONE);
        }else{
            mLeftBackAction = (ImageView) findViewById(R.id.iv_back);
            mLeftBackAction.setVisibility(View.GONE);
        }
    }

    public void goHome(int index){
        Intent intent = new Intent(this , MainActivity.class);
        intent.putExtra("index" , index);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
