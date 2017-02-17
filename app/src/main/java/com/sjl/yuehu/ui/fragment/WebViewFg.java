package com.sjl.yuehu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.WebBean;
import com.sjl.yuehu.mvp.presenter.WebViewPresenter;
import com.sjl.yuehu.mvp.view.WebViewMvpView;
import com.sjl.yuehu.ui.base.BaseFg;
import com.sjl.yuehu.util.HtmlUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/10.
 */
public class WebViewFg extends BaseFg implements WebViewMvpView {
    @Inject
    WebViewPresenter presenter;
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
    private WebBean bean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getFragmentComponent().inject(this);
        View rootView = View.inflate(getContext(), R.layout.web_fg, null);
        ButterKnife.bind(this, rootView);
        presenter.attachView(this);
        init();
        return rootView;
    }

    private void init() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int id = arguments.getInt("id");
            presenter.onLoad(id);
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
    }

    @Override
    public void onLoad(WebBean bean) {
        if (bean != null) {
            this.bean = bean;
            if (bean.getImage() != null) {
                Glide.with(getContext())
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    getFragmentManager().popBackStack();

                    return true;

                }

                return false;
            }
        });
    }
}
