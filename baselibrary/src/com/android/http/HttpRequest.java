package com.android.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.A.Code;

/**
 * 请求参数
 * @author sunjuncai
 */
public class HttpRequest {
	
	/** 服务端URL */
	private String url;
	
	/** 请求标识 */
	private int code = Code.ZERO;
	
	/** 请求总长度 */
	private long totalSize;
	
	/** 附加参数 */
	private Map<String, String> param;
	
	/** 是否上传文件 */
	private boolean uploadFile = false;
	
	/** 是否显示进度  */
	private boolean showProgress = false;
	
	/** 上传的文件对象 */
	private List<AttachFile> files = new ArrayList<AttachFile>();

	public HttpRequest() {
		super();
	}

	public HttpRequest(String url, Map<String, String> param, List<AttachFile> files) {
		super();
		this.url = url;
		this.param = param;
		this.files = files;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	public List<AttachFile> getFiles() {
		return files;
	}

	public void setFiles(List<AttachFile> files) {
		this.files = files;
	}

	public boolean isUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(boolean uploadFile) {
		this.uploadFile = uploadFile;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public boolean isShowProgress() {
		return showProgress;
	}

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}
	
}