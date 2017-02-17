package com.sjl.yuehu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.yuehu.R;
import com.sjl.yuehu.ui.activity.MainAct;
import com.sjl.yuehu.ui.base.BaseFg;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 小鹿 on 2017/2/4.
 */
public class GuideFragment extends BaseFg {
    @Bind(R.id.tvHello)
    TextView tvHello;
    private final String hello = "<h1>Welcome<h1><p>This application is a simple demonstration of Zhihu Daily Android developed by me. It's a free app. All the information, content and api copyright is belong to Zhihu. Inc.</Br><p>-Hefuyi</p></p><h2>About</h2><p>Email: hefuyicoder@gmail.com</p><p>Github: github.com/hefuyicoder</p>";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, rootView);
        tvHello.setText(Html.fromHtml(hello));
        return rootView;
    }
    @OnClick(R.id.btnEnter)
    public void onEnterClick() {
        Intent intent = new Intent(getContext(), MainAct.class);
        startActivity(intent);
    }
}
