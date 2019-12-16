package com.samlu.zhbj.Base.implement.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.samlu.zhbj.Base.BaseMenuDetailPager;
import com.samlu.zhbj.Base.implement.TabDetailPager;
import com.samlu.zhbj.MainActivity;
import com.samlu.zhbj.R;
import com.samlu.zhbj.domain.NewsMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**菜单详情页：新闻
 * Created by sam lu on 2019/12/15.
 */

public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener{

    private ViewPager vp_news_menu_detail;

    private ArrayList<NewsMenu.NewsTabData> children;

    private ArrayList<TabDetailPager> mPagers;
    private TabPageIndicator indicator;

    private ImageButton ib_next;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsTabData> children) {
        super(activity);
        this.children = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_news_menu_detail,null);
        vp_news_menu_detail = view.findViewById(R.id.vp_news_menu_detail);
        indicator = view.findViewById(R.id.tpi_indicator);
        ib_next  = view.findViewById(R.id.ib_next);

        ib_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos = vp_news_menu_detail.getCurrentItem();
                vp_news_menu_detail.setCurrentItem(++currentPos);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<>();
        for (int i =0 ; i<children.size();i++){
            TabDetailPager pager = new TabDetailPager(mActivity,children.get(i));
            mPagers.add(pager);
        }
        vp_news_menu_detail.setAdapter(new NewsMenuDetailAdapter());
        //将ViewPager与Indicator关联在一起。必须写在ViewPager.setAdapter()后
        indicator.setViewPager(vp_news_menu_detail);

        //设置页面监听，作用是除了在北京标签页，其他页面不能划出侧边栏。
        //但为什么不能给ViewPager设置监听器，因为给ViewPager设置监听器后，ViewPager划动会自动跳转回北京页
        //原因是ViewPager的划动没有通知indicator,indicator自动把页面跳转回去。所以只能给indicator设置监听器
        //vp_news_menu_detail.setOnPageChangeListener(this);
        indicator.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            setSlidingMenuEnable(true);
        }else {
            setSlidingMenuEnable(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class NewsMenuDetailAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mPagers.get(position);
            pager.initData();
            container.addView(pager.mRootView);
            return pager.mRootView;
        }
        /**返回Indicator的标题
        *@param
        *@return
        */
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).title;
        }
    }

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
}
