package com.samlu.zhbj.fragment;

import android.view.View;

import com.samlu.zhbj.R;

/**主页面fragment
 * Created by sam lu on 2019/12/13.
 */

public class ContentFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        return view;
    }
}
