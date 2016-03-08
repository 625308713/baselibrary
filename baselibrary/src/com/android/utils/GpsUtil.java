package com.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import com.android.override.CustomDialog;

/**
 * GPS 工具类
 * @author sunjuncai
 *
 */
public class GpsUtil {

	/** 
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的 
     * @param context 
     * @return true 表示开启 
     */  
    public static final boolean isGPSOpen(final Context context,boolean isOpenDialog) {  
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）  
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);  
        if (gps) {  
            return true;  
        }else{
        	if(isOpenDialog){
        		//弹出对话框
    			CustomDialog.Builder builder = new CustomDialog.Builder(context);
    			builder.createGpsOpenDialog().show();
        	}
        }
        return false;  
    }
    
    /**
     * 打开GPS配置页面
     */
    public static void openGpsConfigPage(Activity activity){
    	//转到手机设置界面，用户设置GPS
		Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		//设置完成后返回到原来的界面
		activity.startActivityForResult(intent,0); 
    }
    
}
