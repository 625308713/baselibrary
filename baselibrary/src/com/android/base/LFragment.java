package com.android.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.A.Key;

/**
 * 基础框架
 * @author sunjuncai
 *
 */
public abstract class LFragment extends Fragment{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			resId = bundle.getInt(Key.BUNDLE_RES_ID, -1);
		}
	}
	
	/**
	 * 显示界面
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if(rootView == null){
			rootView = inflater.inflate(resId, null);
		}
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        } 
		_fragment = this;
		_context = this.getActivity();
		if(null != _context){
			prepareView(rootView);
			configWinUI();
		}
		return rootView;
	}
	
	/**
	 * 准备装载组件
	 */
	protected abstract void prepareView(View view);
	
	/**
	 * 配置窗口界面
	 */
	protected abstract void configWinUI();
	
	/** 主View */
	protected View rootView;
	
	/** layout布局 */
	protected int resId = -1;
	
	/** 上下文 */
	protected Context _context = null;
	
	/** 当前Fragment */
	protected Fragment _fragment = null;
	
}
