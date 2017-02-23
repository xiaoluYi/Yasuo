package com.sjl.yuehu.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.TitileBean;
import com.sjl.yuehu.mvp.presenter.MainActPresenter;
import com.sjl.yuehu.mvp.view.MainActMvpView;
import com.sjl.yuehu.ui.adapter.RvLeftAdapter;
import com.sjl.yuehu.ui.base.BaseAct;
import com.sjl.yuehu.ui.base.BaseFg;
import com.sjl.yuehu.ui.fragment.CollectMineFg;
import com.sjl.yuehu.ui.fragment.HomePageFg;
import com.sjl.yuehu.ui.fragment.MessageFg;
import com.sjl.yuehu.ui.fragment.ThemePageFg;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/6.
 */
public class MainAct extends BaseAct implements View.OnClickListener, Toolbar.OnMenuItemClickListener, RvLeftAdapter.OnClickListener, MainActMvpView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_left)
    RecyclerView rvLeft;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.notify)
    ImageView notify;
    @Bind(R.id.about)
    ImageView about;
    @Bind(R.id.tv_homepage)
    TextView tv_homepage;
    @Bind(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    ThemePageFg themePageFg;
    @Inject
    MainActPresenter presenter;
    @Bind(R.id.rl_login)
    RelativeLayout rlLogin;
    @Bind(R.id.ll_collect)
    LinearLayout llCollect;
    @Bind(R.id.ll_down)
    LinearLayout llDown;
    @Bind(R.id.rl_fg)
    RelativeLayout rlFg;
    private RvLeftAdapter adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        presenter.attachView(this);
        ButterKnife.bind(this);
        initToolBar();
        init();
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvLeft.setLayoutManager(layoutManager);
        adpater = new RvLeftAdapter(this, this);
        rvLeft.setAdapter(adpater);
        switchFragment(new HomePageFg(), false);
        RxView.clicks(notify).subscribe(aVoid -> {goMessageFg();});
//        RxView.clicks(about).subscribe(aVoid -> {goMore();});
        RxView.clicks(rlFg).subscribe(aVoid -> {goHomePageFg();});
//        RxView.clicks(rlLogin).subscribe(aVoid -> {goNetUser();});
        RxView.clicks(llCollect).subscribe(aVoid -> {goMyCollect();});
//        RxView.clicks(llDown).subscribe(aVoid -> {goDown();});
        presenter.onList();
    }

    private void goDown() {
        Toast.makeText(MainAct.this, "离线下载完成", Toast.LENGTH_SHORT).show();
    }

    private void goMyCollect() {
        switchFragment(new CollectMineFg(), false);
        drawerlayout.closeDrawers();
    }

    private void goNetUser() {
        Toast.makeText(MainAct.this, "登陆个人账号", Toast.LENGTH_SHORT).show();
    }

    private void goHomePageFg() {
        switchFragment(new HomePageFg(), false);
        drawerlayout.closeDrawers();
    }

    private void goMore() {
        Toast.makeText(MainAct.this, "日间模式和夜间模式", Toast.LENGTH_SHORT).show();
    }

    private void goMessageFg() {
        switchFragment(new MessageFg(), false);
    }

    private void initToolBar() {
//        toolbar.inflateMenu(R.menu.zhihu_toolbar_menu);
//        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        toolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void fragmentVisible(BaseFg fragment) {
        super.fragmentVisible(fragment);
        about.setVisibility(View.GONE);
        notify.setVisibility(View.GONE);
        share.setVisibility(View.GONE);
        if (fragment.getName().equals(HomePageFg.class.getName())) {
            about.setVisibility(View.VISIBLE);
            notify.setVisibility(View.VISIBLE);
        } else {
            share.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onClick(View v) {
        presenter.onList();
        drawerlayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(MainAct.this, "查询", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_notification:
                Toast.makeText(MainAct.this, "暂无通知", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about:
                Toast.makeText(MainAct.this, "关于", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(MainAct.this, "设置", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void click(int id, String theme) {
        themePageFg = new ThemePageFg();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("theme", theme);
        themePageFg.setArguments(bundle);
        switchFragment(themePageFg, false);
        drawerlayout.closeDrawers();
    }

    @Override
    public void onList(TitileBean remoteResult) {
        if (remoteResult != null) {
            List<TitileBean.OthersBean> data = remoteResult.getOthers();
            adpater.addData(data);
            adpater.notifyDataSetChanged();
        }
    }
}
