package com.samlu.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.samlu.zhbj.Base.implement.NewsPager;
import com.samlu.zhbj.MainActivity;
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

    private int mCurrentPosition;

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

        mCurrentPosition = 0;

        final leftMenuAdapter mAdapter = new leftMenuAdapter();

        lv_menu.setAdapter(mAdapter);

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //更新当前点击位置
                mCurrentPosition = position;
                //刷新ListView
                mAdapter.notifyDataSetChanged();
                //收回侧边栏
                toggle();

                //更新NewsPager中的帧布局
                setMenuDetailPager(position);
            }
        });
    }

    /**修改菜单详情页
    *@param
    *@return
    */
    private void setMenuDetailPager(int position) {
        //获取NewsPager对象
        MainActivity mainUI = (MainActivity) mActivity;
        ContentFragment fragment = mainUI.getContentFragment();
        NewsPager pager = fragment.getNewsPager();
        pager.setMenuDetailPager(position);
    }

    class leftMenuAdapter extends BaseAdapter{

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

            //设置TextView的可用和不可用，控制文字颜色
            if (mCurrentPosition == position){
                //当前item被选中
                tv_menu.setEnabled(true);
            }else {
                tv_menu.setEnabled(false);
            }

            tv_menu.setText(getItem(position).title);
            return view;
        }
    }

    private void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        //如果当前侧边栏为空，则收回，反之亦然
        slidingMenu.toggle();
    }
}
