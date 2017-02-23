package com.sjl.yuehu.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.stetho.common.LogUtil;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.HomeBean;
import com.sjl.yuehu.mvp.presenter.HomePagePresenter;
import com.sjl.yuehu.mvp.view.HomePageMvpView;
import com.sjl.yuehu.ui.adapter.HomePageAdapter;
import com.sjl.yuehu.ui.base.BaseFg;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/7.
 */
public class HomePageFg extends BaseFg implements HomePageMvpView, HomePageAdapter.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    private HomePageAdapter adapter;
    @Inject
    HomePagePresenter presenter;
    private LinearLayoutManager linearLayoutManager ;
    private boolean isrefresh = true;
    private int i = 1;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            getFragmentComponent().inject(this);
            rootView = View.inflate(getContext(), R.layout.fristpage_fg, null);
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
        presenter.onLoadLatest();
        linearLayoutManager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        adapter = new HomePageAdapter(getContext(), this);
        rv.setAdapter(adapter);
        //获取数据
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeResources(R.color.colorPrimary);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public int lastVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    isrefresh = false;
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, i);
                    String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
                    presenter.onLoadGone(yesterday);
                    LogUtil.e(yesterday + "+++++++++++++++++++++++++++++++++++++++++++++");
                    --i;
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
        return "首页";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onList(HomeBean data) {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
        if (data != null) {
            adapter.addData(data, isrefresh);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void click(int id) {
        WebViewFg webFg = new WebViewFg();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        webFg.setArguments(bundle);
        fgListener.switchFragment(webFg, true);
    }

    @Override
    public void onRefresh() {
        isrefresh = true;
        i = 1;
        presenter.onLoadLatest();
    }

}
