package com.android.enums;

/**
 * 网络状态枚举
 * @author sunjuncai
 */
public enum NetEnum {
	
	/** 网络已连接  */
	CONNECTED(1001), 
	
	/** 网络连接中  */
	CONNECTING(1002),
	
	/** 无网络  */
	NO(1003);
	
	private int _id;
	
	NetEnum(int id) {
		_id = id;
	}

	public int getId() {
		return _id;
	}
}
