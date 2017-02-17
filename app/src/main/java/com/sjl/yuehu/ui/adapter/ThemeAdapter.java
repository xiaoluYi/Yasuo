package com.sjl.yuehu.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.ThemesBean;
import com.sjl.yuehu.ui.fragment.ThemePageFg;
import com.sjl.yuehu.util.SpUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/10.
 */
public class ThemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ICON = 0;
    private static final int COMMON = 1;
    private final Context context;
    private final ThemePageFg listener;
    private boolean isRead;

    private ThemesBean data;
    private List<ThemesBean.StoriesBean> stories;

    public ThemeAdapter(Context context, ThemePageFg themePageFg) {
        this.context = context;
        this.listener = themePageFg;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ICON) {
            View view2 = View.inflate(context, R.layout.item_theme_top, null);
            return new ViewHolderIcon(view2);
        } else {
            View view = View.inflate(context, R.layout.item_theme, null);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderIcon) {
            ((ViewHolderIcon) holder).iconTitle.setText(data.getDescription());
            Glide.with(context)
                    .load(data.getBackground())
                    .error(R.mipmap.bg_about)
                    .into(((ViewHolderIcon) holder).topIcon);
        } else {
            ((ViewHolder) holder).bindData(data.getStories().get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ICON;
        }
        return COMMON;
    }

    @Override
    public int getItemCount() {
        return data != null ? data.getStories().size() : 0;
    }

    public void addData(ThemesBean bean, boolean isrefresh) {
        if (isrefresh) {
             this.data = bean;
        } else {
            stories = data.getStories();
            stories.addAll(bean.getStories());
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.iv_zhizhu_item_image)
        ImageView img;
        private ThemesBean.StoriesBean bean;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            RxView.clicks(itemView).subscribe(aVoid -> {
                if (listener != null) {
                    listener.click(bean.getId());
                    SpUtils.setIsRead(context, bean.getId(), true);
                    notifyDataSetChanged();
                }
            });
        }

        public void bindData(ThemesBean.StoriesBean data2) {
            this.bean = data2;
            tvTitle.setText(data2.getTitle());
            boolean isRead = SpUtils.getIsRead(context, bean.getId());
            if (isRead) {
                tvTitle.setTextColor(Color.GRAY);
            }
            if (data2.getImages() != null) {
                Glide.with(context)
                        .load(data2.getImages().get(0))
                        .error(R.mipmap.bg_about)
                        .into(img);
            } else {
                img.setVisibility(View.GONE);
            }

        }
    }

    class ViewHolderIcon extends RecyclerView.ViewHolder {
        @Bind(R.id.top_icon)
        ImageView topIcon;
        @Bind(R.id.icon_title)
        TextView iconTitle;

        public ViewHolderIcon(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickListener {
        void click(int id);
    }
}
