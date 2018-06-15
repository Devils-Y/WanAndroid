package com.hy.wanandroid.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hy.wanandroid.R;

import java.util.Random;


/**
 * author: huyin
 * date: 2018/6/14
 * <p>
 * 显示首字头像
 */
public class ColorHeadImage extends View {

    private static final String TAG = ColorHeadImage.class.getName();

    Context mContext;
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
    int mTextSize;
    /**
     * 文字
     */
    String mName;
    /**
     * 半径
     */
    int radius = 0;
    /**
     * 文字绘制范围
     */
    private Rect mBound;

    public ColorHeadImage(Context context) {
        super(context);
        this.mContext = context;
        this.mName = "";
        initAttr(null);
    }

    public ColorHeadImage(Context context, String name) {
        super(context);
        this.mContext = context;
        this.mName = name;
        initAttr(null);
    }

    public ColorHeadImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mName = "";
        initAttr(attrs);
    }

    public ColorHeadImage(Context context, @Nullable AttributeSet attrs, String name) {
        super(context, attrs);
        this.mContext = context;
        this.mName = name;
        initAttr(attrs);
    }

    public ColorHeadImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr, String name) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mName = name;
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.ColorHeadImage);
        mName = array.getString(R.styleable.ColorHeadImage_text);
        radius = array.getDimensionPixelSize(R.styleable.ColorHeadImage_radius, 30);
        mTextSize = array.getDimensionPixelSize(R.styleable.ColorHeadImage_nameTextSize, 20);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
         * 初始化画笔
         */
        mBound = new Rect();
        bgPaint = new Paint();
        /**
         * 随机变换颜色
         */
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
        mPaint.getTextBounds(mName, 0, 1, mBound);

        if (radius == 0) {
            if (getWidth() <= getHeight()) {
                radius = getWidth() / 2;
            } else if (getWidth() > getHeight()) {
                radius = getHeight() / 2;
            }
        }

        canvas.drawCircle(radius, radius, radius, bgPaint);

        canvas.drawText(mName, 0, 1,
                radius - mBound.width() / 2,
                radius + mBound.height() / 2, mPaint);
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
