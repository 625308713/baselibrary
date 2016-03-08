package com.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

import com.android.base.R;
import com.android.enums.NetEnum;

/**
 * 网络工具
 * @author sunjuncai
 */
public class NetUtil {
	
	/**
	 * 检查网络状态
	 * @return
	 */
	public static boolean checkNet(Context context,boolean shwoInfo,boolean config) {
		NetEnum netEnum = checkNetEnum(context);
		switch (netEnum) {
			case CONNECTED:
				return true;
			case CONNECTING:
				return false;
		}
		setConfig(context, shwoInfo, config);
		return false;
    }
	
	/**
	 * 检查网络状态
	 * @return
	 */
	private static NetEnum checkNetEnum(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);        
		//mobile        
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		//wifi
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if(mobile == State.CONNECTED || wifi == State.CONNECTED) {
			return NetEnum.CONNECTED;        
		}
		if(mobile == State.CONNECTING || wifi == State.CONNECTED) {
			return NetEnum.CONNECTING;        
		}
        return NetEnum.NO;
    }
	
	/**
	 * 显示网络配置对话框
	 * @return
	 */
	private static void setConfig(Context context,boolean shwoInfo,boolean config) {
		if(shwoInfo){
			ToastUtil.show(context, R.string.network_error);
		}
		if(config){
			DialogUtil.configNetWork(context);
		}
    }
	
}
