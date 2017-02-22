package com.sjl.yuehu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.stetho.common.LogUtil;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.ThemesBean;
import com.sjl.yuehu.mvp.presenter.ThemePageFgPresenter;
import com.sjl.yuehu.mvp.view.ThemePageFgMvpView;
import com.sjl.yuehu.ui.activity.WebViewAct;
import com.sjl.yuehu.ui.adapter.ThemeAdapter;
import com.sjl.yuehu.ui.base.BaseFg;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/7.
 */
public class ThemePageFg extends BaseFg implements ThemePageFgMvpView, ThemeAdapter.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Inject
    ThemePageFgPresenter presenter;
    String theme;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    private ThemeAdapter adapter;
    private View rootView;
    private LinearLayoutManager linearLayoutManager;
    private boolean isrefresh=true;
    private int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            getFragmentComponent().inject(this);
            rootView = View.inflate(getContext(), R.layout.themepage_fg, null);
            ButterKnife.bind(this, rootView);
            presenter.attachView(this);
            init();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    private void init() {
        Bundle arguments = getArguments();
        if (arguments != null) {
             id = arguments.getInt("id");
            theme = arguments.getString("theme");
            presenter.onLoad(id);
        }
        linearLayoutManager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        adapter = new ThemeAdapter(getContext(), this);
        rv.setAdapter(adapter);
        setRefresh();
    }

    private void setRefresh() {
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeResources(R.color.colorPrimary);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public int lastVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    isrefresh = false;
                     long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
                    String  timestamp=String.valueOf(time);

                    presenter.onLoadGone(timestamp,id);
                    LogUtil.e(id + "+++++++++++++++++++++++++++++++++++++++++++++");
                    LogUtil.e(timestamp + "+++++++++++++++++++++++++++++++++++++++++++++");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public String getFragmentTitle() {
        if (theme != null) {
            return theme;
        } else {
            return "";
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLoad(ThemesBean bean) {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
        if (bean != null) {
            adapter.addData(bean,isrefresh);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void click(int id) {
        Intent intent = new Intent(getContext(), WebViewAct.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        isrefresh = true;
        presenter.onLoad(id);
    }
}
