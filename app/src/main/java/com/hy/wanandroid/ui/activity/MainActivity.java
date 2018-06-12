package com.hy.wanandroid.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.hy.wanandroid.ui.BottomNavigationViewHelper;
import com.hy.wanandroid.R;
import com.hy.wanandroid.ui.fragment.HomeFragment;
import com.hy.wanandroid.ui.fragment.MineFragment;
import com.hy.wanandroid.ui.fragment.NavigationFragment;
import com.hy.wanandroid.ui.fragment.ProjectChannelFragment;
import com.hy.wanandroid.ui.fragment.TreeFragment;

public class MainActivity extends BaseActivity {

    BottomNavigationView bottomNavigationView;

    FragmentTransaction fragmentTransaction;
    HomeFragment homeFragment;
    TreeFragment treeFragment;
    NavigationFragment navigationFragment;
    ProjectChannelFragment projectChannelFragment;
    MineFragment mineFragment;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void viewInit() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(fragmentTransaction);
        toHomeFragment();

        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.stack),
                getResources().getColor(R.color.cerulean)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        bottomNavigationView.setItemTextColor(csl);
        bottomNavigationView.setItemIconTintList(csl);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void dataInit() {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            hideFragments(fragmentTransaction);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toHomeFragment();
                    return true;
                case R.id.navigation_tree:
                    toTreeFragment();
                    return true;
                case R.id.navigation_project:
                    toProjectFragment();
                    return true;
                case R.id.navigation_mine:
                    toMineFragment();
                    return true;
            }
            return false;
        }
    };

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (treeFragment != null) {
            fragmentTransaction.hide(treeFragment);
        }
        if (navigationFragment != null) {
            fragmentTransaction.hide(navigationFragment);
        }
        if (projectChannelFragment != null) {
            fragmentTransaction.hide(projectChannelFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
    }

    /**
     * 首页
     */
    private void toHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            fragmentTransaction.add(R.id.container, homeFragment);
        } else {
            fragmentTransaction.show(homeFragment);
        }
        fragmentTransaction.commit();
    }

    /**
     * 体系
     */
    private void toTreeFragment() {
        if (treeFragment == null) {
            treeFragment = new TreeFragment();
            fragmentTransaction.add(R.id.container, treeFragment);
        } else {
            fragmentTransaction.show(treeFragment);
        }
        fragmentTransaction.commit();
    }

    /**
     * 导航
     */
    private void toNavigationFragment() {
        if (navigationFragment == null) {
            navigationFragment = new NavigationFragment();
            fragmentTransaction.add(R.id.container, navigationFragment);
        } else {
            fragmentTransaction.show(navigationFragment);
        }
        fragmentTransaction.commit();
    }

    /**
     * 项目
     */
    private void toProjectFragment() {
        if (projectChannelFragment == null) {
            projectChannelFragment = new ProjectChannelFragment();
            fragmentTransaction.add(R.id.container, projectChannelFragment);
        } else {
            fragmentTransaction.show(projectChannelFragment);
        }
        fragmentTransaction.commit();
    }

    /**
     * 我的
     */
    private void toMineFragment() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
            fragmentTransaction.add(R.id.container, mineFragment);
        } else {
            fragmentTransaction.show(mineFragment);
        }
        fragmentTransaction.commit();
    }
}
