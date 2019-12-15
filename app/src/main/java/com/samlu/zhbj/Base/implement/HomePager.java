package com.samlu.zhbj.Base.implement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.samlu.zhbj.Base.BasePager;

/**首页
 * Created by sam lu on 2019/12/14.
 */

public class HomePager extends BasePager {
    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        //给空的帧布局动态怎加布局对象
        TextView view = new TextView(mActivity);
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        view.setText("首页");
        fl_container.addView(view);

        //修改标题
        tv_title.setText("智慧北京");
        ib_menu.setVisibility(View.GONE);
    }
}
