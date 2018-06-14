package com.hy.wanandroid.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.HotKeyBean;
import com.hy.wanandroid.framework.presenter.HotKeyPresenter;
import com.hy.wanandroid.framework.presenter.QueryPresenter;
import com.hy.wanandroid.framework.view.HotKeyView;
import com.hy.wanandroid.framework.view.QueryView;
import com.hy.wanandroid.ui.adapter.BaseAdapter;
import com.hy.wanandroid.ui.adapter.BaseViewHolder;
import com.hy.wanandroid.ui.toast.ToastUtils;

import java.util.LinkedList;
import java.util.List;

import static com.hy.wanandroid.constants.Constants.KEYWORD;

/**
 * Created by huyin on 2018/4/23.
 * <p>
 * 搜索页
 */

public class SearchActivity extends BaseActivity implements
        View.OnClickListener, HotKeyView {

    EditText searchEdit;
    TextView search;
    RecyclerView hotSearchRecyclerView;
    List<HotKeyBean> hotKeyList;
    BaseAdapter<HotKeyBean> hotKeyAdapter;

    HotKeyPresenter hotKeyPresenter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_search);
    }

    private void initTitle() {
        findViewById(R.id.backImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void viewInit() {
        initTitle();
        searchEdit = findViewById(R.id.searchEdit);
        search = findViewById(R.id.search);
        search.setOnClickListener(this);
        hotSearchRecyclerView = findViewById(R.id.hotSearchRecyclerView);
        hotSearchRecyclerView.setNestedScrollingEnabled(false);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        hotSearchRecyclerView.setLayoutManager(layoutManager);
        hotKeyList = new LinkedList<>();
        hotKeyAdapter = new BaseAdapter<HotKeyBean>(this, hotKeyList,
                R.layout.item_hot_search_keyword) {
            @Override
            protected void onBindViewHolder(BaseViewHolder holder,
                                            HotKeyBean item, int position) {
                holder.setText(R.id.hotSearchKeyword, item.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this,
                                SearchListActivity.class);
                        intent.putExtra(KEYWORD, item.getName());
                        startActivity(intent);
                    }
                });
            }
        };
        hotSearchRecyclerView.setAdapter(hotKeyAdapter);
    }

    @Override
    public void dataInit() {
        hotKeyPresenter = new HotKeyPresenter(this);
        hotKeyPresenter.getHotKey();
    }

    @Override
    public void setHotKey(List<HotKeyBean> hotKey) {
        for (HotKeyBean hotKeyBean : hotKey) {
            hotKeyAdapter.add(hotKeyBean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                String keyword = searchEdit.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
                    Intent intent = new Intent(SearchActivity.this,
                            SearchListActivity.class);
                    intent.putExtra(KEYWORD, keyword);
                    startActivity(intent);
                } else {
                    ToastUtils.toast("请先输入搜索关键字");
                }
                break;
        }
    }
}
