package com.samlu.zhbj.Base.implement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.samlu.zhbj.Base.BasePager;
import com.samlu.zhbj.domain.NewsMenu;
import com.samlu.zhbj.global.GlobalConstants;

/**新闻中心
 * Created by sam lu on 2019/12/14.
 */

public class NewsPager extends BasePager {
    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        //给空的帧布局动态怎加布局对象
        TextView view = new TextView(mActivity);
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        view.setText("新闻中心");
        fl_container.addView(view);

        //修改标题
        tv_title.setText("新闻");
        
        getDataFromServer();
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
        NewsMenu newsMenu = gson.fromJson(json, NewsMenu.class);

    }


}
