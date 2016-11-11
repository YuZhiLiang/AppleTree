package com.sy.appletree.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 郁智良
 * on 2016/11/10 0010.
 * des
 */

public class PieChart extends View {
    private Paint mGoodPaint;//画表扬的笔
    private Paint mBadPaint;//画坏成绩的笔
    private Paint mLinePaint;//画线的笔
    private RectF mOval;//规划扇形所在的矩形位置
    private RectF mOval2;//规划扇形所在的矩形位置
    private int mWidth;
    private int mHeigth;
    private float mSweepAngle = 360;

    public PieChart(Context context) {
        super(context);
    }


    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLinePaint = new Paint();
        mGoodPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mGoodPaint.setStyle(Paint.Style.STROKE);
        mGoodPaint.setAntiAlias(true);
        mGoodPaint.setStrokeWidth(10);
        mGoodPaint.setStrokeCap(Paint.Cap.ROUND);
        int i = Color.parseColor("#FF0CCE57");
        mGoodPaint.setColor(i);

        mBadPaint.setStyle(Paint.Style.STROKE);
        mBadPaint.setAntiAlias(true);
        mBadPaint.setStrokeWidth(10);
        mBadPaint.setStrokeCap(Paint.Cap.ROUND);
        int i1 = Color.parseColor("#FF4A5280");
        mBadPaint.setColor(i1);

        mLinePaint.setColor(Color.GRAY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeigth = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeigth;
        mOval = new RectF(20, 20, mWidth - 40, mHeigth - 40);
        setMeasuredDimension(mWidth,mHeigth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int startAngle = 135;//起始角度
        //扫过的角度，顺时针为正

        float badStart = startAngle + mSweepAngle + 5;
        float badSweep = 360 - badStart + startAngle - 5;
        boolean useCenter = false;//若为true会画出两条边
        //画好的
        canvas.drawArc(mOval, startAngle, mSweepAngle, useCenter, mGoodPaint);

        //画坏的
        canvas.drawArc(mOval, badStart, badSweep, useCenter, mBadPaint);

        //画线
        float startX = mWidth * 0.75f - 10, startY = mHeigth * 0.25f - 10;
        float stopX = mWidth * 0.25f -10, stopY = mHeigth * 0.75f - 10;
        canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);

    }

    //设置好的比例和坏的比例
    public void setScale(int award, int error) {
        award = 3;
        error = 2;
        float scale = (award + error) / award;
        float angle = scale * 360;
        mSweepAngle = angle;
    }
}
