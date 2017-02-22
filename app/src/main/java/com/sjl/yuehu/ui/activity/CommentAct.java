package com.sjl.yuehu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.sjl.yuehu.R;
import com.sjl.yuehu.ui.base.BaseAct;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/21.
 */
public class CommentAct extends BaseAct implements View.OnClickListener {
    @Bind(R.id.comment_count)
    TextView commentCount;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv)
    RecyclerView rv;
    private int id;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_act);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolbar.setNavigationIcon(R.mipmap.ic_back_toolbar);
        toolbar.setNavigationOnClickListener(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        count = intent.getIntExtra("count", 0);
        if (count != 0) {
            commentCount.setText("" + count);
        }
        if (id != 0) {
//            presenter.onLoad(id);
//            presenter.onLoadExtra(id);
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
