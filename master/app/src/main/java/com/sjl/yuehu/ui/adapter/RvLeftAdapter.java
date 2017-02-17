package com.sjl.yuehu.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.TitileBean;
import com.sjl.yuehu.ui.activity.MainAct;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/7.
 */
public class RvLeftAdapter extends RecyclerView.Adapter<RvLeftAdapter.ViewHolder> {
    private OnClickListener listener;
    private Context context;
    private List<TitileBean.OthersBean> data;

    //        String[] names=new String[]{"nu","nu2","首页","日常心理学","用户推荐日报","电影日报","不许无聊","设计日报","大公司日报","财经日报","互联网安全","开始游戏","音乐日报","动漫日报","体育日报",};
    public RvLeftAdapter(Context context, MainAct mainAct) {
        this.listener = mainAct;
        this.context = context;
    }

    @Override
    public RvLeftAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_left, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvLeftAdapter.ViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }

    }

    public void addData(List<TitileBean.OthersBean> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv)
        TextView tv;
        private TitileBean.OthersBean bean;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.click(bean.getId(),bean.getName());
                    }
                }
            });
        }

        public void bindData(TitileBean.OthersBean bean) {
            this.bean = bean;
            tv.setText(bean.getName());
        }
    }

    public interface OnClickListener {
        void click(int id, String beanName);
    }
}
