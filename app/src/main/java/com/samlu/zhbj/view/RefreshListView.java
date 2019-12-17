package com.samlu.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.samlu.zhbj.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sam lu on 2019/12/16.
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener{

    private View mHeaderView;
    private int mHeaderViewHeight;

    private View mFooterView;
    private int mFooterViewHeight;

    private static final int STATE_PULL_TO_REFRESH = 0;//下拉刷新
    private static final int STATE_RELEASH_TO_REFRESH = 1;//松开刷新
    private static final int STATE_REFRESHING = 2;//正在刷新

    private int mCurrentState = STATE_PULL_TO_REFRESH; //当前状态默认是下拉刷新
    private TextView tv_state;
    private TextView tv_time;
    private ImageView iv_arrow;
    private ProgressBar pb_loading;

    private RotateAnimation animUp;
    private RotateAnimation animDown;

    private boolean isLoadMore = false;


    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
    }

    //初始化头布局
    private void initHeaderView(){
        mHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh, null);
        addHeaderView(mHeaderView);

        //隐藏头布局
        mHeaderView.measure(0,0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);

        tv_state = mHeaderView.findViewById(R.id.tv_state);
        tv_time = mHeaderView.findViewById(R.id.tv_time);
        iv_arrow = mHeaderView.findViewById(R.id.iv_arrow);
        pb_loading = mHeaderView.findViewById(R.id.pb_loading);

        initArrowAnim();
        //更新刷新时间
        setRefreshTime();
    }

    private void initFooterView(){
        mFooterView = View.inflate(getContext(), R.layout.pull_to_refresh_footer, null);
        addFooterView(mFooterView);

        mFooterView.measure(0,0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0,-mFooterViewHeight,0,0);

        setOnScrollListener(this);

    }

    private int startY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1){
                    //没有获取到按下的事件，因为按在了图片轮播控件上，按下事件被图片轮播控件消费了
                    startY = (int) ev.getY();
                }

                int endY = (int) ev.getY();
                int dy = endY - startY;

                //如果正在刷新，什么都不做
                if (mCurrentState == STATE_REFRESHING){
                    break;
                }
                int firstVisiblePosition = this.getFirstVisiblePosition();

                if (dy > 0 && firstVisiblePosition == 0){
                    //判断条件：下拉动作和在ListView的顶部
                    int padding = -mHeaderViewHeight + dy;

                    if (padding > 0 && mCurrentState != STATE_RELEASH_TO_REFRESH){
                        //切换到松开刷新状态
                        mCurrentState = STATE_RELEASH_TO_REFRESH;
                        refreshState();
                    }else if (padding <= 0 && mCurrentState != STATE_PULL_TO_REFRESH){
                        //切换到下拉刷新状态
                        mCurrentState = STATE_PULL_TO_REFRESH;
                        refreshState();

                    }

                    mHeaderView.setPadding(0,padding,0,0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (mCurrentState == STATE_RELEASH_TO_REFRESH){
                    mCurrentState = STATE_REFRESHING;
                    mHeaderView.setPadding(0,0,0,0);
                    refreshState();
                }else if (mCurrentState == STATE_PULL_TO_REFRESH){
                    //隐藏HeaderView
                    mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
                }
                break;

        }
        return super.onTouchEvent(ev);
    }

    private void initArrowAnim(){
        animUp = new RotateAnimation(0,-180,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        animUp.setDuration(300);
        animUp.setFillAfter(true);
        animDown = new RotateAnimation(-180,0,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        animUp.setDuration(300);
        animUp.setFillAfter(true);
    }

    /**根据当前状态刷新HeaderView
    *@param
    *@return
    */
    private void refreshState() {
        switch (mCurrentState){
            case STATE_PULL_TO_REFRESH:
                tv_state.setText("下拉刷新");
                pb_loading.setVisibility(View.INVISIBLE);
                iv_arrow.setVisibility(View.VISIBLE);
                iv_arrow.startAnimation(animDown);
                break;
            case STATE_RELEASH_TO_REFRESH:
                tv_state.setText("松开刷新");
                pb_loading.setVisibility(View.INVISIBLE);
                iv_arrow.setVisibility(View.VISIBLE);
                iv_arrow.startAnimation(animUp);
                break;
            case STATE_REFRESHING:
                tv_state.setText("正在刷新");
                pb_loading.setVisibility(View.VISIBLE);
                //只有清理动画才能隐藏
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(View.INVISIBLE);
                if (mListener != null){
                    mListener.onRefresh();
                }
                break;
        }
    }
    //设置刷新时间
    private void setRefreshTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = format.format(new Date());
        tv_time.setText(time);
    }

    public void onRefreshComplete(){
        if (!isLoadMore){
            mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
            //所有状态初始化
            tv_state.setText("下拉刷新");
            pb_loading.setVisibility(View.INVISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);
            mCurrentState = STATE_PULL_TO_REFRESH;

            //更新刷新时间
            setRefreshTime();
        }else {
            //隐藏加载更多
            mFooterView.setPadding(0,-mFooterViewHeight,0,0);
            isLoadMore = false;
        }
    }

    private OnRefreshListener mListener;
    public void setOnRefreshListener(OnRefreshListener listener){
        mListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE){
            //当前显示的最后一个item的位置
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition == getCount()-1 && !isLoadMore ){

                isLoadMore = true;
                //显示加载中
                mFooterView.setPadding(0,0,0,0);
                //显示在最后一个item的位置
                setSelection(getCount()-1);

                //加载更多数据
                if (mListener != null){
                    mListener.onLoadingMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public interface OnRefreshListener{
        void onRefresh();
        void onLoadingMore();
    }
}
