package com.samlu.zhbj.utils;

import android.content.Context;

/**
 * Created by sam lu on 2019/12/18.
 */

public class DensityUtils {
    /**dp->px
    *@param
    *@return
    */
    public static int dip2px(float dp, Context ctx){

        float dentisy = ctx.getResources().getDisplayMetrics().density;
        //加0.5是为了四舍五入
        int px = (int) (dp * dentisy +0.5);
        return px;
    }

    public static float px2dip(int px, Context ctx){
        float dentisy = ctx.getResources().getDisplayMetrics().density;
        float dip = px/dentisy;
        return dip;
    }
}
