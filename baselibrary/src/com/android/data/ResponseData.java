package com.android.data;

import java.util.ArrayList;

import com.android.enums.ResponseEnum;

/**
 * 客户端数据
 * @author sunjuncai
 * @date 2014-8-20
 * @param <T>
 */
public class ResponseData<T> {
	
	/**
	 *  总条数
	 */
	private int totalCount;
	
	/**
     * 对象数据列表
     */
    private ArrayList<T> dataList = null;
    
	/**
	 * 单个对象数据
	 */
	private T dataObj = null;
    
	/**
	 * 响应时间
	 */
	private String responseTime = null;
	
	/**
     * 是否成功
     */
    private ResponseEnum resultType;

	/**
     *提示信息
     */
    private String message = null;
    
    /**
     * 异常消息
     */
    private String errorMsg = null;

    public T getDataObj() {
		return dataObj;
	}

	public void setDataObj(T dataObj) {
		this.dataObj = dataObj;
	}

	public ArrayList<T> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<T> dataList) {
		this.dataList = dataList;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public ResponseEnum getResultType() {
		return resultType;
	}

	public void setResultType(ResponseEnum resultType) {
		this.resultType = resultType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}