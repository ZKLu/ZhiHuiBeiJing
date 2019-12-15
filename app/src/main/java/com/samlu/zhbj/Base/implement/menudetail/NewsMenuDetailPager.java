package com.samlu.zhbj.Base.implement.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.samlu.zhbj.Base.BaseMenuDetailPager;
import com.samlu.zhbj.Base.implement.TabDetailPager;
import com.samlu.zhbj.R;
import com.samlu.zhbj.domain.NewsMenu;

import java.util.ArrayList;

/**菜单详情页：新闻
 * Created by sam lu on 2019/12/15.
 */

public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private ViewPager vp_news_menu_detail;

    private ArrayList<NewsMenu.NewsTabData> children;

    private ArrayList<TabDetailPager> mPagers;


    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsTabData> children) {
        super(activity);
        this.children = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_news_menu_detail,null);
        vp_news_menu_detail = view.findViewById(R.id.vp_news_menu_detail);

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
    }
}
