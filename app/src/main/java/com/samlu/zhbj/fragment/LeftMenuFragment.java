package com.samlu.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.samlu.zhbj.Base.BasePager;
import com.samlu.zhbj.R;
import com.samlu.zhbj.domain.NewsMenu;

import java.util.ArrayList;

/**侧边栏fragment
 * Created by sam lu on 2019/12/13.
 */

public class LeftMenuFragment extends BaseFragment {

    @ViewInject(R.id.lv_menu)
    private ListView lv_menu;

    private ArrayList<NewsMenu.NewsMenuData> data;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu,null);
        ViewUtils.inject(this,view);
        return view;
    }

    //设置侧边栏数据
    //通过此方法，可以从NewsPager传递数据过来
    public void setMenuData(ArrayList<NewsMenu.NewsMenuData> data) {
        this.data = data;
        lv_menu.setAdapter(new leftMenuAdatper());
    }

    class  leftMenuAdatper extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public NewsMenu.NewsMenuData getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity,R.layout.list_item_left_menu,null);
            TextView tv_menu = view.findViewById(R.id.tv_menu);
            tv_menu.setText(getItem(position).title);
            return view;
        }
    }
}
