package com.android.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.ContextThemeWrapper;

import com.android.base.R;
import com.android.override.CustomDialog;
import com.android.override.ProgressDialog;
import com.android.widget.sweetAlert.SweetAlertDialog;
import com.android.widget.sweetAlert.SweetAlertDialog.OnSweetClickListener;

/**
 * 对话框工具
 * @author sunjuncai
 *
 */
public class DialogUtil {

	/**
	 * 显示Loading
	 * @param title	标题 
	 * @param message 消息
	 */
	public static ProgressDialog showLoadMask(Context context,String message){
		ProgressDialog	progressDialog = null;
		try {
			progressDialog = ProgressDialog.createDialog(context);
			progressDialog.setMessage(message);
			if(null != progressDialog){
				progressDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return progressDialog;
	}
	
	/**
	 * 隐藏进度条
	 */
	public static void hideLoadMask(ProgressDialog	progressDialog) {
		try {
			if (null != progressDialog) {
				progressDialog.cancel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 进行网络配置
	 */
	public static void configNetWork(Context context){
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		final CustomDialog dialog = builder.createNetSelectDialog();
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	
	/**
	 * 显示退出对话框
	 */
	public static void showExitDialog(Context context){
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		CustomDialog dialog = builder.createExitDialog();
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	
	/**
	 * 显示弹出框
	 * @param context
	 * @param title
	 * @param items
	 * @param position
	 * @param clickListener
	 */
	public static void showAlertDialog(Context context,String title,String[] items,int position,OnClickListener clickListener){
		new AlertDialog.Builder(
			new ContextThemeWrapper(context, android.R.style.Theme_DeviceDefault_Light_Dialog)
		)
		.setTitle(title)  
		.setSingleChoiceItems(items, position,clickListener)
		.show(); 
	}
	
	/**
	 * 显示Sweet弹出框
	 * @param context
	 * @param title
	 * @param content
	 * @param confirmClickListener
	 */
	public static void showSweetAlertDialog(Context context,String title,String content, OnSweetClickListener confirmClickListener){
		new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
		.setTitleText(title)
		.setContentText(content)
		.showCancelButton(true)
		.setConfirmText(context.getString(R.string.dialog_ok))
		.setCancelText(context.getString(R.string.dialog_cancel))
		.setConfirmClickListener(confirmClickListener)
		.setCancelClickListener(new OnSweetClickListener() {
	        @Override
	        public void onClick(SweetAlertDialog sDialog) {
	            sDialog.dismiss();
	        }
	    }).show();
	}
	
}
