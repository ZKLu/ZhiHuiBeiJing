package com.samlu.zhbj.Base.implement.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.samlu.zhbj.Base.BaseMenuDetailPager;

/**菜单详情页：互动
 * Created by sam lu on 2019/12/15.
 */

public class InteractMenuDetailPager extends BaseMenuDetailPager {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView view = new TextView(mActivity);
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        view.setText("菜单详情页互动");

        return view;
    }
}
