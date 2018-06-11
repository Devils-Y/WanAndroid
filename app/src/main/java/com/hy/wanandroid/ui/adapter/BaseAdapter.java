package com.hy.wanandroid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 共用adapter
 *
 * @param <T>
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    //布局id
    private int mLayoutId;
    protected List<T> mData;
    protected LayoutInflater mLayoutInflater;
    protected Context mContext;

    private MultiTypeSupport mMultiTypeSupport;

    public BaseAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutId = layoutId;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, List<T> data, MultiTypeSupport multiTypeSupport) {
        this(context, data, -1);
        this.mMultiTypeSupport = multiTypeSupport;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiTypeSupport != null) {
            mLayoutId = viewType;
        }
        View view = mLayoutInflater.inflate(mLayoutId, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getLayoutId(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        onBindViewHolder(holder, mData.get(position), position);
    }

    protected abstract void onBindViewHolder(BaseViewHolder holder, T item, int position);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void add(T e) {
        mData.add(e);
        notifyDataSetChanged();
    }

    public void clearAndAdd(T e) {
        mData.clear();
        mData.add(e);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public int size(){
        return mData.size();
    }
}
