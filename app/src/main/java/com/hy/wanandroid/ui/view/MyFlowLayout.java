package com.hy.wanandroid.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hy.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: huyin
 * date: 2018/6/10
 */
public class MyFlowLayout extends ViewGroup {

    private Context mContext;
    /**
     * 背景
     */
    private ColorStateList background;
    /**
     * 边框颜色
     */
    private ColorStateList borderColor;
    /**
     * 字体颜色
     */
    private ColorStateList textColor;
    /**
     * 字体大小
     */
    private int textSize = 15;
    /**
     * 字体样式
     */
    private int textStyle = -1;

    //视图列表集合
    private List<List<View>> listViews = new ArrayList<>();
    //高度集合
    private List<Integer> heights = new ArrayList<>();
    //数据集合
    private List<String> strings = new ArrayList<>();

    public MyFlowLayout(Context context) {
        this(context, null);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyFlowLayout);
        for (int i = 0; i < array.getIndexCount(); i++) {
            int attr = array.getIndex(i);
            background = array.getColorStateList(R.styleable.MyFlowLayout_fBackground);
            borderColor = array.getColorStateList(R.styleable.MyFlowLayout_borderColor);
            textColor = array.getColorStateList(R.styleable.MyFlowLayout_textColor);
            textSize = array.getDimensionPixelSize(attr, R.styleable.MyFlowLayout_textSize);
            textStyle = array.getInt(attr, textStyle);
        }
        array.recycle();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sHeight = MeasureSpec.getSize(heightMeasureSpec);
        int mWidth = MeasureSpec.getMode(widthMeasureSpec);
        int mHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //测量
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //量取margin属性
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getWidth() + marginLayoutParams.leftMargin +
                    marginLayoutParams.rightMargin;
            int childHeight = child.getHeight() + marginLayoutParams.topMargin +
                    marginLayoutParams.bottomMargin;
            if (lineWidth + childWidth > sHeight) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            /**
             * 加入最后一行
             */
            if (i == getChildCount() - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        int measureWith;
        if (mWidth == MeasureSpec.EXACTLY) {
            measureWith = sWidth;
        } else {
            measureWith = width;
        }
        int measureHeight;
        if (mHeight == MeasureSpec.EXACTLY) {
            measureHeight = sHeight;
        } else {
            measureHeight = height;
        }
        setMeasuredDimension(measureWith, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        listViews.clear();
        heights.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> list = new ArrayList<>();
        int count = getChildCount();
        //填充
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getWidth() + marginLayoutParams.leftMargin +
                    marginLayoutParams.rightMargin;
            int childHeight = child.getHeight() + marginLayoutParams.topMargin +
                    marginLayoutParams.bottomMargin;
            if (childWidth + lineWidth > width) {
                listViews.add(list);
                heights.add(lineHeight);
                lineWidth = 0;
                lineHeight = 0;
                list = new ArrayList<>();
            }
            list.add(child);
            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
        }
        //添加最后一行
        listViews.add(list);
        heights.add(lineHeight);

        int left = 0;
        int top = 0;
        for (int i = 0; i < listViews.size(); i++) {
            list = listViews.get(i);
            lineHeight = heights.get(i);

            for (int j = 0; j < list.size(); i++) {
                View child = list.get(j);
                MarginLayoutParams marginLayoutParams =
                        (MarginLayoutParams) child.getLayoutParams();
                int leftChild = left + marginLayoutParams.leftMargin;
                int topChild = top + marginLayoutParams.topMargin;
                int rightChild = leftChild + child.getMeasuredWidth();
                int bottomChild = topChild + child.getMeasuredHeight();
                child.layout(leftChild, topChild, rightChild, bottomChild);
                left += child.getMeasuredWidth() + marginLayoutParams.leftMargin +
                        marginLayoutParams.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    public void setDatas(List<String> datas) {
        for (int i = 0; i < datas.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setText(datas.get(i));
            textView.setLayoutParams(new ViewGroup.MarginLayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            addView(textView);
        }
        invalidate();
    }
}
