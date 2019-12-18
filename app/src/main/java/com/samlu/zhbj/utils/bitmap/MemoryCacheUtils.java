package com.samlu.zhbj.utils.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**内存缓存
 * Created by sam lu on 2019/12/18.
 */

public class MemoryCacheUtils {
    private HashMap<String,SoftReference<Bitmap>> mHashMap = new HashMap<>();
    private final LruCache<String, Bitmap> lruCache;

    public MemoryCacheUtils(){
        //获取虚拟机分配的最大内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        lruCache = new LruCache<String, Bitmap>((int) (maxMemory/8)){
            //返回单个对象占用内存的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //计算图片占用内存的大小
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };
    }

    /**写缓存
    *@param
    *@return
    */
    public void setMemoryCache(String url ,Bitmap bitmap){
        /*//用软引用包装bitmap
        SoftReference<Bitmap> soft = new SoftReference<>(bitmap);
        mHashMap.put(url,soft);*/
        lruCache.put(url,bitmap);
    }

    /**读缓存
    *@param
    *@return
    */
    public Bitmap getMemoryCache(String url){
       /* SoftReference<Bitmap> soft = mHashMap.get(url);
        if (soft != null){
            //从软引用取出当前对象
            Bitmap bitmap = soft.get();
            return bitmap;
        }
        return null;*/
        return lruCache.get(url);
    }
}
