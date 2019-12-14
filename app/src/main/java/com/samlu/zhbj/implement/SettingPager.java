package com.samlu.zhbj.implement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.samlu.zhbj.Base.BasePager;

/**设置
 * Created by sam lu on 2019/12/14.
 */

public class SettingPager extends BasePager {
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        //给空的帧布局动态怎加布局对象
        TextView view = new TextView(mActivity);
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        view.setText("设置");
        fl_container.addView(view);

        //修改标题
        tv_title.setText("设置");
    }
}
