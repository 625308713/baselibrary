package com.android.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 移动设备信息信息
 * @author sunjuncai
 */
public class MobileInfo {
	
	/** 上下文环境 */
	private Context context;
	
	public MobileInfo(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 获取手机的mac地址
	 */
	public String getLocalMacAddress() {
		if(null != context){
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			if(null != wifi){
				WifiInfo info = wifi.getConnectionInfo();
				String mac = info.getMacAddress();
				if(!TextUtils.isEmpty(mac)){
					return mac;
				}
			}
		}
        return null;   
    } 
	
	/**
	 * 获取手机设备的IMEI
	 * @return
	 */
	public String getIMEI(){
		if(null != context){
			TelephonyManager tm =(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (null != tm) {
				String deviceId = tm.getDeviceId();
				if (!TextUtils.isEmpty(deviceId)) {
					return deviceId;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取手机设备的IMSI
	 * @return
	 */
	public String getIMSI(){
		if(null != context){
			TelephonyManager tm =(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (null != tm) {
				String imsiStr = tm.getSubscriberId();
				if (!TextUtils.isEmpty(imsiStr)) {
					return imsiStr;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取系统的版本号
	 * @param context
	 * @return
	 */
	public int getVerCode() {
		int verCode = -1;
		if(null != context){
			try {
				verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
			} catch (NameNotFoundException e) {
				verCode = -1;
			}
		}
		return verCode;
	}
	
	/**
	 * 得到当前apk版本号
	 */
	public String getVersionCode(){
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		String code = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			if(null != packInfo){
				code = String.valueOf(packInfo.versionCode);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "0";
		}
		return code;
	}
	
	/**
	 * 得到当前apk版本名
	 */
	public String getVersionName(){
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		String code = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			if(null != packInfo){
				code = String.valueOf(packInfo.versionName);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return code;
	}
	
	/**
	 * 获得移动网络号
	 * @return
	 */
	public static String getMNC(Context context){
		if(null != context){
			try {
				TelephonyManager tm =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
				if (null != tm) {
					String subId = tm.getSubscriberId();
					if (!TextUtils.isEmpty(subId)) {
						return subId.substring(3, 5);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		return null;
	}
	
	/**
	 * 获取移动国家号码
	 * @return
	 */
	public static String getMCC(Context context){
		if(null != context){
			TelephonyManager tm =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			if (null != tm) {
				String subId = tm.getSubscriberId();
				if (!TextUtils.isEmpty(subId)) {
					return subId.substring(0, 3);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取运营商的名称
	 */
	public static String getIspName(String mnc) {
		String ispName = null;
		if (null != mnc) {
			if (mnc.equals("00") || mnc.equals("02")|| mnc.equals("07")) {
				ispName = "移动";
			} else if (mnc.equals("01") || mnc.equals("26")) {
				ispName = "联通";
			} else if (mnc.equals("03")) {
				ispName = "电信";
			} else {
				ispName = mnc+"";
			}
		}else {
			ispName= "请插入SIM卡";
		}
		return ispName;
	}
	
	// 判断当前是否使用的是 WIFI网络  
    public static boolean isWifiActive(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info;
        if (connectivity != null) {    
            info = connectivity.getAllNetworkInfo();    
            if (info != null) {    
                for (int i = 0; i < info.length; i++) {    
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {    
                        return true;    
                    }    
                }    
            }    
        }    
        return false;   
    }
	
	/**
	 * 获取手机型号
	 * @return
	 */
	public String getPhoneType(){
		return android.os.Build.MODEL;
	}
	
	/**
	 * 获取手机SDK的版本号
	 * @return
	 */
	public String getPhoneSdkVer(){
		return android.os.Build.VERSION.RELEASE;
	}
	
	// 获取CPU名字
	public String getCpuName() {
		try {
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
