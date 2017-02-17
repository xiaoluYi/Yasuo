package com.sjl.yuehu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.HomeBean;
import com.sjl.yuehu.mvp.presenter.HomePagePresenter;
import com.sjl.yuehu.mvp.view.HomePageMvpView;
import com.sjl.yuehu.ui.activity.MainAct;
import com.sjl.yuehu.ui.base.BaseFg;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/4.
 */
public class SplashFragment extends BaseFg implements HomePageMvpView {
    @Inject
    HomePagePresenter presenter;
    @Bind(R.id.splash)
    ImageView splash;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getFragmentComponent().inject(this);
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, rootView);
        presenter.attachView(this);
        init();
        return rootView;
    }

    private void init() {
        presenter.onLoadLatest();
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(getContext(), MainAct.class);
                    startActivity(intent);
                    getActivity().finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onList(HomeBean jsonObject) {
        if (jsonObject != null) {
            List<HomeBean.TopStoriesBean> data = jsonObject.getTop_stories();
            int position = (int)(Math.random() * 4);
            Glide.with(getContext())
                    .load(data.get(position).getImage())
                    .error(R.drawable.mask)
                    .into(splash);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
