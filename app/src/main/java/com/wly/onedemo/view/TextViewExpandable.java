package com.wly.onedemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wly.onedemo.R;

/**
 * Created by Candy on 2016/10/31.
 */
public class TextViewExpandable extends LinearLayout implements View.OnClickListener{

    private int expandedLines;

    private int textStateColor;

    private int textContentColor;

    private float textContentSize;

    private String textShrink;

    private String textExpand;

    private String textContent;

    private int textLines;
    private boolean isNeedExpanded;

    private boolean isInitTextView = true;

    private TextView mTextContent;
    private TextView mTextState;
    private RelativeLayout mTextViewExpandedLayout;

    private boolean isShrink;

    private Thread mThread;

    private static final int WHAT = 0;
    private static final int WHET_ANIMATION_END = 1;
    private static final int WHAT_EXPAND_ONLY = 2;

    private int sleepTime = 22;

    public TextViewExpandable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValue(context , attrs);
        initView(context);
        initListener();

    }

    public void initValue(Context context , AttributeSet attrs){

        TypedArray ty = context.obtainStyledAttributes(attrs , R.styleable.TextViewExpandable);

        expandedLines = ty.getInteger(R.styleable.TextViewExpandable_tv_expandLines , 5);//默认最多显示吴列

        textStateColor = ty.getColor(R.styleable.TextViewExpandable_tv_textStateColor , getResources().getColor(R.color.tabs_selector_color));

        textShrink = ty.getString(R.styleable.TextViewExpandable_tv_textShrink);
        if(TextUtils.isEmpty(textShrink)){
            textShrink = getResources().getString(R.string.shrink);
        }

        textExpand = ty.getString(R.styleable.TextViewExpandable_tv_textExpand);
        if(TextUtils.isEmpty(textExpand)){
            textExpand = getResources().getString(R.string.expand);
        }

        textContentColor = ty.getColor(R.styleable.TextViewExpandable_tv_textContentColor , getResources().getColor(R.color.news_detail_color));

        textContentSize = ty.getDimension(R.styleable.TextViewExpandable_tv_textContentSize , 14);
        ty.recycle();
    }

    public void initView(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_textview_expand_animation , this);

        mTextViewExpandedLayout = (RelativeLayout) findViewById(R.id.rl_expand_text_view_animation_toggle_layout);

        mTextContent = (TextView) findViewById(R.id.tv_expand_text_view_animation);
        mTextContent.setTextColor(textContentColor);
        mTextContent.getPaint().setTextSize(textContentSize);

        mTextState = (TextView) findViewById(R.id.tv_expand_text_view_animation_hint);
        mTextState.setTextColor(textStateColor);
    }

    public void initListener(){
        mTextViewExpandedLayout.setOnClickListener(this);
        mTextState.setOnClickListener(this);
    }

    public void setText(String charSequence){
        textContent = charSequence;
        mTextContent.setText(textContent);

        ViewTreeObserver observer = mTextContent.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if(!isInitTextView){
                    return true;
                }
                textLines = mTextContent.getLineCount();
                isNeedExpanded = textLines > expandedLines;
                isInitTextView = false;
                if(isNeedExpanded){
                    //需要收起
                    isShrink = true;
                    mTextContent.setMaxLines(expandedLines);
                    setExpandState(expandedLines);
                }else{
                    isShrink = false;
                    doNoExpanded();
                }
                return true;
            }
        });
    }

    public void doNoExpanded(){
        mTextContent.setMaxLines(expandedLines);
        mTextViewExpandedLayout.setVisibility(GONE);
        mTextContent.setOnClickListener(null);
    }

    /**
     * 改变折叠状态（仅仅改变折叠与展开状态，不会隐藏折叠/展开图片布局）
     * change shrink/expand state(only change state,but not hide shrink/expand icon)
     *
     * @param endIndex
     */
    @SuppressWarnings("deprecation")
    private void changeExpandState(int endIndex) {
        mTextViewExpandedLayout.setVisibility(View.VISIBLE);
        if (endIndex < textLines) {
            mTextState.setText(textExpand);
        } else {
            mTextState.setText(textShrink);
        }

    }

    public void setExpandState(int endIndex){
        if(endIndex < textLines){
            isShrink = true;
            mTextViewExpandedLayout.setVisibility(VISIBLE);
            mTextState.setOnClickListener(this);
            mTextState.setText(textExpand);
        }else{
            isShrink = false;
            mTextViewExpandedLayout.setVisibility(GONE);
            mTextState.setOnClickListener(null);
            mTextState.setText(textShrink);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_expand_text_view_animation_hint || view.getId() == R.id.rl_expand_text_view_animation_toggle_layout){
            changeImageToggle();
        }
    }

    public void changeImageToggle(){
        if (isShrink) {
            mTextContent.setMaxLines(textLines);
            mTextContent.invalidate();
            mTextState.setText(textShrink);
        }else{
            mTextContent.setMaxLines(expandedLines);
            mTextContent.invalidate();
            mTextState.setText(textExpand);
        }

        isShrink = !isShrink;
    }

    public int getExpandLines(){
        return expandedLines;
    }

    public void setExpandedLines(int lines){
        this.expandedLines = lines;
    }

    public CharSequence getTextContent(){
        return textContent;
    }
    public void setTextContent(String text){
        this.textContent = text;
    }
}
