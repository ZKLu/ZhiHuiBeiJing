package com.samlu.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.samlu.zhbj.utils.SPUtil;

import java.util.ArrayList;

/*
* 新手引导页
* */
public class GuideActivity extends Activity {

    private ViewPager mViewPager;
    //图片ID集合
    private int[] mImageIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private ArrayList<ImageView> mImageView;
    private LinearLayout ll_container;
    private int mPointDis;
    private ImageView iv_red_point;
    private Button bt_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        initView();
        initData();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        iv_red_point = (ImageView) findViewById(R.id.iv_red_point);
        bt_start = findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.putBoolean(getApplicationContext(),"is_guide_show",true);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    /**初始化三张ImageView
    *@param
    *@return
    */
    private void initData() {
        mImageView = new ArrayList<>();
        for (int i =0;i<mImageIds.length;i++){
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mImageView.add(view);

            //初始化小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_normal);
            //初始化布局参数
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i >0){
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);
            ll_container.addView(point);

        }
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager的滑动事件，更新红点的位置
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
            *@param positionOffset 位置偏移百分比
            *@return void
            */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //通过修改红点的左边距，达到移动的效果
                int distance = (int) (mPointDis * positionOffset +position * mPointDis);
                //获取红点的布局参数
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
                layoutParams.leftMargin = distance;
                iv_red_point.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageIds.length - 1){
                    bt_start.setVisibility(View.VISIBLE);
                }else {
                    bt_start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //计算红点移动距离
        //mPointDis = ll_container.getChildAt(1).getLeft() - ll_container.getChildAt(0).getLeft();
        //这样是获取获取不到getLeft()的值，因为onMeasure onLayout onDraw 三个方法是在onCreate执行完才执行
        //在onStart onResume里也或许不行，因为这三个方法都是耗时操作，可能执行完onStart onResume，那三个方法也还没执行完
        //只有监听layout的执行，一旦执行，立即获取getLeft()的值

        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**一旦视图树的onLayout()方法完成，就会回调这方法
            *@param
            *@return void
            */
            @Override
            public void onGlobalLayout() {
                //布局位置已经确定，可以拿到位置信息
                mPointDis = ll_container.getChildAt(1).getLeft() - ll_container.getChildAt(0).getLeft();
                //移除观察者
                iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**初始化布局
        *@param
        *@return
        */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImageView.get(position);
            container.addView(imageView);
            return imageView;
        }

        /**删除布局
        *@param
        *@return
        */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
