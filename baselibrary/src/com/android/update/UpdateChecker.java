package com.android.update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

import com.android.base.R;
import com.android.override.CustomDialog;
import com.android.utils.CommonUtil;
import com.android.widget.CustomToast;
import com.android.widget.sweetAlert.SweetAlertDialog;
import com.android.widget.sweetAlert.SweetAlertDialog.OnSweetClickListener;

/**
 * 更新核心类
 * @author yaguang.wang
 */
public class UpdateChecker {
	
	public UpdateChecker() {
		
	}
	
	public Handler mDownloadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case WHAT_DOWNLOAD_UPDATE:
					@SuppressWarnings("unchecked")
					HashMap<String, Object> updateObjects = (HashMap<String, Object>) msg.obj;
					Context updateContext = (Context)updateObjects.get("context");
					int progress = (Integer) updateObjects.get("progress");
					if (null == cplg) {
						dlg = new CustomDialog.Builder(mContext);
						cplg = dlg.createProgressDialog();
						cplg.setCancelable(false);
						cplg.show();
					} else {
						if (null!= updateContext) {
							TextView tvProgress = (TextView) cplg.findViewById(R.id.loading_msg_tv);
							tvProgress.setText(String.format(updateContext.getString(R.string.download_progress), progress));
						}
					}
					break;
				case WHAT_DOWNLOAD_FINISH:
					@SuppressWarnings("unchecked")
					Map<String, Object> finishObject = (Map<String, Object>) msg.obj;
					Context context = (Context)finishObject.get("context");
					Intent installIntent = (Intent) finishObject.get("intent");
					if (null != cplg) {
						cplg.dismiss();
						if (null!= installIntent && null!= context) {
								mContext.startActivity(installIntent);
						}
					}
					break;
				case WHAT_DOWNLOAD_RETRY:
					showRetryDlg(msg.obj.toString());
					break;
			}
		};
	};
	/**
	 * 根据传入的json串进行更新
	 * 
	 * @param fragmentActivity
	 * @param json
	 */
	public void checkForJson(Context context, String json,Class<?> activity) {
		mContext = context;
		jsonString = json;
		jumpToActivity = activity;
		toast = CustomToast.getInstance(mContext);
		parseJson(jsonString);
	}

	public Handler getHandler() {
		if (null != mDownloadHandler) {
			return mDownloadHandler;
		} else
			return null;
	}

	/**
	 * 
	 * 从URL返回json串信息中更新
	 * @param fragmentActivity
	 * 
	 */
	public void checkForUrl(Activity context, String url,Class<?> activity) {
		mContext = context;
		urlString = url;
		jumpToActivity = activity;
		toast = CustomToast.getInstance(mContext);
		checkForUpdates(urlString);
	}

	/**
	 * Heart of the library. Check if an update is available for download
	 * parsing the desktop Play Store page of the app
	 */
	private void checkForUpdates(final String url) {
		mThread = new Thread() {
			@Override
			public void run() {
				if (isNetworkAvailable(mContext)) {
					String json = sendPost(url);
					if (json != null) {
						parseJson(json);
					}
				} else {
					toast.ToastText(mContext.getString(R.string.dialog_network_tv_info));
				}
			}
		};
		mThread.start();
	}

	/**
	 * 请求json
	 * 
	 * @param urlStr
	 * @return
	 */
	protected String sendPost(String urlStr) {
		HttpURLConnection uRLConnection = null;
		InputStream is = null;
		BufferedReader buffer = null;
		String result = null;
		try {
			URL url = new URL(urlStr);
			uRLConnection = (HttpURLConnection) url.openConnection();
			uRLConnection.setDoInput(true);
			uRLConnection.setDoOutput(true);
			uRLConnection.setRequestMethod("POST");
			uRLConnection.setUseCaches(false);
			uRLConnection.setConnectTimeout(10 * 1000);
			uRLConnection.setReadTimeout(10 * 1000);
			uRLConnection.setInstanceFollowRedirects(false);
			uRLConnection.setRequestProperty("Connection", "Keep-Alive");
			uRLConnection.setRequestProperty("Charset", "UTF-8");
			uRLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			uRLConnection.setRequestProperty("Content-Type", "application/json");
			uRLConnection.connect();
			is = uRLConnection.getInputStream();

			String content_encode = uRLConnection.getContentEncoding();

			if (null != content_encode && !"".equals(content_encode) && content_encode.equals("gzip")) {
				is = new GZIPInputStream(is);
			}

			buffer = new BufferedReader(new InputStreamReader(is));
			StringBuilder strBuilder = new StringBuilder();
			String line;
			while ((line = buffer.readLine()) != null) {
				strBuilder.append(line);
			}
			result = strBuilder.toString();
		} catch (Exception e) {
			Log.e(TAG, "http post error", e);
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (uRLConnection != null) {
				uRLConnection.disconnect();
			}
		}
		return result;
	}

	
	private void parseJson(String json) {
		try {

			JSONObject obj = new JSONObject(json);
			String updateMessage = obj.getString(Constants.APK_UPDATE_CONTENT);
			apkUrl = obj.getString(Constants.APK_DOWNLOAD_URL);
			isNew = obj.getString(Constants.APK_IS_NEW);
			isForceUpdate = obj.getString(Constants.APK_IS_UPDATE);

			if (!FLAG_TRUE.equals(isNew) && !CommonUtil.isNullStr(apkUrl)) { // 不是最新
				if (FLAG_TRUE.equals(isForceUpdate)) { // 是否强制更新
					isReadyForceUpdate = true;
				} else {
					isReadyForceUpdate = false;
				}
			} 
			showDialog(updateMessage, apkUrl,isReadyForceUpdate,jumpToActivity);
		} catch (JSONException e) {
			Log.e(TAG, "parse json error", e);
		}
	}

	/**
	 * Show dialog
	 * 
	 */
	public void showDialog(String content, final String apkUrl,boolean isFource,final Class<?> activity) {
		cdlg = new SweetAlertDialog(mContext);
		cdlg.setCancelable(false);
		cdlg.setConfirmText("确认");
		cdlg.setTitleText("更新提示");
		cdlg.setCanceledOnTouchOutside(true);
		cdlg.showCancelButton(true);
		cdlg.setContentText(content);
		if (isFource) {
			cdlg.setCancelText("退出");
		}else {
			cdlg.setCancelText("取消");
		}
		
		cdlg.setCancelClickListener(new OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				if ("退出".equals(sweetAlertDialog.getCancelText())) {
						((Activity) mContext).finish();
					}else {
						mContext.startActivity(new Intent(mContext,jumpToActivity));
						cdlg.dismiss();
						((Activity) mContext).finish();
				}
			}
		});
		
		cdlg.setConfirmClickListener(new OnSweetClickListener() {
			
			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				goToDownload(apkUrl);
				cdlg.dismiss();
			}
		});
		cdlg.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				mContext.startActivity(new Intent(mContext,jumpToActivity));
				cdlg.dismiss();
				((Activity) mContext).finish();
			}
		});
		
		cdlg.show();
	}

	/**
	 * Show Notification
	 * 
	 */
	public void showNotification(String content, String apkUrl) {
		android.app.Notification noti;
		Intent myIntent = new Intent(mContext, DownloadService.class);
		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
		PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		PackageManager pm = mContext.getPackageManager();
		int smallIcon;
		try {
			smallIcon = pm.getPackageInfo(mContext.getPackageName(), 0).applicationInfo.icon;
			
			noti = new NotificationCompat.Builder(mContext).
					setTicker(mContext.getString(R.string.newUpdateAvailable))
					.setContentTitle(mContext.getString(R.string.newUpdateAvailable))
					.setContentText(content)
					.setSmallIcon(smallIcon)
					.setContentIntent(pendingIntent).build();
			noti.flags = android.app.Notification.FLAG_NO_CLEAR;
			NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(0, noti);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 网络错误重试
	public void showRetryDlg(final String uslString) {
		celg = new SweetAlertDialog(mContext,SweetAlertDialog.WARNING_TYPE);
		celg.setCancelable(false);
		celg.showCancelButton(true);
		celg.setConfirmText("重试");
		celg.setCancelText("退出");
		celg.setContentText("下载错误，请重试");
		celg.setCancelClickListener(new OnSweetClickListener() {
			
			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				celg.dismiss();
				((Activity) mContext).finish();
			}
		});
		celg.setConfirmClickListener(new OnSweetClickListener() {
			
			@Override
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				celg.dismiss();
				goToDownload(uslString);
			}
		});
		celg.show();

	}

	/**
	 * Check if a network available
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean connected = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni != null) {
				connected = ni.isConnected();
			}
		}
		return connected;
	}

	/**
	 * service下载
	 */
	private void goToDownload(String url) {
		Intent intent = new Intent(mContext, DownloadService.class);
		intent.putExtra(Constants.APK_DOWNLOAD_URL, url);
		mContext.startService(intent);
	}

	private String jsonString;
	
	private String urlString;
	
	private String apkUrl;
	
	private String isNew;
	
	private String isForceUpdate;
	
	private Class<?> jumpToActivity;
	/** --------------------------- 组件------------------------------ */

	/** 从url获取json线程 */
	private Thread mThread;
	/** 上线文 */
	private static Context mContext;

	/** 进度dlg */
	private static CustomDialog cplg;

	/** dlg工厂 */
	private static CustomDialog.Builder dlg;
	
	/** 下载确认dlg */
	private SweetAlertDialog cdlg,celg;
	
	private CustomToast toast;
	
	/** -------------------------------- 常量 ------------------------------ */
	private static final String TAG = "UpdateChecker";

	/** msg.what更新 */
	public static final int WHAT_DOWNLOAD_UPDATE = 100;
	/** msg.what完成 */
	public static final int WHAT_DOWNLOAD_FINISH = 101;
	/** msg.what重试 */
	public static final int WHAT_DOWNLOAD_RETRY = 102;
	/** 条件标志 */
	public static final String FLAG_TRUE = "1";
	
	private boolean isReadyForceUpdate = false;
	
}
