package com.example.weipeng.andemos.CustomViewDemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerTabStrip;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class BreathButton extends View {

    // 参数
    private int mColor = Color.GRAY;
    private float mSmallerBaseRadius = 100f;
    private float mBiggerBaseRadius = 200f;
    private float mRadiusOverlayMax = 30f;
    // 中间变量
    private float mRadiusOverlay = 30f;
    private float mBaseRadius = mSmallerBaseRadius;
    private float mRadius = 0; // mRadius = mRadiusOverlay + mBaseRadius

    ValueAnimator mBreathAnimator = ValueAnimator.ofFloat(mRadiusOverlay, - mRadiusOverlay);
    ValueAnimator mTransAnimator = ValueAnimator.ofFloat(mSmallerBaseRadius, mBiggerBaseRadius);

    public BreathButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTransAnimator();
        initBreathAnimator();
        mBreathAnimator.start();
    }

    private void initBreathAnimator() {
        mBreathAnimator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return (float) Math.sin(input * 2 * Math.PI);
            }
        });
        mBreathAnimator.setRepeatMode(ValueAnimator.RESTART);
        mBreathAnimator.setDuration(3000);
        mBreathAnimator.setRepeatCount(-1);
        mBreathAnimator.addUpdateListener(mBreathAnimatorListener);
        mBreathAnimator.setFloatValues(0, mRadiusOverlayMax);
    }

    private void initTransAnimator() {
        mTransAnimator.addUpdateListener(mTransAnimatorListener);
    }

    public void setSmallerBaseRadius(float value) {
        mSmallerBaseRadius = value;
    }

    public void setBiggerBaseRadius(float value) {
        mBiggerBaseRadius = value;
    }

    public void setBreathRange(float range) {
        mRadiusOverlayMax = range;
        mBreathAnimator.setFloatValues(0, mRadiusOverlayMax);
    }

    public void setColor(int color) {
        mColor = color;
    }

    Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mRadius = mBaseRadius + mRadiusOverlay;
        canvas.drawCircle(centerX, centerY, mRadius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                becomeBig();
                break;
            case MotionEvent.ACTION_UP:
                becomeSmall();
                break;
        }
        return true;
    }

    private void becomeBig() {
        //if (mRadius >= mBiggerBaseRadius)
        //    return;
        mTransAnimator.cancel();
        mTransAnimator.setFloatValues(mBaseRadius , mBiggerBaseRadius);
        mTransAnimator.setInterpolator(new DecelerateInterpolator());
        mTransAnimator.setDuration(300);
        mTransAnimator.start();
    }

    private void becomeSmall() {
        //if (mRadius <= mSmallerBaseRadius)
        //    return;
        mTransAnimator.cancel();
        mTransAnimator.setFloatValues(mBaseRadius, mSmallerBaseRadius);
        mTransAnimator.setInterpolator(new DecelerateInterpolator());
        mTransAnimator.setDuration(500);
        mTransAnimator.start();
    }

    ValueAnimator.AnimatorUpdateListener mTransAnimatorListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //Log.i("test", "value="+mBaseRadius);
            mBaseRadius = (float) animation.getAnimatedValue();
            invalidate();
        }
    };

    ValueAnimator.AnimatorUpdateListener mBreathAnimatorListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mRadiusOverlay = (float) animation.getAnimatedValue();
            invalidate();
        }
    };
}
