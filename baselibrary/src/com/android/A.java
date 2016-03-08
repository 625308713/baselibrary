package com.android;


/**
 * 常量
 * 
 * @author sunjuncai
 */
public final class A {
	
	/**
	 * 异常信息
	 */
	public static final class Exception {
		
		/** 连接超时 */
		public static final String CONNECT_TIMEOUT = "ConnectTimeoutException:";
		
		/** 连接服务器失败 */
		public static final String HTTP_HOST = "HttpHostConnectException:";
		
		/** 其他异常 */
		public static final String OTHER = "Exception:";
		
		/** 错误码 */
		public static final String ERROR_CODE ="错误码:";
		
		/** 请求状态 */
		public static final String HTTP_STATUS="请求出错,状态码:";
	}

	/**
	 * 存放键
	 */
	public static final class Key {
		
		/** 请求码 */
		public static final String REQUEST_CODE = "request_code";
		
		/** 传递列表数据 */
		public static final String BUNDLE_DATA_LIST = "bundle_data_list";
		
		/** 标识不同类型  */
		public static final String BUNDLE_TYPE = "bundle_type";
		
		/** 标识不同标题  */
		public static final String BUNDLE_TITLE = "bundle_title";

		/** 传递对象 */
		public static final String BUNDLE_DATA = "bundle_data";
		
		/** 传递布尔型  */
		public static final String BUNDLE_BOOL = "bundle_bool";
		
		/** 资源Id */
		public static final String BUNDLE_RES_ID = "bundle_res_id";
		
		/** IMEI */
		public static final String PHONE_IMEI = "v_imei";
		
		/** IMSI */
		public static final String PHONE_IMSI = "v_imsi";
		
		/** 手机型号 */
		public static final String PHONE_TYPE = "v_phone_type";
		
		/** 手机系统版本号 */
		public static final String PHONE_SYS_CODE = "v_syscode";
		
		/** 手机设备类型 */
		public static final String PHONE_DEVICE_TYPE = "v_device_type";
		
		/** APK版本号 */
		public static final String APK_VER_CODE = "v_verscode";
		
		/** APK版本名字 */
		public static final String APK_VER_NAME = "v_versname";
		
		/** List的位置 */
		public static final String LIST_POSITION = "list_position";
		
	}
	
	/**
	 * 请求码
	 */
	public static final class Code{
		
		/** 请求码 0 */
		public static final int ZERO = 0;
		
	}
	
	/**
	 * 默认值
	 */
	public static final class Value {
		
		/** 空串 */
		public static final String EMPTY = "";
		
		/** 数据为空 */
		public static final String EMPTY_DATA = "无";
		
		/** 连接符 */
		public static final String _ = "x";
		
		/** 修改 */
		public static final int MODIFY = 1;

		/** 新增 */
		public static final int NEW_ADD = 2;
		
		/** 删除 */
		public static final int DELETE = 3;
		
		/** 准确  */
		public static final int RIGHT = 4;
		
		/** 是 */
		public static final int YES = 1;
		
		/** 否 */
		public static final int NO = 0;
		
		/** 默认值 */
		public static final int DEFAULT_VALUE = -1;
		
		/** 单条数据  */
		public static final int SINGLE_LINE = 1;
		
	}
	


}