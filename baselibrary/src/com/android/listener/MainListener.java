package com.android.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.app.CommonApp;
import com.android.base.BaseActivity;
import com.android.base.R;
import com.android.override.CustomDialog;
import com.android.utils.GpsUtil;

/**
 * 主界面监听
 * @author sunjuncai
 *
 */
public class MainListener implements OnClickListener {
	
	/** 对话框 */
	private CustomDialog dialog;
	
	public MainListener() {
		super();
	}
	
	@Override
	public void onClick(View v)  {
		Context _context =  v.getContext();
		int id = v.getId();
		//------------------------------退出系统------------------------------//
		if(id == R.id.dialog_exit_btn_enter){
			dialog.cancel();
			CommonApp.getInstance().exitSys();
		}else if(id == R.id.dialog_exit_btn_cancle){
			dialog.cancel();
		}else if(id == R.id.dialog_network_config_btn){
		//------------------------------设置网络------------------------------//
			dialog.cancel();
			((Activity)_context).startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 30);
		}else if(id == R.id.dialog_network_cancel_btn){
			dialog.cancel();
			((Activity)_context).finish();
		}else if(id == R.id.dialog_gps_btn_outside){
		//------------------------------检查使用哪种方式获取经纬度------------------//
			dialog.cancel();
			//GPS
			if(GpsUtil.isGPSOpen(_context,true)){
				//打开经纬度
			}
		}else if(id == R.id.dialog_gps_btn_inside){
			dialog.cancel();
			//获取基站经纬度
		}else if(id == R.id.dialog_gps_open_btn_enter){
			dialog.cancel();
			GpsUtil.openGpsConfigPage((BaseActivity)_context);
		}else if(id == R.id.dialog_gps_open_btn_cancle){
			dialog.cancel();
		}
	}

	public void setDialog(CustomDialog dialog) {
		this.dialog = dialog;
	}
	
}
