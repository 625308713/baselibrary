package com.android.base;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.android.A.Code;
import com.android.A.Key;
import com.android.app.CommonApp;
import com.android.base.BaseChannel.PullView;
import com.android.enums.RequestEnum;
import com.android.http.HttpClient;
import com.android.http.HttpRequest;
import com.android.override.ProgressDialog;
import com.android.utils.BundleUtil;
import com.android.utils.CommonUtil;
import com.android.utils.DialogUtil;
import com.android.utils.NetUtil;
import com.android.utils.ToastUtil;
import com.android.widget.CustomToast;

/**
 * 框架集页面
 * @author sunjuncai
 *
 */
public abstract class BaseTabActivity extends TabActivity implements IActivity,Callback,PullView{
	
	/** Handler 消息 */
	protected Handler _handler;
	
	/** 上下文 */
	protected Context _context;
	
	/** 页面 */
	protected Activity _activity;
	
	/** 应用的Application */
	protected CommonApp _commonApp;
	
	protected CustomToast _customToast;
	
	/** 进度框 */
	protected ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_context = this;
		_activity = this;
		_handler = new Handler(this);
		_commonApp = (CommonApp) getApplication();
		_customToast = CustomToast.getInstance(_context);
		_commonApp.addActivity(_activity);
		//显示标题
		boolean showTitle = isShowTitle();
		//注明：以下三行代码顺序不能调
		if(showTitle){
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		}
		setContentView();
		if(showTitle){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.activity_title);
		}
		prepareView();
		configWinUI();
	}

	@Override
	public boolean handleMessage(Message msg) {
		if(null != msg){
			String result = null;
			int what = msg.what;
			Object obj = msg.obj;
			if(null != obj){
				//返回结果
				result = String.valueOf(msg.obj);
			}
			RequestEnum requestType = RequestEnum.valueOf(what);
			if(null != requestType){
				switch (requestType) {
					case START :
						showLoadMask(result);
						break;
					case PROGRESS :
						
						break;
					case FINISH :
						hideLoadMask();
						int requestCode = BundleUtil.getInt(msg.getData(), Key.REQUEST_CODE);
						if(requestCode != Code.ZERO){
							if(CommonUtil.checkData(_context, result,requestCode)){
								//对请求结果的验证
								responseVerify(requestCode,result);
							}
						}
						break;
				}
			}
		}
		return false;
	}
	
	/**
	 * 显示进度框
	 */
	protected void showLoadMask(String message){
		if(TextUtils.isEmpty(message)){
			message = getString(R.string.request_loading);
		}
		progressDialog = DialogUtil.showLoadMask(_context, message);
	}
	
	/**
	 * 隐藏进度框
	 */
	protected void hideLoadMask() {
		DialogUtil.hideLoadMask(progressDialog);
	}
	
	/**
	 * 显示提示框
	 */
	protected void showToast(int resId) {
		ToastUtil.show(_context, resId);
	}
	
	/**
	 * 显示提示
	 * @param resId
	 */
	protected void showToastCustom(int resId) {
		_customToast.ToastText(_context.getString(resId));
	}
	
	/**
	 *  带图片
	 * @param resId 字符串id
	 * @param imgId toast icon图片资源id
	 */
	protected void showToastCustom(int resId,int imgId) {
		_customToast.ToastImgText(_context.getString(resId), imgId);
	}
	
	/**
	 *  
	 * @param text
	 */
	protected void showToastCustom(String text) {
		_customToast.ToastText(text);
	}
	
	/**
	 * 
	 *  @param text
	 *  @param time  时间长短
	 */
	protected void showToastCustom(String text,int time) {
		_customToast.ToastText(text, time);
	}
	
	
	
	/**
	 * 进入某个页面
	 * @param cls
	 */
	protected void startActivity(Class<?> cls){
		startActivity(new Intent(_context, cls));
	}
	
	/**
	 * 进入某个页面
	 * @param cls
	 */
	protected void startActivity(Class<?> cls,Bundle data){
		Intent intent = new Intent(_context, cls);
		intent.putExtras(data);
		startActivity(intent);
	}
	
	/**
	 * 提交请求
	 * @param isVerify 请求前是否需要验证
	 */
	protected void submit(int requestCode,boolean isVerify){
		//验证根据验证结果,不验证直接请求
		boolean flag = isVerify?requestVerify(requestCode):true;
		if(flag){
			//检查网络,考虑有正在连接的情况或者信号不好，扩展对信号不好的情况进行处理
			if(NetUtil.checkNet(_context, true, true)){
				requestServer(requestCode);
			}
		}
	}
	
	/**
	 * 执行请求
	 * @param request 执行请求
	 */
	protected void execute(HttpRequest request){
		if(null != request){
			//请求客户端
			HttpClient httpClient = new HttpClient(_context,_handler);
			httpClient.execute(request);
		}
	}
	
	
	@Override
	public boolean isShowTitle() {
		return true;
	}

	@Override
	public <T> T returnResult(int type, Object... param) {
		return null;
	}

	@Override
	public String getS(int resId) {
		return getString(resId);
	}

	@Override
	public View find(int resId) {
		return findViewById(resId);
	}

	@Override
	public OnClickListener returnListener() {
		return null;
	}
	
}
