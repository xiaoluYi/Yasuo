package com.sjl.yuehu.mvp.view;

import com.sjl.yuehu.data.bean.WebBean;
import com.sjl.yuehu.data.bean.WebExtraBean;
import com.sjl.yuehu.ui.base.MvpView;

/**
 * Created by 小鹿 on 2017/2/13.
 */
public interface WebViewMvpView extends MvpView {
    void onLoad(WebBean bean);

    void onLoadExtra(WebExtraBean bean);
}
