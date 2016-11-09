package com.wly.onedemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.wly.onedemo.R;
import com.wly.onedemo.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Candy on 2016/11/3.
 */

public class LoadingView extends View {

    private final int DEFAULT_DURATION = 15;
    private final int DEFAULT_INTERNAL_RADIUS = DisplayUtil.dip2px(getContext() , 8);
    private final int DEFAULT_EXTERNAL_RADIUS = DisplayUtil.dip2px(getContext() , 82);
    private final int DEFAULT_RADIUS = 45;

    private int mWidth;
    private int mHeight;
    private Path mPath = new Path();
    private Subscription mTimer;

    private List<ValueAnimator> mAnimations;
    private Paint mPaint;
    private List<PointF> mPoints;

    private int[] mColors;

    //时间间隔
    private int mDuration;

    //外圆半径
    private float mExternalRadius;

    //内圆半径
    private float mInternalRadius;

    //点之间的弧度
    private int mRadius = DEFAULT_RADIUS;

    //外圆圆心
    private float x0 , y0;

    //圈数
    private int mCyclic = 0;

    private int mAngle = 50;

    private float mGetBiggerCircleRadius;
    private float mGetSmallerCircleRadius;

    public LoadingView(Context context) {
        this(context , null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs){

        mAnimations = new ArrayList<>();
        mPoints = new ArrayList<>();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs , R.styleable.LoadingView);

        mDuration = typedArray.getInt(R.styleable.LoadingView_lv_duration , DEFAULT_DURATION);
        mExternalRadius = typedArray.getDimension(R.styleable.LoadingView_lv_external_radius , DEFAULT_EXTERNAL_RADIUS);
        mInternalRadius = typedArray.getDimension(R.styleable.LoadingView_lv_internal_radius , DEFAULT_INTERNAL_RADIUS);

        int default_color = 999999;
        int startColor = typedArray.getColor(R.styleable.LoadingView_lv_start_color , default_color);
        int endColor = typedArray.getColor(R.styleable.LoadingView_lv_end_color , default_color);

        List<Integer> colorList = new ArrayList<Integer>();
        if(startColor != default_color){
            colorList.add(startColor);
        }

        if(endColor != default_color){
            colorList.add(endColor);
        }

        if(colorList.size() == 1){
            colorList.add(colorList.get(0));
        }

        if(colorList.size() == 0){
            mColors = new int[] {
                    ContextCompat.getColor(getContext() , R.color.loading_yellow) ,
                    ContextCompat.getColor(getContext() , R.color.loading_pink)
            };
        }else{
            mColors = new int[colorList.size()];
            for (int i = 0; i < colorList.size(); i++) {
                mColors[i] = colorList.get(i);
            }
        }

        typedArray.recycle();
    }

    public void start(){
        if (mTimer == null || mTimer.isUnsubscribed()) {
            mTimer = Observable.interval(mDuration , TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            dealTimerBusiness();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
        }
        this.setVisibility(VISIBLE);
    }

    public void stop(){
        if(mTimer != null){
            mTimer.unsubscribe();
        }
        this.setVisibility(GONE);
    }

    public void dealTimerBusiness(){
        setOffset((mAngle % mRadius) / (float) mRadius);

        mAngle ++;
        if(mAngle == 360){
            mAngle = 0;
            mCyclic++;
        }
    }

    public void createAnimator(){
        if(mPoints == null){
            return;
        }
        mAnimations.clear();

        ValueAnimator circleGetSmallRadius = ValueAnimator.ofFloat(getMaxInternalRadius() , getMinInternalRadius());
        circleGetSmallRadius.setDuration(5000L);
        circleGetSmallRadius.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mGetSmallerCircleRadius = (float) valueAnimator.getAnimatedValue();
            }
        });
        mAnimations.add(circleGetSmallRadius);

        ValueAnimator circleGetBiggerRadius = ValueAnimator.ofFloat(getMinInternalRadius() , getMaxInternalRadius());
        circleGetBiggerRadius.setDuration(5000L);
        circleGetBiggerRadius.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mGetBiggerCircleRadius = (float) valueAnimator.getAnimatedValue();
            }
        });
        mAnimations.add(circleGetBiggerRadius);

    }

    public void seekOffset(float offset){
        for (ValueAnimator animator : mAnimations){
            animator.setCurrentPlayTime((long) offset * 5000L);
        }
    }

    public void setOffset(float offset){
        createAnimator();
        seekOffset(offset);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawBezier(canvas);
    }

    //绘制各个圆点
    private void drawCircle(Canvas canvas) {
        for (int i = 0; i < mPoints.size(); i++) {
            int index = mAngle / mRadius;
            if (isEvenCyclic()) {
                if (i == index) {
                    if (mAngle % mRadius == 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), getMaxInternalRadius(), mPaint);
                    } else if (mAngle % mRadius > 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), mGetSmallerCircleRadius < mInternalRadius ? mInternalRadius : mGetSmallerCircleRadius, mPaint);
                    }
                } else if (i == index + 1) {
                    if (mAngle % mRadius == 0) {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mInternalRadius, mPaint);
                    } else {
                        canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mGetBiggerCircleRadius < mInternalRadius ? mInternalRadius : mGetBiggerCircleRadius, mPaint);
                    }
                } else if (i > index + 1) {
                    canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mInternalRadius, mPaint);
                }
            } else {
                if (i < index) {
                    canvas.drawCircle(mPoints.get(i + 1).x, mPoints.get(i + 1).y, mInternalRadius, mPaint);
                } else if (i == index) {
                    if (mAngle % mRadius == 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), getMaxInternalRadius(), mPaint);
                    } else {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), mGetSmallerCircleRadius < mInternalRadius ? mInternalRadius : mGetSmallerCircleRadius, mPaint);
                    }
                } else if (i == index + 1) {
                    if (mAngle % mRadius == 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), getMinInternalRadius(), mPaint);
                    } else if (mAngle % mRadius > 0) {
                        canvas.drawCircle(getCircleX(mAngle), getCircleY(mAngle), mGetBiggerCircleRadius < mInternalRadius ? mInternalRadius : mGetBiggerCircleRadius, mPaint);
                    }
                }
            }
        }
    }

    //绘制贝塞尔曲线

    private void drawBezier(Canvas canvas) {

        mPath.reset();

        int circleIndex = mAngle / mRadius;

        float rightX = getCircleX(mAngle);
        float rightY = getCircleY(mAngle);

        float leftX, leftY;
        if (isEvenCyclic()) {
            int index;
            index = circleIndex + 1;
            leftX = mPoints.get(index >= mPoints.size() ? mPoints.size() - 1 : index).x;
            leftY = mPoints.get(index >= mPoints.size() ? mPoints.size() - 1 : index).y;
        } else {
            int index = circleIndex;
            leftX = mPoints.get(index < 0 ? 0 : index).x;
            leftY = mPoints.get(index < 0 ? 0 : index).y;
        }

        double theta = getTheta(new PointF(leftX, leftY), new PointF(rightX, rightY));
        float sinTheta = (float) Math.sin(theta);
        float cosTheta = (float) Math.cos(theta);

        PointF pointF1 = new PointF(leftX - mInternalRadius * sinTheta, leftY + mInternalRadius * cosTheta);
        PointF pointF2 = new PointF(rightX - mInternalRadius * sinTheta, rightY + mInternalRadius * cosTheta);
        PointF pointF3 = new PointF(rightX + mInternalRadius * sinTheta, rightY - mInternalRadius * cosTheta);
        PointF pointF4 = new PointF(leftX + mInternalRadius * sinTheta, leftY - mInternalRadius * cosTheta);

        if (isEvenCyclic()) {
            if (mAngle % mRadius < mRadius / 2) {

                mPath.moveTo(pointF3.x, pointF3.y);
                mPath.quadTo(rightX + (leftX - rightX) / (mRadius / 2) * (mAngle % mRadius > (mRadius / 2) ? (mRadius / 2) : mAngle % mRadius), rightY + (leftY - rightY) / (mRadius / 2) * (mAngle % mRadius > (mRadius / 2) ? (mRadius / 2) : mAngle % mRadius), pointF2.x, pointF2.y);
                mPath.lineTo(pointF3.x, pointF3.y);

                mPath.moveTo(pointF4.x, pointF4.y);
                mPath.quadTo(leftX + (rightX - leftX) / (mRadius / 2) * (mAngle % mRadius > (mRadius / 2) ? (mRadius / 2) : mAngle % mRadius), leftY + (rightY - leftY) / (mRadius / 2) * (mAngle % mRadius > (mRadius / 2) ? (mRadius / 2) : mAngle % mRadius), pointF1.x, pointF1.y);
                mPath.lineTo(pointF4.x, pointF4.y);

                mPath.close();
                canvas.drawPath(mPath, mPaint);
                return;
            }
        } else {
            if (circleIndex > 0 && mAngle % mRadius > mRadius / 2) {

                mPath.moveTo(pointF3.x, pointF3.y);
                mPath.quadTo(rightX + (leftX - rightX) / (mRadius / 2) * (mRadius - mAngle % mRadius > (mRadius / 2) ? (mRadius / 2) : (mRadius - mAngle % mRadius)), rightY + (leftY - rightY) / (mRadius / 2) * (mRadius - mAngle % mRadius > (mRadius / 2) ? (mRadius / 2) : (mRadius - mAngle % mRadius)), pointF2.x, pointF2.y);
                mPath.lineTo(pointF3.x, pointF3.y);

                mPath.moveTo(pointF4.x, pointF4.y);
                mPath.quadTo(leftX + (rightX - leftX) / (mRadius / 2) * (mRadius - mAngle % mRadius > (mRadius / 2) ? (mRadius / 2) : (mRadius - mAngle % mRadius)), leftY + (rightY - leftY) / (mRadius / 2) * (mRadius - mAngle % mRadius > (mRadius / 2) ? (mRadius / 2) : (mRadius - mAngle % mRadius)), pointF1.x, pointF1.y);
                mPath.lineTo(pointF4.x, pointF4.y);

                mPath.close();
                canvas.drawPath(mPath, mPaint);
                return;
            }
        }

        if (circleIndex == 0 && !isEvenCyclic()) return;

        mPath.moveTo(pointF1.x, pointF1.y);
        mPath.quadTo((leftX + rightX) / 2, (leftY + rightY) / 2, pointF2.x, pointF2.y);
        mPath.lineTo(pointF3.x, pointF3.y);
        mPath.quadTo((leftX + rightX) / 2, (leftY + rightY) / 2, pointF4.x, pointF4.y);
        mPath.lineTo(pointF1.x, pointF1.y);

        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    private double getTheta(PointF pointCenterLeft, PointF pointCenterRight) {
        double theta = Math.atan((pointCenterRight.y - pointCenterLeft.y) / (pointCenterRight.x - pointCenterLeft.x));
        return theta;
    }

    //判断是否为偶数圈
    public boolean isEvenCyclic(){
        return mCyclic % 2 == 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        setShader();
        resetPoints();

    }

    public void setShader(){

        Shader mLinearGradient = new LinearGradient(mWidth / 2 - mExternalRadius , mWidth / 2 - mExternalRadius , mWidth / 2  - mExternalRadius , mWidth / 2 + mExternalRadius , mColors , null ,
                Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    public void resetPoints(){

        x0 = mWidth / 2;
        y0 = mHeight / 2;

        createPoints();
        if(!mPoints.isEmpty()){
            mGetBiggerCircleRadius = getMaxInternalRadius();
            mGetSmallerCircleRadius = getMinInternalRadius();
            postInvalidate();
        }
    }

    /*
    创建每一个点所在的位置
        圆点坐标：(x0,y0)
        半径：r
        角度：a0
        则圆上任一点为：（x1,y1）
        x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
        y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
     */
    public void createPoints(){
        mPoints.clear();
        for (int i = 0; i < 360; i++) {
            if(i % mRadius == 0){
                float x1 = getCircleX(i);
                float y1 = getCircleY(i);
                mPoints.add(new PointF(x1 , y1));
            }
        }
    }

    public float getCircleX(int angle){
        return x0 + mExternalRadius * (float) Math.cos(angle * Math.PI / 180);
    }

    public float getCircleY(int angle){
        return y0 + mExternalRadius * (float) Math.sin(angle * Math.PI / 180);
    }

    public float getMaxInternalRadius(){
        return mInternalRadius / 10f * 14f;
    }

    public float getMinInternalRadius(){
        return mInternalRadius / 10f;
    }
}
