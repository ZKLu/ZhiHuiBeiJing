package com.samlu.zhbj.Base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.samlu.zhbj.R;

/**5个标签页的基类
 * Created by sam lu on 2019/12/13.
 */

public class BasePager {

    public Activity mActivity;
    public TextView tv_title;
    public ImageButton iv_menu;
    public FrameLayout fl_container;//空的帧布局，由子类动态填充
    public final View mRootView;//当前页面的根布局

    public BasePager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }
    public View initView(){
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        tv_title = view.findViewById(R.id.tv_title);
        iv_menu = view.findViewById(R.id.ib_menu);
        fl_container = view.findViewById(R.id.fl_container);
        return view;
    }
    public void initData(){

    }
}