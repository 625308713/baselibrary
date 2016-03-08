package com.android.utils;

import android.content.Context;

import com.android.widget.CustomToast;

/**
 * Toast工具
 * @author sunjuncai
 *
 */
public class ToastUtil {
	
	/**
	 * 显示提示信息
	 * @param text
	 */
	public static void show(Context context,String info){
		getCustomToast(context).ToastText(info);
	}
	
	public static CustomToast getCustomToast(Context context){
		return CustomToast.getInstance(context);
	}
	
	/**
	 * 显示提示信息
	 * @param text
	 */
	public static void show(Context context,int resId){
		show(context, context.getString(resId));
	}
	
	/**
	 * 显示提示信息
	 * @param text
	 */
	public static void show(Context context,int resId,String messge){
		show(context, context.getString(resId) + messge);
	}
	
	/**
	 *  显示提示信息
	 * @param context 上下文
	 * @param text	  文字信息
	 */
	public static void showCustom(Context context,String text){
		getCustomToast(context).ToastText(text);
		
	}
	
	/**
	 *  显示提示信息
	 * @param context 上下文信息
	 * @param resId	文字资源信息
	 */
	public static void showCustom(Context context ,int resId){
		getCustomToast(context).ToastText(context.getString(resId));
		
	}
	
	/**
	 *  显示带图标提示信息
	 * @param context	上下文信息
	 * @param resId		字符串资源信息
	 * @param imgRes	图片资源信息
	 */
	public static void showCustom(Context context ,int resId,int imgRes){
		getCustomToast(context).ToastImgText(context.getString(resId), imgRes);
	}
	
	/**
	 *  显示带提示信息+message
	 * @param context 上下文
	 * @param resId	 资源信息
	 * @param messge 消息文本
	 */
	public static void showCustom(Context context ,int resId,String messge){
		getCustomToast(context).ToastText(context.getString(resId) + messge);
	}
}
