package com.samlu.zhbj.domain;

import java.util.ArrayList;

/**分类数据的分装
 * Created by sam lu on 2019/12/15.
 */

public class NewsMenu {
    public int retcode;

    public ArrayList<NewsMenuData> data;

    //分类菜单的信息：新闻、专题、组图、互动
    public class NewsMenuData{
        public String id;
        public String title;
        public int type;
        public ArrayList<NewsTabData> children;
    }

    //12个变迁的对象封装
    public class NewsTabData{
        public String id;
        public String title;
        public int type;
        public String url;
    }

}
