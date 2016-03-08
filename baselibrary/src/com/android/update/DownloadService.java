package com.android.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import com.android.base.R;

public class DownloadService extends IntentService {
	private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K
	private static final String TAG = "DownloadService";
	private NotificationManager mNotifyManager;
	private Builder mBuilder;
	
	private Handler handler;
	private String urlStr;
	private HttpURLConnection urlConnection;
	public DownloadService(){
		super("DownloadService");
		UpdateChecker checker = new UpdateChecker();
		handler = checker.getHandler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);
		ApplicationInfo info;
		try {
			info = getApplicationContext().getPackageManager().getApplicationInfo(getPackageName(), 0);
			String appName=getString(info.labelRes);
			int icon=info.icon;
			mBuilder.setContentTitle(appName).setSmallIcon(icon);
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}
		
		urlStr = intent.getStringExtra(Constants.APK_DOWNLOAD_URL);
		InputStream in=null;
		FileOutputStream out = null;
		try {
			URL url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(false);
			urlConnection.setConnectTimeout(10 * 1000);
			urlConnection.setReadTimeout(3 * 1000);
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Charset", "UTF-8");
			urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			urlConnection.connect();
			long bytetotal = urlConnection.getContentLength();
			
			if (bytetotal<= 0) {
				Message msg = handler.obtainMessage();
				msg.what = UpdateChecker.WHAT_DOWNLOAD_RETRY;
				msg.obj = urlStr;
				msg.sendToTarget();
				return;
			}
			long bytesum = 0;
			int byteread = 0;
			in = urlConnection.getInputStream();
			File dir = StorageUtils.getCacheDirectory(this);
			String apkName=urlStr.substring(urlStr.lastIndexOf("/")+1, urlStr.length());
			File apkFile = new File(dir, apkName);
			out = new FileOutputStream(apkFile);
			byte[] buffer = new byte[BUFFER_SIZE];

			int oldProgress = 0;

			while ((byteread = in.read(buffer)) != -1) {
				bytesum += byteread;
				out.write(buffer, 0, byteread);

				int progress = (int) (bytesum * 100L / bytetotal);
				if (progress != oldProgress) {
					updateProgress(progress);
				}
				oldProgress = progress;
			}
			// 下载完成
			mBuilder.setContentText(getString(R.string.download_success)).setProgress(0, 0, false);
			Intent installAPKIntent = new Intent(Intent.ACTION_VIEW);
			String[] command = {"chmod","777",apkFile.toString()};
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.start();
			installAPKIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
			
			Message msg = handler.obtainMessage();
			HashMap<String, Object> objects = new HashMap<String, Object>();
			objects.put("context", this.getApplicationContext());
			objects.put("intent", installAPKIntent);
			msg.what = UpdateChecker.WHAT_DOWNLOAD_FINISH;
			msg.obj = objects;
			msg.sendToTarget();
			
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, installAPKIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			mBuilder.setContentIntent(pendingIntent);
			Notification noti = mBuilder.build();
			noti.flags = android.app.Notification.FLAG_AUTO_CANCEL;
			mNotifyManager.notify(0, noti);

		} catch (Exception e) {
			Log.e(TAG, "download apk file error", e);
			Message msg = handler.obtainMessage();
			msg.what = UpdateChecker.WHAT_DOWNLOAD_RETRY;
			msg.obj = urlStr;
			msg.sendToTarget();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
				
	}

	private void updateProgress(int progress) {
		//"正在下载:" + progress + "%"
		mBuilder.setContentText(this.getString(R.string.download_progress, progress)).setProgress(100, progress, false);
		PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
		Message msg = handler.obtainMessage();
		Map<String, Object> progressObj = new HashMap<String, Object>();
		msg.what = UpdateChecker.WHAT_DOWNLOAD_UPDATE;
		progressObj.put("progress", progress);
		progressObj.put("context", this.getApplicationContext());
		msg.obj = progressObj;
		msg.sendToTarget();
		mBuilder.setContentIntent(pendingintent);
		mNotifyManager.notify(0, mBuilder.build());
	}
	
}
