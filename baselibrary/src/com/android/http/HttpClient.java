package com.android.http;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.android.A;
import com.android.enums.RequestEnum;
import com.android.http.CustomMultipartEntity.ProgressListener;
import com.android.utils.MobileInfo;

/**
 * Http请求客户端
 * @author sunjuncai
 */
public class HttpClient extends AsyncTask<HttpRequest, Integer, String> {
	
	/** 上下文  */
	private Context context;
	
	/** 请求参数的封装  */
	private HttpRequest request;
	
	/** 回调数据  */
	private Handler handler = null;
	
	/** 是否显示Loading */
	private boolean showLoad = true;
	
	/** Loading 文本 */
	private String loading;
	
	/** 请求单例 */
	private static DefaultHttpClient httpClient = null;
	
	/**
	 * 默认构造
	 * @param context
	 * @param handler
	 */
	public HttpClient(Context context,Handler handler) {
		this.context = context;
		this.handler = handler;
	}
	
	/**
	 * 请求单例
	 * @return
	 */
	synchronized public static DefaultHttpClient getInstance() {
		if (null == httpClient) {
			DefaultHttpClient client = new DefaultHttpClient();  
			ClientConnectionManager manager = client.getConnectionManager();  
			HttpParams httpParams = client.getParams();  
			ConnManagerParams.setTimeout(httpParams, 1000);
			ConnManagerParams.setMaxTotalConnections(httpParams,DEFAULT_MAX_CONNECTIONS);
			ConnManagerParams.setMaxConnectionsPerRoute(httpParams,new ConnPerRouteBean(10));
			HttpClientParams.setRedirecting(httpParams, true);
			HttpProtocolParams.setContentCharset(httpParams, CONTENT_CHARSET);
			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
			HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setUserAgent(httpParams, USER_AGENT);
			HttpConnectionParams.setConnectionTimeout(httpParams,REQUEST_TIMEOUT);
			HttpConnectionParams.setSocketBufferSize(httpParams,DEFAULT_SOCKET_BUFFER_SIZE);
			httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams,manager.getSchemeRegistry()), httpParams);  
		}
		return httpClient;
	}
	
	/**
	 * 请求开始
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(showLoad){
			sendMessage(RequestEnum.START.getId(), loading, null);
		}
	}
	
	/**
	 * 请求执行中
	 */
	@Override
	protected String doInBackground(HttpRequest... params) {
		String result = null;
		if (null != params && params.length > 0) {
			try {
				result = httpPost(params[0]);
			} catch (ConnectTimeoutException e) {
				e.printStackTrace();
				result = A.Exception.CONNECT_TIMEOUT + e.getMessage();
			} catch (HttpHostConnectException e) {
				e.printStackTrace();
				result = A.Exception.HTTP_HOST + e.getMessage();
			} catch (Exception e) {
				e.printStackTrace();
				result = A.Exception.OTHER + e.getMessage();
			}
		}
		return result;
	}
	
	/**
	 * 更新请求进度
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if(null != request && request.isShowProgress()){
			if(null != values[0]){
				sendMessage(RequestEnum.PROGRESS.getId(), String.valueOf(values[0]), null);
			}
		}
	}
	
	/**
	 * 请求完成
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Bundle bundle = new Bundle();
		if(null != request){
			bundle.putInt(A.Key.REQUEST_CODE, request.getCode());
		}
		sendMessage(RequestEnum.FINISH.getId(), result, bundle);
	}
	
	/**
	 * 发送消息
	 */
	private void sendMessage(int what,String result,Bundle bundle){
		Message message = handler.obtainMessage();
		if(null != bundle){
			message.setData(bundle);
		}
		message.what = what;
		message.obj = result;
		message.sendToTarget();
	}

	/**
	 * 提交请求
	 * @return
	 * @throws Exception
	 */
	synchronized private String httpPost(HttpRequest request)throws Exception {
		//请求结果
		String result = null;
		if (null != request) {
			if(!TextUtils.isEmpty(request.getUrl())){
				HttpPost post = new HttpPost(request.getUrl());
				setParameters(request,post);
				HttpEntity entity = post.getEntity();
				if(null != entity){
					request.setTotalSize(entity.getContentLength());
				}
				this.request = request;
				if (null != cookieStore) {
					HttpClient.getInstance().setCookieStore(cookieStore);
				}
				HttpResponse response = HttpClient.getInstance().execute(post);
				//请求
				int stateCode = response.getStatusLine().getStatusCode();
				if(stateCode == HttpStatus.SC_OK) {
					cookieStore = HttpClient.getInstance().getCookieStore();
					HttpEntity httpEntity = response.getEntity();
					if (httpEntity != null) {
						result = EntityUtils.toString(httpEntity);
					}
				}else{
					return A.Exception.HTTP_STATUS + stateCode;
				}
				post.abort();
			}
		}
		return result;
	}
	
	/**
	 * 设置参数
	 * @param post
	 */
	private void setParameters(HttpRequest request,HttpPost post) throws Exception{
		Map<String, String> param = request.getParam();
		if (null == param) {
			param = new HashMap<String, String>();
		}
		if(null != context){
			MobileInfo mobileInfo = new MobileInfo(context);
			param.put(A.Key.PHONE_IMEI, mobileInfo.getIMEI());
			param.put(A.Key.PHONE_IMSI, mobileInfo.getIMSI());
			param.put(A.Key.PHONE_DEVICE_TYPE, DEVICE_ANDROID);
			param.put(A.Key.PHONE_TYPE, mobileInfo.getPhoneType());
			param.put(A.Key.PHONE_SYS_CODE, mobileInfo.getPhoneSdkVer());
			param.put(A.Key.APK_VER_CODE, mobileInfo.getVersionCode());
			param.put(A.Key.APK_VER_NAME, mobileInfo.getVersionName());
		}
		List<AttachFile> fileList = request.getFiles();
		//上传附件
		if((null != fileList && !fileList.isEmpty()) || request.isUploadFile()){
			CustomMultipartEntity entity = new CustomMultipartEntity(progressListener);
			if(!param.isEmpty()){
				for (Entry<String, String> entry : param.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if(null == value){
						value = "";
					}
					entity.addPart(key, new StringBody(value,Charset.forName(HTTP.UTF_8)));
				}
			}
			int i = 0;
			for(AttachFile UploadFile : fileList){
				String path = UploadFile.getPath();
				String type = UploadFile.getType();
				if(type == null){
					type = "";
				}
				if(!TextUtils.isEmpty(path)){
					File file = new File(path);
					if(null != file && file.exists()){
						i++;
						entity.addPart("file_"+i, new FileBody(file));
						entity.addPart("type_"+i, new StringBody(type,Charset.forName(HTTP.UTF_8)));
					}
				}
			}
			post.setEntity(entity);
		}else{
			//普通form提交
			if(!param.isEmpty()){
				List<NameValuePair> params = new ArrayList<NameValuePair>();  
				for(Entry<String, String> entry : param.entrySet()){
					String key = entry.getKey();
					String value = entry.getValue();
					if(null == value){
						value = "";
					}
					params.add(new BasicNameValuePair(key, value));
				}
				//设置HTTP POST请求参数  
				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
			}
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}
	
	/**
	 * 更新上传进度
	 */
	private ProgressListener progressListener = new  ProgressListener() {
		@Override
		public void transferred(long num) {
			if(null != request && request.isShowProgress()){
				publishProgress((int) ((num / (float) request.getTotalSize()) * 100));
			}
		}
	};
	
	public void setLoading(String loading) {
		this.loading = loading;
	}

	public void setShowLoad(boolean showLoad) {
		this.showLoad = showLoad;
	}

	/** 会话保持 */
	private static CookieStore cookieStore;
	
	/** 设置等待数据超时时间10秒钟  */
	private static final int SO_TIMEOUT = 60 * 1000;
	
	/** 安卓手机 */
	private static final String DEVICE_ANDROID = "1";
	
	/** 设置请求超时10秒钟 */
	private static final int REQUEST_TIMEOUT = 20 * 1000;
	
	/** 最大连接数 */
	private static final int DEFAULT_MAX_CONNECTIONS = 30;

	/** 内容编码 */
	private static final String CONTENT_CHARSET  = "UTF-8";

	/** 授权标识 */
	private static final String USER_AGENT = "Android client";
	
	/** 缓存区大小 */
	private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
	
	
	
}