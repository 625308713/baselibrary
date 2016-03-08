package com.android.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import com.android.A.Exception;
import com.android.A.Value;
import com.android.base.BuildConfig;
import com.android.base.R;
import com.android.data.ResponseData;
import com.android.enums.RequestEnum;
import com.android.enums.ResponseEnum;


/**
 * 公共工具类
 * @author sunjuncai
 *
 */
public class CommonUtil {
	
	/** 字符串资源 */
	public static final String R_STRING = "string";
	
	/** 数组资源 */
	public static final String R_ARRAY = "array";
	
	/**
	 * 是否为空Map
	 * @param map
	 * @return
	 */
	public static boolean isNotNullMap(Map<?, ?> map){
		if(null != map && !map.isEmpty()){
			return true;
		}
		return false;
		
	}
	
	/**
	 * 判断集合是否为null
	 * @param list
	 * @return
	 */
	public static boolean isNotNullCollect(Collection<?> collection){
		if(null != collection && !collection.isEmpty() && collection.size() >0){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取单个对象
	 * @param dataList
	 * @param i
	 * @return
	 */
	public static <T> T getData(ArrayList<T> dataList,int i){
		if(i >= 0){
			if(CommonUtil.isNotNullCollect(dataList)){
				return dataList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 检查数据
	 * @param result 返回结果
	 * @return true 正常  false 不正常
	 */
	public static boolean checkData(Context _context,String result,int requestCode){
		if(TextUtils.isEmpty(result)){
			if(BuildConfig.DEBUG){
				ToastUtil.showCustom(_context, R.string.request_empty, Exception.ERROR_CODE + RequestEnum.EMPTY.getId()+Value._+requestCode);
			}else{
				ToastUtil.showCustom(_context, R.string.request_empty);
			}
			return false;
		}else if(result.startsWith(Exception.HTTP_STATUS)){
			if(BuildConfig.DEBUG){
				ToastUtil.showCustom(_context, result + Exception.ERROR_CODE + RequestEnum.HTTP_STATUS.getId()+Value._+requestCode);
			}else{
				ToastUtil.showCustom(_context, result);
			}
			return false;
		}else if(result.startsWith(Exception.CONNECT_TIMEOUT)){
			if(BuildConfig.DEBUG){
				ToastUtil.showCustom(_context, R.string.request_timeout,Exception.ERROR_CODE + RequestEnum.TIME_OUT.getId()+Value._+requestCode);
			}else{
				ToastUtil.showCustom(_context, R.string.request_timeout);
			}
			return false;
		}else if(result.startsWith(Exception.HTTP_HOST)){
			if(BuildConfig.DEBUG){
				ToastUtil.showCustom(_context, R.string.request_http_host,Exception.ERROR_CODE + RequestEnum.HTTP_HOST.getId()+Value._+requestCode);
			}else{
				ToastUtil.showCustom(_context, R.string.request_http_host);
			}
			return false;
		}else if(result.startsWith(Exception.OTHER)){
			if(BuildConfig.DEBUG){
				ToastUtil.showCustom(_context, R.string.request_error,Exception.ERROR_CODE + RequestEnum.ERROR.getId()+Value._+requestCode);
			}else{
				ToastUtil.showCustom(_context, R.string.request_error);
			}
			return false;
		}
		return true;
	}
	
	/**
	 * 检查返回结果是否成功
	 * @param responseData
	 * @return
	 */
	public static <T> boolean checkData(Context _context,ResponseData<T> responseData,int requestCode){
		if(null != responseData){
			ResponseEnum resultType = responseData.getResultType();
			if(null != resultType){
				//成功提示信息
				String message = responseData.getMessage();
				//请求成功
				if(resultType == ResponseEnum.SUCCESS){
					if(!TextUtils.isEmpty(message)){
						ToastUtil.showCustom(_context, message);
					}
					return true;
				}else{
					//DEBUG模式
					if(BuildConfig.DEBUG){
						//错误提示信息
						String errorMsg = TextUtils.isEmpty(message)?""+responseData.getErrorMsg():message + Exception.ERROR_CODE;
						ToastUtil.showCustom(_context, errorMsg + resultType.getId()+Value._+requestCode);
					}else{
						if(!TextUtils.isEmpty(message)){
							ToastUtil.showCustom(_context, message);
						}
					}
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * 是否为空及空字符串
	 * @param str
	 * @return
	 */
	public static boolean isNullStr(String str){
		if(null == str || "null".equals(str) || "".equals(str)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取当前时间
	 * @param formatStr
	 * @return
	 */
	public static String getNowTime(String formatStr) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		return formatter.format(currentTime);
	}
	
	/**
	 * 获取共享配置文件
	 */
	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
	};
	
	/**
	 * 获取Boolean值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static Boolean getBooleanShare(Context context,String key, boolean defValue){
		return getSharedPreferences(context).getBoolean(key, defValue);
	}
	
	/**
	 * 获取Int值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getIntShare(Context context,String key, int defValue){
		return getSharedPreferences(context).getInt(key, defValue);
	}
	
	/**
	 * 获取String值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getStringShare(Context context,String key,String defValue){
		return getSharedPreferences(context).getString(key, defValue);
	}
	
	/**
	 * 验证是否只包含数字
	 */
	public static boolean isNotNum(String num){
		try {
			double d = Double.parseDouble(num);
			if(d < 0){
				return true;
			}
			return false;
		} catch (java.lang.Exception e) {
			return true;
		}
	}
	
	/**
	 * 验证只包含六位小数的浮点型
	 */
	public static boolean isFloatWihtSix(String num){
		try {
			Double.parseDouble(num);
			String[] s = num.split("\\.");
			int jw = Integer.parseInt(s[0]);
			if(s[1].length() >=6 && jw > 0 && jw < 180){
				return true;
			}else{
				return false;
			}
		} catch (java.lang.Exception e) {
			return false;
		}
	}
	
	/**
	 * 获取版本信息
	 * @param _context
	 * @return
	 */
	public static String getVersion(Context _context){
		String year = CommonUtil.getNowTime("yyyy");
		return String.format(_context.getString(R.string.copy_right_format), year);
	}
	
	/**
	 * 格式化经纬度值
	 * @param value
	 * @return
	 */
	public static String formatJWValue(String value){
		double jwValue = 0;
		try {
			BigDecimal bg = new BigDecimal(value);  
			jwValue = bg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();  
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			jwValue = 0;
		}
		return String.valueOf(jwValue);	
	}
	
	/**
	 * 检查经纬度值是否正确
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public static boolean checkJWNum(Double longitude,Double latitude){
		if((longitude !=null && latitude !=null) && (longitude != 4.9E-324 && latitude != 4.9E-324) && (longitude !=0.0 && latitude !=0.0)){
			//值正常
			return true;
		}
		longitude = null;
		latitude = null;
		return false;
	}
	
	/**
	 * 获取小区信息
	 * @return
	 */
	public static Map<String,String> getCellInfo(Context context){
		Map<String,String> cellInfo = new HashMap<String, String>();
		//手机的信息
		try{
			TelephonyManager telManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
			if (null != telManager) {
				GsmCellLocation cellLocation = (GsmCellLocation) telManager.getCellLocation();
				//小区位置信息
				if(null != cellLocation){
					int cid = cellLocation.getCid();
					if(cid > 99999){
						cid = cid & 0xffff;
					}
					cellInfo.put("cellId", String.valueOf(cid));
					cellInfo.put("lac", String.valueOf(cellLocation.getLac()));
				}else{
					cellInfo.put("cellId", "-1");
					cellInfo.put("lac", "-1");
				}
			}
		}catch(java.lang.Exception e){
			cellInfo.put("cellId", "-1");
			cellInfo.put("lac", "-1");
		}
		
		return cellInfo;
	}
	
	/**
	 * 获取级联列表
	 * @param length 
	 * @param key
	 * @param context
	 * @return
	 */
	public static List<String> getResourcesValue(int length,String name,Context context){
		String type = null;
		int resId = 0;
		Resources res = context.getResources();
		List<String> valueList = new ArrayList<String>();
		if(length == 1){
			type = R_STRING;
			try {
				Field field = R.string.class.getField(name);
				resId = field.getInt(new R.string());
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}else{
			type = R_ARRAY;
			try {
				Field field = R.array.class.getField(name);
				resId = field.getInt(new R.array());
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}
		if(resId > 0){
			if(type.equals(R_STRING)){
				String value = res.getString(resId);
				valueList.add(value);
			}else{
				String[] valueArray = res.getStringArray(resId);
				valueList = Arrays.asList(valueArray);
			}
		}
		return valueList;
	}
	
	/**
	 * 初始化目录
	 * @return
	 */
	public static boolean initDir(String sdCardDir,String appFolderName) {
		if (sdCardDir == null) {
			return false;
		}
		File f = new File(sdCardDir, appFolderName);
		if (!f.exists()) {
			try {
				f.mkdir();
			} catch (java.lang.Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取SD卡目录
	 * @return
	 */
	public static String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
	
}
