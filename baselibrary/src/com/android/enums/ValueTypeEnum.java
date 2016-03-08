package com.android.enums;

/**
 * 数值类型枚举
 * @author sunjuncai
 */
public enum ValueTypeEnum {
	
	/** 字符型  */
	STRING(1),
	
	/** 数值型  */
	INT(2), 
	
	/** 字典  */
	JW(3);
	
	private int _id;
	
	ValueTypeEnum(int id) {
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
	public static ValueTypeEnum valueOf(int value) {
        switch (value) {
	        case 1:
	            return STRING;
	        case 2:
	            return INT;
	        case 3:
	        	return JW;
        }
        return null;
    }
}
