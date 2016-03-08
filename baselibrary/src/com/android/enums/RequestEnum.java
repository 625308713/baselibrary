package com.android.enums;

/**
 * 请求枚举
 * @author sunjuncai
 *
 */
public enum RequestEnum {
	
	//添加请求枚举，必须添加到valueOf
	
	/** 请求开始 */
	START(2001), 
	
	/** 请求中  */
	PROGRESS(2005),
	
	/** 请求超时  */
	TIME_OUT(2002),
	
	/** 连接服务器失败  */
	HTTP_HOST(2003),
	
	/** 失败  */
	ERROR(2004),
	
	/** 请求完成  */
	FINISH(2006),
	
	/** 请求结果为空  */
	EMPTY(2007),
	
	/** 请求状态 */
	HTTP_STATUS(2008);
	
	private int _id;
	
	RequestEnum(int id) {
		_id = id;
	}

	public int getId() {
		return _id;
	}
	
	/**
	 * 从int到enum的转换函数
	 * @param value
	 * @return
	 */
	public static RequestEnum valueOf(int value) {
        switch (value) {
	        case 2001:
	            return START;
	        case 2002:
	            return TIME_OUT;
	        case 2003:
	        	return HTTP_HOST;
	        case 2004:
	        	return ERROR;
	        case 2005:
	        	return PROGRESS;
	        case 2006:
	        	return FINISH;
	        case 2007:
	        	return EMPTY;
	        case 2008:
	        	return HTTP_STATUS;
        }
        return null;
    }

}
