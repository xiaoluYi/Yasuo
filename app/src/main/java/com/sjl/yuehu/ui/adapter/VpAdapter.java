package com.sjl.yuehu.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.HomeBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/15.
 */
public class VpAdapter extends PagerAdapter {
    @Bind(R.id.top_icon)
    ImageView topIcon;
    @Bind(R.id.icon_title)
    TextView iconTitle;
    private List<HomeBean.TopStoriesBean> data;
    private Context context;
//
    public VpAdapter(Context context, List<HomeBean.TopStoriesBean> top_stories) {
        this.context = context;
        this.data = top_stories;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= data.size();
        if (position < 0) {
            position = data.size() + position;
        }
        View view = View.inflate(context, R.layout.top_vp_homepage_fg, null);
        ButterKnife.bind(this, view);
        iconTitle.setText(data.get(position).getTitle());
            Glide.with(context)
                    .load(data.get(position).getImage())
                    .error(R.mipmap.bg_about)
                    .into(topIcon);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        return view;
    }


}
