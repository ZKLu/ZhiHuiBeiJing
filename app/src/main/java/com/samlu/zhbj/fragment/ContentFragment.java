package com.samlu.zhbj.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.samlu.zhbj.Base.BasePager;
import com.samlu.zhbj.MainActivity;
import com.samlu.zhbj.R;
import com.samlu.zhbj.Base.implement.GovPager;
import com.samlu.zhbj.Base.implement.HomePager;
import com.samlu.zhbj.Base.implement.NewsPager;
import com.samlu.zhbj.Base.implement.SettingPager;
import com.samlu.zhbj.Base.implement.SmartPager;
import com.samlu.zhbj.view.NoScrollViewPager;

import java.util.ArrayList;

/**主页面fragment
 * Created by sam lu on 2019/12/13.
 */

public class ContentFragment extends BaseFragment {
    @ViewInject(R.id.vp_content)
    private NoScrollViewPager vp_content;
    private ArrayList<BasePager> mList ;
    @ViewInject(R.id.rg_group) //XUtils的注解式findViewById()
    private RadioGroup rg_group;


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        ViewUtils.inject(this,view);
//        vp_content = view.findViewById(R.id.vp_content);
//        rg_group = view.findViewById(R.id.rg_group);
        return view;
    }

    @Override
    public void initData() {
        //初始化5个页面对象
        mList = new ArrayList<>();
        mList.add(new HomePager(mActivity));
        mList.add(new NewsPager(mActivity));
        mList.add(new SmartPager(mActivity));
        mList.add(new GovPager(mActivity));
        mList.add(new SettingPager(mActivity));

        vp_content.setAdapter(new ContentAdapter());

        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        //第二个参数，为false为去掉页面切换动画
                    vp_content.setCurrentItem(0,false);
                    break;
                    case R.id.rb_news:
                    vp_content.setCurrentItem(1,false);
                    break;
                    case R.id.rb_smart:
                    vp_content.setCurrentItem(2,false);
                    break;
                    case R.id.rb_gov:
                    vp_content.setCurrentItem(3,false);
                    break;
                    case R.id.rb_setting:
                    vp_content.setCurrentItem(4,false);
                    break;
                }
            }
        });

        //监听ViewPager的页面切换事件，初始化页面数据。
        // 使用这种方法会导致只有切换的页面的数据会初始化，新打开应用时，第一页的数据不会加载
        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mList.get(position).initData();
                if (position ==0 ||position == mList.size()-1){
                    setSlidingMenuEnable(false);
                }else {
                    setSlidingMenuEnable(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //手动初始化页面第一个数据
        mList.get(0).initData();
        //手动禁用第一个页面的侧边栏
        setSlidingMenuEnable(false);
    }
    /**开启或禁用侧边栏
    *@param
    *@return
    */
    private void setSlidingMenuEnable(boolean enable){
        //获取SlidingMenu对象
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    /**获取新闻中心对象
    *@param
    *@return
    */
    public NewsPager getNewsPager(){
        NewsPager pager = (NewsPager) mList.get(1);
        return pager;
    }

    class ContentAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //获取当前页面的对象
            BasePager pager = mList.get(position);

            //此方法导致每次都提前下一个标签页的数据，浪费流量和性能，因为预加载的标签页可能用户不会访问。不建议在此调用此方法
            //初始化布局,以子类实现为准
//            pager.initData();

            //获取布局对象
            //pager.mRootView当前页面的根布局
            container.addView(pager.mRootView);

            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
