package com.samlu.zhbj.utils;

import android.content.Context;

/**网络缓存工具类
 * Created by sam lu on 2019/12/15.
 */

public class CacheUtil {

    /**写缓存
    *@param url 以url为标识，以json为值，保存在本地
    *@return
    */
    public static void setCache(Context ctx,String url, String json){
        SPUtil.putString(ctx,url,json);
    }
    /**读缓存
    *@param
    *@return
    */
    public static String getCache(Context ctx,String url){
        return SPUtil.getString(ctx,url,null);
    }
}
