package com.samlu.zhbj.Base.implement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.samlu.zhbj.Base.BaseMenuDetailPager;
import com.samlu.zhbj.domain.NewsMenu;

/**
 * Created by sam lu on 2019/12/15.
 */

public class TabDetailPager extends BaseMenuDetailPager {

    private NewsMenu.NewsTabData newsTabData;
    private TextView textView;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        this.newsTabData = newsTabData;
    }

    @Override
    public View initView() {
        textView = new TextView(mActivity);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        textView.setText(newsTabData.title);
    }
}
