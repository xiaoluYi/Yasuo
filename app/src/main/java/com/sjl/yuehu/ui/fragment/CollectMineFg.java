package com.sjl.yuehu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.yuehu.R;
import com.sjl.yuehu.ui.base.BaseFg;

import butterknife.ButterKnife;

/**
 * Created by 小鹿 on 2017/2/15.
 */
public class CollectMineFg extends BaseFg {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.collectmine_fg, null);
        ButterKnife.bind(this, rootView);
        return rootView;
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

                    fgListener.switchFragment(new HomePageFg(),false);

                    return true;

                }

                return false;
            }
        });
    }

    @Override
    public String getFragmentTitle() {
        return "N个收藏";
    }
}
