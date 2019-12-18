package com.samlu.zhbj.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.samlu.zhbj.domain.NewsTab;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**网络缓存工具类
 * Created by sam lu on 2019/12/18.
 */

public class NetCacheUtils {



    public void getBitmapFromNet(ImageView imageView, String url) {
        //异步下载图片
        new BitmapTask().execute(imageView,url);
    }
    class BitmapTask extends AsyncTask<Object,Void,Bitmap>{

        ImageView imageView;
        private String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            imageView = (ImageView) params[0];
            url = (String) params[1];

            //使用url下载图片
            Bitmap bitmap = download(url);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //给ImageView设置图片
            //由于ListView的重用机制，导致某个item有可能展示它重用的那个item的图片
            //解决办法：确保当前设置的图片与当前显示的imageview完全匹配
            if (result != null){
                String url = (String) imageView.getTag();
                if (this.url.equals(url)){
                    //判断当前图片的url是否与imageview的url一致，如果一致，说明图片正确
                    imageView.setImageBitmap(result);
                    Log.e("NetCacheUtils","从网络下载图片了");
                }

            }

        }
    }

    /**根据url下载图片
    *@param
    *@return
    */
    private Bitmap download(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(6000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200){
                InputStream is = conn.getInputStream();
                //使用输入流生成bitmap对象
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn != null){
                conn.disconnect();
            }
        }
        return null;
    }
}
