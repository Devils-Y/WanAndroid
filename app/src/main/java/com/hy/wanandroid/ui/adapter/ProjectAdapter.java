package com.hy.wanandroid.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hy.wanandroid.R;
import com.hy.wanandroid.bean.ProjectListBean;

import java.util.List;

/**
 * author: huyin
 * date: 2018/6/11
 */
public class ProjectAdapter extends BaseAdapter<ProjectListBean.DatasBean> {

    public ProjectAdapter(Context context, List<ProjectListBean.DatasBean> data) {
        super(context, data, -1);
    }

    /**
     * 图文完整模式
     */
    public static final int ALL_MODE = 0;
    /**
     * 文字模式
     */
    public static final int WORDS_MODE = 1;
    /**
     * 图片模式
     */
    public static final int PICTURE_MODE = 2;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载Item View的时候根据不同TYPE加载不同的布局
        if (viewType == ALL_MODE) {
            return new BaseViewHolder(mLayoutInflater.
                    inflate(R.layout.item_project_list, parent, false));
        } else if (type == WORDS_MODE) {
            return new BaseViewHolder(mLayoutInflater.
                    inflate(R.layout.item_project_words_list, parent, false));
        } else {
            return new BaseViewHolder(mLayoutInflater.
                    inflate(R.layout.item_project_pics_list, parent, false));
        }
    }

    @Override
    protected void onBindViewHolder(BaseViewHolder holder, ProjectListBean.DatasBean item,
                                    int position) {
        if (type == ALL_MODE) {
            holder.setText(R.id.title, item.getTitle())
                    .setText(R.id.content, item.getDesc())
                    .setText(R.id.date, item.getNiceDate())
                    .setText(R.id.author, item.getAuthor());
            holder.setImageUrl(R.id.projectImg, new BaseViewHolder.HolderImage(
                    item.getEnvelopePic()) {
                @Override
                public void loadImg(ImageView imageView, String url) {
                    Glide.with(mContext).load(url).into(imageView);
                }
            });
        } else if (type == WORDS_MODE) {
            holder.setText(R.id.title, item.getTitle())
                    .setText(R.id.content, item.getDesc())
                    .setText(R.id.date, item.getNiceDate())
                    .setText(R.id.author, item.getAuthor());
        } else {
            holder.setImageUrl(R.id.projectImg, new BaseViewHolder.HolderImage(
                    item.getEnvelopePic()) {
                @Override
                public void loadImg(ImageView imageView, String url) {
                    Glide.with(mContext).load(url).into(imageView);
                }
            });
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(item.getLink());
                }
            }
        });
    }

    int type;

    /**
     * 点击切换布局的时候调用这个方法设置type
     *
     * @param type 商品排列的方式 0：网格；1：垂直列表排列
     */
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    /**
     * 项目item点击事件接口
     */
    public interface OnItemClickListener {
        void onItemClick(String link);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
