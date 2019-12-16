package com.samlu.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.samlu.zhbj.R;

/**
 * Created by sam lu on 2019/12/16.
 */

public class RefreshListView extends ListView {

    private View mHeaderView;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    //初始化头布局
    private void initHeaderView(){
        mHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh, null);
        addHeaderView(mHeaderView);

        //隐藏头布局
    }
}
