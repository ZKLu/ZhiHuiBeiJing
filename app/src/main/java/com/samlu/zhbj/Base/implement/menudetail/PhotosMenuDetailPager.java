package com.samlu.zhbj.Base.implement.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.samlu.zhbj.Base.BaseMenuDetailPager;
import com.samlu.zhbj.R;
import com.samlu.zhbj.domain.PhotosBean;
import com.samlu.zhbj.global.GlobalConstants;
import com.samlu.zhbj.utils.CacheUtil;

import java.util.ArrayList;

/**菜单详情页：组图
 * Created by sam lu on 2019/12/15.
 */

public class PhotosMenuDetailPager extends BaseMenuDetailPager implements View.OnClickListener{

    @ViewInject(R.id.lv_list)
    private ListView lv_list;
    @ViewInject(R.id.gv_list)
    private GridView gv_list;

    private ArrayList<PhotosBean.PhotoNews> mPhotoList;
    private ImageButton ib_menu;

    public PhotosMenuDetailPager(Activity activity, ImageButton ib_menu) {
        super(activity);
        this.ib_menu = ib_menu;
        ib_menu.setOnClickListener(this);

    }

    @Override
    public View initView() {
        /*TextView view = new TextView(mActivity);
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        view.setText("菜单详情页组图");*/

        View view = View.inflate(mActivity, R.layout.pager_photos_menu_detail,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtil.getCache(mActivity, GlobalConstants.PHOTO_URL);
        if (!TextUtils.isEmpty(cache)){
            processData(cache);
        }
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstants.PHOTO_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                processData(result);
                CacheUtil.setCache(mActivity,GlobalConstants.PHOTO_URL,result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    private void processData(String result) {
        Gson gson = new Gson();
        PhotosBean photosBean = gson.fromJson(result, PhotosBean.class);
        mPhotoList = photosBean.data.news;

        lv_list.setAdapter(new PhotosAdapter());
        gv_list.setAdapter(new PhotosAdapter());
    }

    private boolean isListView = true;//判断是否是ListView
    @Override
    public void onClick(View v) {
        if (isListView){
            lv_list.setVisibility(View.GONE);
            gv_list.setVisibility(View.VISIBLE);

            ib_menu.setImageResource(R.drawable.icon_pic_list_type);

            isListView = false;
        }else {
            lv_list.setVisibility(View.VISIBLE);
            gv_list.setVisibility(View.GONE);

            ib_menu.setImageResource(R.drawable.icon_pic_grid_type);

            isListView = true;
        }
    }

    class PhotosAdapter extends BaseAdapter{

        private final BitmapUtils mBitmapUtils;

        public PhotosAdapter(){
            mBitmapUtils = new BitmapUtils(mActivity);
            mBitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mPhotoList.size();
        }

        @Override
        public PhotosBean.PhotoNews getItem(int position) {
            return mPhotoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                convertView = View.inflate(mActivity,R.layout.list_item_photo,null);
                holder = new ViewHolder();
                holder.iv_pic = convertView.findViewById(R.id.iv_pic);
                holder.tv_title = convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            PhotosBean.PhotoNews item = getItem(position);
            holder.tv_title.setText(item.title);
            mBitmapUtils.display(holder.iv_pic,item.listimage);
            return convertView;
        }
    }

    class ViewHolder{
        public ImageView iv_pic;
        public TextView tv_title;
    }

}
