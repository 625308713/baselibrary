package com.android.app;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

import com.android.utils.BaseCrashHandler;
import com.android.utils.CommonUtil;

/**
 * 应用的Application
 * @author sunjuncai
 *
 */
public class CommonApp extends Application{
	
	private static CommonApp commonApp;

	public static CommonApp getInstance() {
	    return commonApp;
	}
	
	/** 应用无响应 */
	private BaseCrashHandler crashHandler;
	
	/** 存放所有的Activity */
	private List<Activity> activityList = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		commonApp = this;
//		crashHandler = BaseCrashHandler.getInstance();  
//	    crashHandler.init(this);
	}
	
	/**
	 * 添加页面
	 * @param activity
	 */
	public void addActivity(Activity activity){
		if(null == activityList){
			activityList = new LinkedList<Activity>();
		}
		activityList.add(activity);
	}
	
	/**
	 * 退出系统
	 */
	public void exitSys(){
		if(CommonUtil.isNotNullCollect(activityList)){
			for(Activity activity : activityList){
				if(null != activity){
					activity.finish();
				}
			}
		}
	}
	
}
