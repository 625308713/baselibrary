package com.android.utils;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import com.android.A.Code;
import com.android.A.Key;

/**
 * Bundle 工具类
 * @author sunjuncai
 */
public class BundleUtil {
	
	/**
	 * 获取Boolean型值
	 * @param bundle
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Intent intent,String key){
		boolean flag = false;
		Bundle bundle = getExtras(intent);
		if(null != bundle){
			flag = bundle.getBoolean(key, false);
		}
		return flag;
	}
	
	/**
	 * 获取Int 值
	 * @param bundle
	 * @param key
	 * @return
	 */
	public static int getInt(Bundle bundle,String key){
		int value = Code.ZERO;
		if(null != bundle){
			value = bundle.getInt(key, Code.ZERO);
		}
		return value;
	}
	
	/**
	 * 获取Int 值
	 * @param bundle
	 * @param key
	 * @return
	 */
	public static int getInt(Intent intent,String key){
		return getInt(getExtras(intent), key);
	}
	
	/**
	 * 获取String 值
	 * @param bundle
	 * @param key
	 * @return
	 */
	public static String getString(Intent intent,String key){
		Bundle bundle = getExtras(intent);
		if(null != bundle){
			return bundle.getString(key);
		}
		return null;
	}
	
	/**
	 * 获取数据
	 * @return
	 */
	public static <T> T getData(Intent intent){
		T data = null;
		Bundle bundle = getExtras(intent);
		if(null != bundle){
			data = (T) bundle.getSerializable(Key.BUNDLE_DATA);
		}
		return data;
	}
	
	/**
	 * 获取List数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> getListData(Intent intent){
		ArrayList<T> dataList = null;
			Bundle bundle = getExtras(intent);
			if(null != bundle){
				dataList = (ArrayList<T>) bundle.getSerializable(Key.BUNDLE_DATA_LIST);
			}
		return dataList;
	}
	
	/**
	 * 从Intent中获取数据
	 * @return
	 */
	public static Bundle getExtras(Intent intent){
		if(null != intent){
			Bundle bundle = intent.getExtras();
			return bundle;
		}
		return null;
	}
}
