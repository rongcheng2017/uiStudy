package com.rong.cheng.fish;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: frc
 * @description:
 * @date: 2021/6/10 5:39 下午
 */
public class FishDrawable extends Drawable {
    private Path mPath;
    private Paint mPaint;
    private int OTHER_ALPHA = 110;


    //Drawable重心 即鱼的重点
    private PointF middlePoint;

    //起始角度,即鱼头的朝向
    private float fishMainAngle = 0;


    private final float HEAD_RADIUS = 100;
    private float BODY_LENGTH = HEAD_RADIUS * 3.2f;

    public FishDrawable() {
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71);
        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);
    }


    @Override
    public void draw(@NonNull Canvas canvas) {

        float fishAngle = fishMainAngle;
        PointF headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle);

        canvas.drawCircle(headPoint.x,headPoint.y,HEAD_RADIUS,mPaint);
    }

    /**
     * 计算xy值坐标
     *
     * @param startPoint 开始点
     * @param length     要求的点到起始点的直线距离--线长
     * @param angle      角度
     * @return PointF
     */
    public PointF calculatePoint(PointF startPoint, float length, float angle) {
        //x坐标
        float deltaX = (float) Math.cos(Math.toRadians(angle) * length);
        //y坐标
        float deltaY = (float) Math.sin(Math.toRadians(angle - 180) * length);
        return new PointF(deltaX, deltaY);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }
}
