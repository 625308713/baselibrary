package com.android.enums;

/**
 * DLL类型枚举
 * @author sunjuncai
 */
public enum DLLEnum {
	
	/** 新增 */
	NEW_ADD(1),
	
	/** 修改 */
	MODIFY(2), 
	
	/** 删除 */
	DELETE(3),
	
	/** 准确  */
	RIGHT(4);
	
	private int _id;
	
	DLLEnum(int id) {
		_id = id;
	}

	public int getId() {
		return _id;
	}
	
}
