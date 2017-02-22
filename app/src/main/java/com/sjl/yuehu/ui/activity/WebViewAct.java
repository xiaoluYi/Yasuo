package com.sjl.yuehu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.CollectBean;
import com.sjl.yuehu.data.bean.WebBean;
import com.sjl.yuehu.data.bean.WebExtraBean;
import com.sjl.yuehu.mvp.presenter.WebViewPresenter;
import com.sjl.yuehu.mvp.view.WebViewMvpView;
import com.sjl.yuehu.ui.activity.CommentAct;
import com.sjl.yuehu.ui.base.BaseAct;
import com.sjl.yuehu.util.HtmlUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 小鹿 on 2017/2/10.
 */
public class WebViewAct extends BaseAct implements WebViewMvpView, View.OnClickListener {
    @Inject
    WebViewPresenter presenter;
    @Inject
    Realm realm;
    @Bind(R.id.web)
    WebView webView;
    @Bind(R.id.top_icon)
    ImageView topIcon;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.session)
    TextView session;
    @Bind(R.id.fl)
    FrameLayout fl;
    @Bind(R.id.iv_like)
    ImageView ivLike;
    @Bind(R.id.tv_like_count)
    TextView tvLikeCount;
    @Bind(R.id.ll_like_count)
    LinearLayout llLikeCount;
    @Bind(R.id.tv_comment_count)
    TextView tvCommentCount;
    @Bind(R.id.ll_comment_count)
    LinearLayout llCommentCount;
    @Bind(R.id.collect)
    ImageView collect;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private int id;
    private WebExtraBean bean;
    private boolean isLike;
    private boolean isCollect;
    private WebBean data_realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.web_fg);
        presenter.attachView(this);
        ButterKnife.bind(this);
        initToolbar();
        init();
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.mipmap.ic_back_toolbar);
        toolbar.setNavigationOnClickListener(this);
        RxView.clicks(llCommentCount).subscribe(aVoid -> {
//            goComments();
        });
        RxView.clicks(llLikeCount).subscribe(aVoid -> {
            goCustomToa();
        });
        RxView.clicks(collect).subscribe(aVoid -> {
            goCollect();
        });

    }

    private void showCollect() {
        RealmResults<CollectBean> results = realm.where(CollectBean.class).findAll();
        for (CollectBean item : results) {
            if (item.getId() == id) {
                isCollect = item.isCollect();
            }
        }
        if (isCollect) {
            collect.setImageResource(R.mipmap.collect_toolbar);
        } else {
            collect.setImageResource(R.mipmap.nocollect_toolbar);
        }

    }

    private void goCollect() {
        if (isCollect) {
            collect.setImageResource(R.mipmap.nocollect_toolbar);
            cancelCollect();
            isCollect=false;
        } else {
            collect.setImageResource(R.mipmap.collect_toolbar);
            startCollect();
            isCollect=true;
        }
    }

    private void cancelCollect() {
        RealmResults<CollectBean> results = realm.where(CollectBean.class).equalTo("id", this.id).findAll();
        realm.executeTransaction(realm1 -> results.deleteAllFromRealm());
    }

    private void startCollect() {
        String imgurl;
        if(data_realm.getImages()!=null && data_realm.getImages().size()!=0){
            imgurl = data_realm.getImages().get(0);
        } else {
            imgurl=null;
        }
        CollectBean cbean = new CollectBean(id, data_realm.getTitle(), imgurl, true);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(cbean);
            }
        });
    }

    private void goCustomToa() {
        isLike = !isLike;
        if (isLike) {
            ivLike.setImageResource(R.mipmap.praised_toolbar);
            tvLikeCount.setText((bean.getPopularity() + 1) + "");
        } else {
            ivLike.setImageResource(R.mipmap.nopraise_toolbar);
            tvLikeCount.setText(bean.getPopularity() + "");
        }
    }

    private void goComments() {
        Intent intent = new Intent(this, CommentAct.class);
        intent.putExtra("id", id);
        intent.putExtra("count", bean.getLong_comments() + bean.getShort_comments());
        startActivity(intent);
    }

    private void init() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        if (id != 0) {
            presenter.onLoad(id);
            presenter.onLoadExtra(id);
        }
        WebSettings settings = webView.getSettings();
        //设置缓存
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        showCollect();
    }

    @Override
    public void onLoad(WebBean bean) {
        this.data_realm = bean;
        if (bean != null) {
            if (bean.getImage() != null) {
                Glide.with(this)
                        .load(bean.getImage())
                        .error(R.mipmap.bg_about)
                        .into(topIcon);
                title.setText(bean.getTitle());
                session.setText(bean.getImage_source());
            } else {
                fl.setVisibility(View.GONE);
            }
            String htmlData = HtmlUtil.createHtmlData(bean.getBody(), bean.getCss(), bean.getJs());
            webView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
        }
    }

    @Override
    public void onLoadExtra(WebExtraBean bean) {
        if (bean != null) {
            this.bean = bean;
            tvCommentCount.setText(bean.getComments() + "");
            tvLikeCount.setText(bean.getPopularity() + "");
        }
    }


    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
