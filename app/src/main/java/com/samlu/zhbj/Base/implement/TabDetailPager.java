package com.samlu.zhbj.Base.implement;

import android.app.Activity;
import android.view.View;

import com.samlu.zhbj.Base.BaseMenuDetailPager;
import com.samlu.zhbj.domain.NewsMenu;

/**
 * Created by sam lu on 2019/12/15.
 */

public class TabDetailPager extends BaseMenuDetailPager {

    private NewsMenu.NewsTabData newsTabData;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        this.newsTabData = newsTabData;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        //newsTabData.title;
    }
}
