package com.sjl.yuehu.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.HomeBean;
import com.sjl.yuehu.ui.fragment.HomePageFg;
import com.sjl.yuehu.util.SpUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/8.
 */
public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ICON = 0;
    private static final int COMMON = 1;
    private Context context;
    private HomePageFg listener;
    private HomeBean data;
    private List<HomeBean.StoriesBean> stories;

    public HomePageAdapter(Context context, HomePageFg homePageFg) {
        this.context = context;
        this.listener = homePageFg;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ICON) {
            View view2 = View.inflate(context, R.layout.item_zhizhu_top, null);
            return new ViewHolderIcon(view2);
        } else {
            View view = View.inflate(context, R.layout.item_zhizhu, null);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bindData(data.getStories().get(position - 1));
        } else {
            ((ViewHolderIcon) holder).vp.setAdapter(new ImageLoopAdapter(((ViewHolderIcon) holder).vp));
            ((ViewHolderIcon) holder).vp.setHintView(new ColorPointHintView(context, Color.WHITE, Color.GRAY));
            ((ViewHolderIcon) holder).vp.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (listener != null) {
                        listener.click(data.getTop_stories().get(position).getId());
                    }
                }
            });
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

    public void addData(HomeBean data2, boolean isrefresh) {
        if (isrefresh) {
            this.data = data2;
        } else {
            stories = data.getStories();
            stories.addAll(data2.getStories());
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.iv_zhizhu_item_image)
        ImageView img;
        private HomeBean.StoriesBean bean;

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

        public void bindData(HomeBean.StoriesBean data2) {
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

    static class ViewHolderIcon extends RecyclerView.ViewHolder {
        @Bind(R.id.vp)
        RollPagerView vp;

        public ViewHolderIcon(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickListener {
        void click(int id);
    }

    public class ImageLoopAdapter extends LoopPagerAdapter {
        @Bind(R.id.top_icon)
        ImageView topIcon;
        @Bind(R.id.icon_title)
        TextView iconTitle;

        public ImageLoopAdapter(RollPagerView vp) {
            super(vp);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            View view = View.inflate(context, R.layout.top_vp_homepage_fg, null);
            ButterKnife.bind(this, view);
            iconTitle.setText(data.getTop_stories().get(position).getTitle());
            Glide.with(context)
                    .load(data.getTop_stories().get(position).getImage())
                    .error(R.mipmap.bg_about)
                    .into(topIcon);

            return view;
            //add listeners here if necessary
        }

        @Override
        public int getRealCount() {
            return data.getTop_stories().size();
        }
    }
}
