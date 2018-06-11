package com.hy.wanandroid.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.hy.wanandroid.R;
import com.hy.wanandroid.ui.adapter.ProjectAdapter;

/**
 * author: huyin
 * date: 2018/6/11
 */
public class ChangeStylePopupWindow extends PopupWindow {

    private View mView;

    public ChangeStylePopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popup_window_change_style, null);

        mView.findViewById(R.id.allMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTypeClickListener != null) {
                    dismiss();
                    onTypeClickListener.onClick(ProjectAdapter.ALL_MODE);
                }
            }
        });
        mView.findViewById(R.id.wordsMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTypeClickListener != null) {
                    dismiss();
                    onTypeClickListener.onClick(ProjectAdapter.WORDS_MODE);
                }
            }
        });
        mView.findViewById(R.id.pictureMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTypeClickListener != null) {
                    dismiss();
                    onTypeClickListener.onClick(ProjectAdapter.PICTURE_MODE);
                }
            }
        });

        //设置SelectPicPopupWindow的View
        this.setContentView(mView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(mView.getResources().getColor(R.color.white));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (mView != null) {
                    dismiss();
                    mView = null;
                }
                return false;
            }
        });
    }

    public interface OnTypeClickListener {
        void onClick(int type);
    }

    OnTypeClickListener onTypeClickListener;

    public void setOnTypeClickListener(OnTypeClickListener onTypeClickListener) {
        this.onTypeClickListener = onTypeClickListener;
    }
}
