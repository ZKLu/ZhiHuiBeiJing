package com.samlu.zhbj.fragment;

import android.view.View;

import com.samlu.zhbj.R;

/**侧边栏fragment
 * Created by sam lu on 2019/12/13.
 */

public class LeftMenuFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu,null);
        return view;
    }
}
