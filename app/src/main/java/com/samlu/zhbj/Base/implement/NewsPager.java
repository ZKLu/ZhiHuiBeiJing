package com.samlu.zhbj.Base.implement;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.samlu.zhbj.Base.BaseMenuDetailPager;
import com.samlu.zhbj.Base.BasePager;
import com.samlu.zhbj.Base.implement.menudetail.InteractMenuDetailPager;
import com.samlu.zhbj.Base.implement.menudetail.NewsMenuDetailPager;
import com.samlu.zhbj.Base.implement.menudetail.PhotosMenuDetailPager;
import com.samlu.zhbj.Base.implement.menudetail.TopicMenuDetailPager;
import com.samlu.zhbj.MainActivity;
import com.samlu.zhbj.domain.NewsMenu;
import com.samlu.zhbj.fragment.LeftMenuFragment;
import com.samlu.zhbj.global.GlobalConstants;
import com.samlu.zhbj.utils.CacheUtil;

import java.util.ArrayList;

/**新闻中心
 * Created by sam lu on 2019/12/14.
 */

public class NewsPager extends BasePager {

    private ArrayList<BaseMenuDetailPager> mPagers;
    private NewsMenu mNewsMenu;

    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        //修改标题
        tv_title.setText("新闻");

        String cache = CacheUtil.getCache(mActivity, GlobalConstants.CATEGORY_URL);
        if (!TextUtils.isEmpty(cache)){
            //有缓存
            processData(cache);
        }/*else {
            getDataFromServer();
        }*/
        getDataFromServer();
        //上面的做法是为了用户体验。不用等待也可以看见内容，也可以获得最新内容
    }

    /**从服务器获取数据
    *@param
    *@return
    */
    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstants.CATEGORY_URL,
                new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                processData(responseInfo.result);

                //写缓存
                CacheUtil.setCache(mActivity,GlobalConstants.CATEGORY_URL,responseInfo.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    /**解析Json数据
    *@param
    *@return
    */
    private void processData(String json) {
        Gson gson = new Gson();
        mNewsMenu = gson.fromJson(json, NewsMenu.class);

        //找侧边栏对象
        MainActivity mainUI = (MainActivity) mActivity;
        LeftMenuFragment fragment = mainUI.getLeftMenuFragment();
        fragment.setMenuData(mNewsMenu.data);

        //网络请求成功之后，初始化四个菜单详情页
        mPagers = new ArrayList<>();
        mPagers.add(new NewsMenuDetailPager(mActivity,mNewsMenu.data.get(0).children));
        mPagers.add(new TopicMenuDetailPager(mActivity));
        mPagers.add(new PhotosMenuDetailPager(mActivity,ib_display));
        mPagers.add(new InteractMenuDetailPager(mActivity));

        //设置新闻菜单详情页为默认页
        setMenuDetailPager(0);
    }

    /**修改菜单详情页
    *@param
    *@return
    */
    public void setMenuDetailPager(int position) {
        //修改当前帧布局显示的内容
         BaseMenuDetailPager pager= mPagers.get(position);
        //清除之前帧布局显示的内容，否则会不断的addView叠加在已有的上面
        fl_container.removeAllViews();

        //判断切换的页面是否是组图，如果是，显示切换按钮，否则隐藏
        if (pager instanceof PhotosMenuDetailPager){
            ib_display.setVisibility(View.VISIBLE);
        }else {
            ib_display.setVisibility(View.GONE);
        }

        fl_container.addView(pager.mRootView);
        //初始化当前页的数据
        pager.initData();

        //修改标题
        tv_title.setText(mNewsMenu.data.get(position).title);
    }
}
