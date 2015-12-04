package com.fy.administrator.superbook.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者：Administrator
 * 时间：2015/7/23  17:11
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DelView extends View{
    private Context context;
    private AttributeSet attrs;
    private float mWidth;
    private float mHeigth;

    private boolean firstTag;
    private Paint mPaint;

    private float pWidth;

    private int paddingTop;
    private int paddingButtom;
    private int paddingLeft;
    private int paddingRight;


    public DelView(Context context) {
        this(context, null);
    }

    public DelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        setData();
    }

    private void setData() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        paddingButtom = getPaddingBottom();
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!firstTag){
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
            mHeigth = MeasureSpec.getSize(heightMeasureSpec);

            if (mWidth > mHeigth){
                mWidth = mHeigth;
            }else{
                mHeigth = mWidth;
            }

            pWidth = (mWidth - paddingLeft - paddingRight) / 10;

            setMeasuredDimension((int)mWidth,(int)mHeigth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        //空心
        mPaint.setStyle(Paint.Style.STROKE);
        //设置画笔宽度
        mPaint.setStrokeWidth(pWidth);

        canvas.drawRect(0 + paddingLeft,0 + paddingTop,mWidth - paddingRight,mHeigth - paddingButtom,mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(0 + paddingLeft, pWidth + paddingTop);// 此点为多边形的起点
        path.lineTo(pWidth + paddingLeft, 0 + paddingTop);
        path.lineTo(mWidth - paddingRight, mHeigth - pWidth - paddingButtom);
        path.lineTo(mWidth - pWidth - paddingRight,mHeigth - paddingButtom);
        path.close(); // 使这些点构成封闭的多边形

        canvas.drawPath(path, mPaint);

        Path path2 = new Path();
        path2.moveTo(0 + paddingLeft,mHeigth - pWidth - paddingButtom);
        path2.lineTo(pWidth + paddingLeft, mHeigth - paddingButtom);
        path2.lineTo(mWidth - paddingRight, pWidth + paddingTop);
        path2.lineTo(mWidth - pWidth - paddingRight,0 + paddingTop);
        path2.close();

        canvas.drawPath(path2,mPaint);
    }
}
