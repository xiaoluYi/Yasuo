package com.sjl.yuehu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.CollectBean;
import com.sjl.yuehu.ui.fragment.CollectMineFg;
import com.sjl.yuehu.util.SpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by 小鹿 on 2017/2/22.
 */
public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {
    private OnClickListener listener;
    private Context context;
    private RealmResults<CollectBean> data;

    public CollectAdapter(Context context, CollectMineFg collectMineFg) {
        this.context = context;
        this.listener = collectMineFg;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_collect, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void addData(RealmResults<CollectBean> beans) {
        this.data = beans;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.iv_zhizhu_item_image)
        ImageView img;
        private CollectBean bean;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            RxView.clicks(itemView).subscribe(aVoid -> {
                if (listener != null) {
                    listener.click(bean.getId());
                    notifyDataSetChanged();
                }
            });
        }

        public void bindData(CollectBean bean) {
            this.bean = bean;
            tvTitle.setText(bean.getTitle());
            if (bean.getIcon() != null) {
                Glide.with(context)
                        .load(bean.getIcon())
                        .error(R.mipmap.bg_about)
                        .into(img);
            } else {
                img.setVisibility(View.GONE);
            }
        }
    }

    public interface OnClickListener {
        void click(int id);
    }
}
