package com.android.base;


/**
 * 页面接口
 * @author sunjuncai
 *
 */
public interface IActivity {
	
	/***
	 * 是否显示标题栏
	 */
	public boolean isShowTitle();
	
	/**
	 * 设置页面主布局
	 */
	public void setContentView();
	
	/**
	 * 加载View
	 */
	public void prepareView();
	
	/**
	 * 设置标题
	 */
	public void setTitle(int resId);
	
	/**
	 * 配置页面显示
	 */
	public void configWinUI();
	
	/**
	 * 请求前验证
	 */
	public boolean requestVerify(int requestCode);
	
	/**
	 * 开始请求
	 * @return
	 */
	public void requestServer(int requestCode);
	
	/**
	 * 请求结果验证
	 */
	public void responseVerify(int requestCode,String result);
	
}
