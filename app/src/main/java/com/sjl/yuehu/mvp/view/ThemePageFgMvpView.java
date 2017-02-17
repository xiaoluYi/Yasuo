package com.sjl.yuehu.mvp.view;

import com.sjl.yuehu.data.bean.ThemesBean;
import com.sjl.yuehu.ui.base.MvpView;

/**
 * Created by 小鹿 on 2017/2/10.
 */
public interface ThemePageFgMvpView extends MvpView{
    void onLoad(ThemesBean bean);
}
