package com.samlu.zhbj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**Fragment的基类
 * Created by sam lu on 2019/12/13.
 */

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    private View view;

    /**fragment的创建
    *@param 
    *@return 
    */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取fragment所依赖的activity对象，为什么要这样做，因为fragment没有继承context,所以只有使用所依赖的activity
        mActivity = getActivity();
    }

    /**初始化fragment的布局
    *@param 
    *@return 
    */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这个是父类，正常来说是不知道子类的具体的布局，具体的布局的实现是由子类去实现
        view = initView();
        return view;
        
    }

    /**fragment所在的activity创建完成
    *@param 
    *@return 
    */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
    }

    /**继承的子类必须实现这个方法，完成布局
    *@param 
    *@return 
    */
    public abstract View initView();
    
}
