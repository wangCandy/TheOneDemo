package com.wly.onedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Candy on 2016/11/9.
 */

public class BottomScrollView extends ScrollView {

    private OnScrollToBottomListener onScrollToBottom;

    public BottomScrollView(Context context) {
        super(context);
    }

    public BottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if(scrollY != 0 && null != onScrollToBottom){
            onScrollToBottom.onScrollToBottom(clampedY);
        }
    }

    public void setOnScrollToBottom(OnScrollToBottomListener listener){
        this.onScrollToBottom = listener;
    }

    public interface OnScrollToBottomListener{
        void onScrollToBottom(boolean scrollToBottom);
    }
}
