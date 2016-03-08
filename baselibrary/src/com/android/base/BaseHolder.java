package com.android.base;

import android.view.View;

/**
 * 初始化数据源
 * @author sunjuncai
 *
 * @param <T>
 */
public interface BaseHolder<T> {

	/**
	 * 初始化界面
	 * @param view
	 */
	public void initView(View view);
	
	/**
	 * 绑定监听
	 */
	public void bindComponentEvent();

	/**
	 * 初始化数据
	 * @param t
	 */
	public void initData(T t);

}
