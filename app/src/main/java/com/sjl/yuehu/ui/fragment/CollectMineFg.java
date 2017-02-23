package com.sjl.yuehu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sjl.yuehu.R;
import com.sjl.yuehu.data.bean.CollectBean;
import com.sjl.yuehu.ui.activity.WebViewAct;
import com.sjl.yuehu.ui.adapter.CollectAdapter;
import com.sjl.yuehu.ui.base.BaseFg;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 小鹿 on 2017/2/15.
 */
public class CollectMineFg extends BaseFg implements CollectAdapter.OnClickListener {
    @Inject
    Realm realm;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.tip)
    TextView tip;
    private CollectAdapter adapter;
    private RealmResults<CollectBean> beans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getFragmentComponent().inject(this);
        View rootView = View.inflate(getContext(), R.layout.collectmine_fg, null);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        adapter = new CollectAdapter(getContext(), this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        beans = realm.where(CollectBean.class).findAll();
        if (beans != null && beans.size() != 0) {
            adapter.addData(beans);
            adapter.notifyDataSetChanged();
        } else {
            tip.setVisibility(View.VISIBLE);
        }
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

                    fgListener.switchFragment(new HomePageFg(), false);

                    return true;

                }

                return false;
            }
        });

        adapter.notifyDataSetChanged();

    }

    @Override
    public String getFragmentTitle() {
        if (beans != null && beans.size() != 0) {
            return beans.size() + "个收藏";
        } else {
            return "0个收藏";
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void click(int id) {
        Intent intent = new Intent(getContext(), WebViewAct.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
