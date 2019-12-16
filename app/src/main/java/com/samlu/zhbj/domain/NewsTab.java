package com.samlu.zhbj.domain;

import java.util.ArrayList;

/**页签网络数据
 * Created by sam lu on 2019/12/16.
 */

public class NewsTab {

    public NewsTabData data;

    public class NewsTabData{
        public String more;
        public ArrayList<TopNews> topnews;
        public ArrayList<News> news;

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "more='" + more + '\'' +
                    '}';
        }
    }

    public class TopNews{
        public String id;
        public String pubdate;
        public String title;
        public String topimage;
        public String url;

        @Override
        public String toString() {
            return "TopNews{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    public class News{
        public String id;
        public String pubdate;
        public String title;
        public String listimage;
        public String url;

        @Override
        public String toString() {
            return "News{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

}
