package com.hy.wanandroid.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.CollectListBean;
import com.hy.wanandroid.framework.presenter.CollectListPresenter;
import com.hy.wanandroid.framework.view.CollectListView;

/**
 * author: huyin
 * date: 2018/6/13
 */
public class CollectListActivity extends BaseActivity implements CollectListView{

    Toolbar toolbar;
    CollectListPresenter collectListPresenter;
    int page = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_collect_list);
    }

    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.my_collection_txt);
        toolbar.setNavigationIcon(R.drawable.ic_back_left_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void viewInit() {
        initTitle();

    }

    @Override
    public void dataInit() {
        collectListPresenter = new CollectListPresenter(this);
        collectListPresenter.getCollectList(String.valueOf(page));
    }

    @Override
    public void setCollectList(CollectListBean collectList) {

    }
}
