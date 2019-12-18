package com.samlu.zhbj.utils.bitmap;

import android.widget.ImageView;

/**自定义三级缓存工具类
 * Created by sam lu on 2019/12/18.
 */

public class MyBitmapUtils {

    private final NetCacheUtils mNetCacheUtil;

    public MyBitmapUtils(){
        mNetCacheUtil = new NetCacheUtils();
    }
    /**加载图片进行展示。内存缓存、本地缓存、网络缓存
    *@param
    *@return
    */
    public void display(ImageView imageView, String url) {
        imageView.setTag(url);//给当前ImageView打一个标签
        mNetCacheUtil.getBitmapFromNet(imageView,url);
    }
}
