package com.samlu.zhbj.Base.implement;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.samlu.zhbj.Base.BaseMenuDetailPager;
import com.samlu.zhbj.R;
import com.samlu.zhbj.domain.NewsMenu;
import com.samlu.zhbj.domain.NewsTab;
import com.samlu.zhbj.global.GlobalConstants;
import com.samlu.zhbj.utils.CacheUtil;
import com.samlu.zhbj.view.RefreshListView;
import com.samlu.zhbj.view.TopNewsViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by sam lu on 2019/12/15.
 */

public class TabDetailPager extends BaseMenuDetailPager {

    private NewsMenu.NewsTabData newsTabData;
    private TopNewsViewPager vp_tab_detail;
    private ArrayList<NewsTab.TopNews> mTopNewsList;
    private ArrayList<NewsTab.News> mNewsList;
    private TextView tv_title;
    private CirclePageIndicator cpi_circle;
    private RefreshListView  lv_list;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        this.newsTabData = newsTabData;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_tab_detail,null);

        lv_list = view.findViewById(R.id.lv_list);

        //图片轮播的布局
        View headerView = View.inflate(mActivity,R.layout.list_item_header,null);
        vp_tab_detail = headerView.findViewById(R.id.vp_tab_detail);
        tv_title = headerView.findViewById(R.id.tv_title);
        cpi_circle = headerView.findViewById(R.id.cpi_circle);
        lv_list.addHeaderView(headerView);
        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtil.getCache(mActivity, GlobalConstants.SERVER_URL + newsTabData.url);
        if (!TextUtils.isEmpty(cache)){
            processData(cache);
        }
        getDataFromServer();
    }

    private void getDataFromServer() {

        HttpUtils util = new HttpUtils();
        util.send(HttpRequest.HttpMethod.GET, GlobalConstants.SERVER_URL + newsTabData.url, new RequestCallBack<String>(){
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                processData(result);
                CacheUtil.setCache(mActivity,GlobalConstants.SERVER_URL + newsTabData.url,result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    private void processData(String result) {
        Gson gson = new Gson();
        NewsTab newsTab = gson.fromJson(result, NewsTab.class);
        mTopNewsList = newsTab.data.topnews;
        if (mTopNewsList != null){
            vp_tab_detail.setAdapter(new TopNewsAdapter());

            //将指示器与ViewPager绑定
            cpi_circle.setViewPager(vp_tab_detail);
            cpi_circle.setSnap(true);
            cpi_circle.onPageSelected(0);
            cpi_circle.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tv_title.setText(mTopNewsList.get(position).title);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //初始化第一张图片的新闻标题
            tv_title.setText(mTopNewsList.get(0).title);
        }
        mNewsList = newsTab.data.news;
        if (mNewsList != null){
            lv_list.setAdapter(new NewsAdapter());
        }
    }

    class TopNewsAdapter extends PagerAdapter{

        private final BitmapUtils mBitmapUtil;

        public TopNewsAdapter(){
            //加载图片的工具类
            mBitmapUtil = new BitmapUtils(mActivity);
            //设置加载中的默认图片
            mBitmapUtil.configDefaultLoadingImage(R.drawable.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mTopNewsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
            NewsTab.TopNews topNews = mTopNewsList.get(position);
            String topImage = topNews.topimage;//图片的下载连接
            //设置缩放模式，图片宽高匹配窗体
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            //1、根据url下载图片2、将图片设置给ImageView3、图片缓存4、避免内存溢出
            mBitmapUtil.display(view,topImage);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class NewsAdapter extends BaseAdapter{

        private final BitmapUtils utils;

        public NewsAdapter(){
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.drawable.news_pic_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public NewsTab.News getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                convertView = View.inflate(mActivity,R.layout.list_item_news,null);
                holder = new ViewHolder();
                holder.iv_news = convertView.findViewById(R.id.iv_news);
                holder.tv_title = convertView.findViewById(R.id.tv_title);
                holder.tv_time = convertView.findViewById(R.id.tv_time);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            NewsTab.News info = getItem(position);
            holder.tv_title.setText(info.title);
            holder.tv_time.setText(info.pubdate);

            utils.display(holder.iv_news,info.listimage);

            return convertView;
        }
    }

    static class ViewHolder{
        public ImageView iv_news;
        public TextView tv_title;
        public TextView tv_time;

    }
}
