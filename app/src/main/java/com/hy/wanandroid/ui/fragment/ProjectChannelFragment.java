package com.hy.wanandroid.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.ProjectBean;
import com.hy.wanandroid.data.SharedPreferenceUtils;
import com.hy.wanandroid.framework.presenter.ProjectPresenter;
import com.hy.wanandroid.framework.view.ProjectView;
import com.hy.wanandroid.ui.adapter.ProjectAdapter;
import com.hy.wanandroid.ui.popupwindow.ChangeStylePopupWindow;
import com.hy.wanandroid.ui.toast.ToastUtils;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.PROJECTSTYLE;
import static com.hy.wanandroid.constants.Constants.STYLETYPE;

/**
 * Created by huyin on 2018/4/24.
 * <p>
 * 项目
 */

public class ProjectChannelFragment extends BaseFragment implements
        ProjectView, View.OnClickListener {

    Toolbar toolbar;
    ImageView changStyle;
    TabLayout tabView;
    ViewPager viewPager;

    ProjectPresenter projectPresenter;
    ShortPagerAdapter shortPagerAdapter;
    List<ProjectBean> mProjectList;

    ChangeStylePopupWindow changeStylePopupWindow;

    @Override
    public int setContentLyaoutId() {
        return R.layout.fragment_project_channel;
    }

    @Override
    public void viewInit() {
        toolbar = (Toolbar) getContentView().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.project_txt);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        changStyle = getContentView().findViewById(R.id.changStyle);
        changStyle.setOnClickListener(this);

        tabView = getContentView().findViewById(R.id.tabView);
        viewPager = getContentView().findViewById(R.id.viewPager);

        shortPagerAdapter = new ShortPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(shortPagerAdapter);
        tabView.setupWithViewPager(viewPager);
    }

    @Override
    public void dataInit() {
        projectPresenter = new ProjectPresenter(this);
        projectPresenter.getProject();
    }

    @Override
    public void viewDestroy() {

    }

    @Override
    public void setProject(List<ProjectBean> projectList) {
        mProjectList = projectList;
        shortPagerAdapter.add(projectList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changStyle:
                /**
                 * 样式点击
                 */
                if (changeStylePopupWindow == null) {
                    changeStylePopupWindow = new ChangeStylePopupWindow(getActivity());
                }
                changeStylePopupWindow.showAsDropDown(changStyle);
                changeStylePopupWindow.setOnTypeClickListener(new ChangeStylePopupWindow.OnTypeClickListener() {
                    @Override
                    public void onClick(int type) {
                        //存储用户喜好样式
                        SharedPreferenceUtils.WriteProjectType(type);
                        Intent intent = new Intent(PROJECTSTYLE);
                        intent.putExtra(STYLETYPE, type);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    }
                });
                break;
        }
    }

    private class ShortPagerAdapter extends FragmentPagerAdapter {
        public List<String> mTilte;

        public ShortPagerAdapter(FragmentManager fm) {
            super(fm);
            mTilte = new LinkedList<>();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTilte.get(position);
        }

        @Override
        public BaseFragment getItem(int position) {
            ProjectFragment fragment = null;
            for (int i = 0; i < mProjectList.size(); i++) {
                mTilte.get(position).equals(mProjectList.get(i));
                if (position == i) {
                    fragment = ProjectFragment.arguments(mProjectList.get(i).getId());
                }
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mTilte.size();
        }

        public void add(List<ProjectBean> projectList) {
            for (int i = 0; i < projectList.size(); i++) {
                mTilte.add(projectList.get(i).getName());
            }
            notifyDataSetChanged();
        }
    }
}
