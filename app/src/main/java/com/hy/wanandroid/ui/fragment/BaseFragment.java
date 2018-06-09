package com.hy.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huyin on 2018/4/23.
 */

public abstract class BaseFragment extends Fragment {

    private View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(setContentLyaoutId(), container, false);
        } else {
            return contentView;
        }
        return contentView;
    }

    /**
     * 布局ID
     *
     * @return
     */
    public abstract int setContentLyaoutId();

    /**
     * 得到当前对象
     *
     * @return
     */
    protected View getContentView() {
        if (contentView != null) {
            return contentView;
        }
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewInit();

        dataInit();
    }

    /**
     * 控件初始化
     */
    public abstract void viewInit();

    /**
     * 数据初始化
     */
    public abstract void dataInit();

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewDestroy();
    }

    /**
     * 回收资源
     */
    public abstract void viewDestroy();
}
