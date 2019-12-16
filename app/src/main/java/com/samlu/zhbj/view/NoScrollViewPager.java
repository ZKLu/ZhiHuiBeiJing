package com.samlu.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**禁止滑动的ViewPager
 * Created by sam lu on 2019/12/14.
 */

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //返回true的目的是让ViewPager不响应滑动事件
        return true;
    }

    /**对事件进行拦截。onInterceptTouchEvent()只能用于ViewGroup，所以ViewPager也是继承ViewGroup
    *@param
    *@return 
    */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //返回true表示拦截，返回false表示不拦截，传给子控件
        return false;
    }
}
