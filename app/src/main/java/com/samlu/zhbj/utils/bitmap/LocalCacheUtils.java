package com.samlu.zhbj.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.samlu.zhbj.utils.MD5Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**本地缓存工具类
 * Created by sam lu on 2019/12/18.
 */

public class LocalCacheUtils {

    String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            +"/zhbj_cache/";

    /**写缓存
    *@param
    *@return
    */
    public void setLocalCache(String url,Bitmap bitmap){
        //将图片保存在本地文件

        File dir = new File(PATH);
        if (!dir.exists() || dir.isDirectory()){
            dir.mkdir();//创建文件夹
        }
        try {
            //为了避免url中的字符无法作为文件名，用md5修改url
            File cacheFile = new File(dir, MD5Encoder.encode(url));
            //将图片压缩保存在本地，参1是图片格式，参2是压缩比（0~100），3是输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(cacheFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**读缓存
    *@param
    *@return
    */
    public Bitmap getLocalCache(String url){
        try {
            File cacheFile = new File(PATH,MD5Encoder.encode(url));
            if (cacheFile.exists()){
                //本地缓存存在
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(cacheFile));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
