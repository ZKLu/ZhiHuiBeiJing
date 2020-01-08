package com.samlu.zhbj;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.samlu.zhbj.fragment.ContentFragment;
import com.samlu.zhbj.fragment.LeftMenuFragment;

/*
* 主页面
* */
public class MainActivity extends SlidingFragmentActivity {

    private static final String TAG_CONTENT = "TAG_CONTENT";
    private static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_menu);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();//屏幕宽度

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(200/340*width);//主页面左边距最大的距离

        initFragment();

    }

    private void initFragment(){
        //获取fragment管理器
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content,new ContentFragment(),TAG_CONTENT);//使用fragment替换现有布局，参数1：当前布局id，参数2：要替换的fragment对象
        transaction.replace(R.id.fl_left_menu,new LeftMenuFragment(),TAG_LEFT_MENU);
        transaction.commit();
    }

    //获取侧边栏对象
    public LeftMenuFragment getLeftMenuFragment(){
        FragmentManager fm = getSupportFragmentManager();
        LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(TAG_LEFT_MENU);
        return fragment;
    }

    public ContentFragment getContentFragment(){
        FragmentManager fm = getSupportFragmentManager();
        ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(TAG_CONTENT);
        return fragment;
    }
}
