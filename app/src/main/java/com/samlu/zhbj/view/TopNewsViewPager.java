package com.samlu.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sam lu on 2019/12/16.
 */

public class TopNewsViewPager extends ViewPager {

    private int startY;
    private int startX;
    private int endX;
    private int exdY;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //1、上下划动，需要父控件拦截
    // 2、左划到最后一张图片，需要父控件拦截
    //3、右划到第一张图片，需要父控件拦截
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = (int) ev.getX();
                exdY = (int) ev.getY();

                int dx = endX - startX;
                int dy = exdY - startY;

                if (Math.abs(dx)>Math.abs(dy)){
                    int currentItem = getCurrentItem();
                    //左右划动
                    if (dx > 0){
                        //向右划动
                        if (currentItem == 0){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else {
                        //向左划动
                        if (currentItem == getAdapter().getCount() - 1){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }else {
                    //上下划动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;

        }
        return super.dispatchTouchEvent(ev);
    }
}
