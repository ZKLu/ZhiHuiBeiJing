package com.samlu.zhbj.Base;

import android.app.Activity;
import android.view.View;

import com.samlu.zhbj.MainActivity;

/**菜单详情页基类
 * Created by sam lu on 2019/12/15.
 */

public abstract class BaseMenuDetailPager {

    public Activity mActivity;
    public final View mRootView;//菜单详情页的根布局

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    //没有共同的地方，由子类去实现
    public abstract View initView();

    public void initData(){}

}
