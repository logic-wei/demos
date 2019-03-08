package com.example.weipeng.andemos.CustomViewDemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


@Deprecated
public class CustomView extends View {

    Paint mPaint = new Paint();
    ValueAnimator mAnimator; // 归一化的值
    float mAnimatedValue = 0;
    float mRadiusBase = 50;
    float mRadiusOverlay = 100;
    int mColor = Color.GRAY;
    float centerX;
    float centerY;


    /**
     * 根据API文档，这个类型的构造函数是解析XML时必须要调用的函数，所以必须实现此方法，否则会报错
     * @param context
     * @param attrs
     */
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimator();
    }

    private void initAnimator() {
        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.setInterpolator(new BreathInterpolator());
        mAnimator.setDuration(2000);
        mAnimator.setFloatValues(0f, 0.5f);
        mAnimator.setCurrentFraction(mAnimatedValue);
        mAnimator.removeAllListeners();
        mAnimator.setRepeatCount(-1);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        canvas.drawCircle(centerX, centerY, mRadiusBase + mAnimatedValue * mRadiusOverlay, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mAnimator.cancel();
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(200);
                mAnimator.setFloatValues(mAnimatedValue, 1f);
                //mAnimator.setCurrentFraction(mAnimatedValue);
                mAnimator.removeListener(mSmallerListener);
                mAnimator.addListener(mBiggerListener);
                mAnimator.setRepeatCount(0);
                mAnimator.start();
                break;
            case MotionEvent.ACTION_UP:
                mAnimator.cancel();
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(600);
                mAnimator.setFloatValues(mAnimatedValue, 0f);
                //mAnimator.setCurrentFraction(mAnimatedValue);
                mAnimator.removeListener(mBiggerListener);
                mAnimator.addListener(mSmallerListener);
                mAnimator.setRepeatCount(0);
                mAnimator.start();
                break;
        }
        return true;
    }

    class BreathInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            // 统一归一化处理
            return (float) (- Math.cos(input * 2 * Math.PI) / 2f) + 0.5f;
        }
    }

    class Breath2Interpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            // 统一归一化处理
            return (float) (Math.cos(input * 2 * Math.PI) / 2f) + 0.5f;
        }
    }

    ValueAnimator.AnimatorListener mBiggerListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mAnimator.cancel();
            mAnimator.setInterpolator(new Breath2Interpolator());
            mAnimator.setDuration(2000);
            mAnimator.setFloatValues(0.5f, 1f);
            mAnimator.setCurrentFraction(mAnimatedValue);
            mAnimator.removeAllListeners();
            mAnimator.setRepeatCount(-1);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    ValueAnimator.AnimatorListener mSmallerListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mAnimator.cancel();
            mAnimator.setInterpolator(new BreathInterpolator());
            mAnimator.setDuration(2000);
            mAnimator.setFloatValues(0f, 0.5f);
            mAnimator.setCurrentFraction(mAnimatedValue);
            mAnimator.removeAllListeners();
            mAnimator.setRepeatCount(-1);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public void setRadius(float r) {
        mRadiusBase = r;
    }

    public void setRadiusOverlay(float overlay) {
        mRadiusOverlay = overlay;
    }

    public void setColor(int color) {
        mColor = color;
    }
}
