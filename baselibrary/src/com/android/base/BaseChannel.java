package com.android.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 通往主Activity的管道
 * @author sunjuncai
 *
 */
public abstract class BaseChannel {

	/** 上下文 */
	public Context context;
	
	/** 获取 */
	public PullView pullView;
	
	/** 用于反射获取View */
	public LayoutInflater layoutInflater = null;
	
	/**
	 * 
	 * @param _context
	 * @param pullView
	 */
	public BaseChannel(Context _context, PullView _pullView) {
		super();
		this.context = _context;
		this.pullView = _pullView;
		layoutInflater = LayoutInflater.from(_context);
		setContentView();
	}

	/**
	 * 设置页面布局
	 */
	public void setContentView(){
		prepareView();
		configWinUI();
	}
	
	/**
	 * 初始化页面组件
	 */
	public abstract void prepareView();
	
	/**
	 * 配置窗口界面事件
	 */
	public abstract void configWinUI();
	

	/**
	 * 获取页面组件
	 */
	public abstract void findViewById(int viewId);
	
	
	/**
	 * 保存页面的值
	 */
	public interface PullValue<T>{
		
		/**
		 * 根据KEY 保存值
		 * @param key
		 * @param value
		 */
		public void setValue(int position,T value);
		
	}
	
	/**
	 * 获取页面组件接口
	 * @author sunjuncai
	 *
	 */
	public interface PullView{
		
		/**
		 * 回调返回结果
		 * @param type
		 * @return
		 */
		public <T> T returnResult(int type,Object ... param);
		
		/**
		 * 获取页面组件
		 * @param viewId
		 * @return
		 */
		public String getS(int resId);
		
		/**
		 * 获取资源Id
		 * @param resId
		 * @return
		 */
		public View find(int resId);
		
		/**
		 * 获取监听
		 * @return
		 */
		public OnClickListener returnListener();
	}
	
}
