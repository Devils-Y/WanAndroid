package com.hy.wanandroid.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.layout.util.DesignUtil;

import java.util.Random;


/**
 * author: huyin
 * date: 2018/6/14
 * <p>
 * 显示首字头像
 */
public class ColorHeadImage extends View {

    private static final String TAG = ColorHeadImage.class.getName();
    /**
     * 背景的画笔
     */
    Paint bgPaint;
    /**
     * 文字的画笔
     */
    Paint mPaint;
    /**
     * 字体大小
     */
    int mTextSize = 20;

    Context mContext;
    /**
     * 名字
     */
    String mName;
    /**
     * 半径
     */
    float radius = 50;

    public ColorHeadImage(Context context) {
        super(context);
        this.mContext = context;
        this.mName = "";
    }

    public ColorHeadImage(Context context, String name) {
        super(context, null);
        this.mContext = context;
        this.mName = name;
    }

    public ColorHeadImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mName = "";
    }

    public ColorHeadImage(Context context, @Nullable AttributeSet attrs, String name) {
        super(context, attrs, -1);
        this.mContext = context;
        this.mName = name;
    }

    public ColorHeadImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr, String name) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mName = name;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        /**
         * 判断必须相等
         */
        if (width == height) {
            radius = width / 2;
        } else {
            try {
            } catch (Exception e) {
                Log.e(TAG, "Length and width must be equal");
                e.printStackTrace();
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bgPaint = new Paint();
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        int mColor = Color.rgb(r, g, b);
        bgPaint.setColor(mColor);// 设置颜色
        bgPaint.setAntiAlias(true);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(mTextSize);

        canvas.drawCircle(radius, radius, radius, bgPaint);

        canvas.drawText(mName, 0, 1, radius, radius, mPaint);
    }

    /**
     * 设置名字
     *
     * @param name
     */
    public void setName(String name) {
        this.mName = name;
        invalidate();
    }
}
