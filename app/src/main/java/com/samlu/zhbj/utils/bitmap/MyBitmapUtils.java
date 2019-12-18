package com.samlu.zhbj.utils.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**自定义三级缓存工具类
 * Created by sam lu on 2019/12/18.
 */

public class MyBitmapUtils {

    private final NetCacheUtils mNetCacheUtil;
    private final LocalCacheUtils mLocalCacheUtils;
    private final MemoryCacheUtils mMemoryCacheUtils;

    public MyBitmapUtils(){
        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtil = new NetCacheUtils(mLocalCacheUtils,mMemoryCacheUtils);

    }
    /**加载图片进行展示。内存缓存、本地缓存、网络缓存
    *@param
    *@return
    */
    public void display(ImageView imageView, String url) {

        //内存缓存
        Bitmap bitmap = mMemoryCacheUtils.getMemoryCache(url);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        //本地缓存
        bitmap = mLocalCacheUtils.getLocalCache(url);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            //写内存缓存
            mMemoryCacheUtils.setMemoryCache(url,bitmap);
            return;
        }
        //网络缓存
        mNetCacheUtil.getBitmapFromNet(imageView,url);
    }
}
