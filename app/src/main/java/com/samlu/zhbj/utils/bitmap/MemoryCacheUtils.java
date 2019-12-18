package com.samlu.zhbj.utils.bitmap;

import android.graphics.Bitmap;

import java.util.HashMap;

/**内存缓存
 * Created by sam lu on 2019/12/18.
 */

public class MemoryCacheUtils {
    private HashMap<String,Bitmap> mHashMap = new HashMap<>();

    /**写缓存
    *@param
    *@return
    */
    public void setMemoryCache(String url ,Bitmap bitmap){
        mHashMap.put(url,bitmap);
    }

    /**读缓存
    *@param
    *@return
    */
    public Bitmap getMemoryCache(String url){
       return mHashMap.get(url);

    }
}
