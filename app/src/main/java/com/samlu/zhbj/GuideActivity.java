package com.samlu.zhbj;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/*
* 新手引导页
* */
public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    //图片ID集合
    private int[] mImageIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private ArrayList<ImageView> mImageView;
    private LinearLayout ll_container;

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
