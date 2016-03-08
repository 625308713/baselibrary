package com.android.enums;

/**
 * 运营商枚举
 * @author daijinge
 */
public enum ISPEnum {
	/**移动  */
	YD(10086,"移动"), 
	
	/**联通  */
	LT(10010,"联通"),
	
	/**电信  */
	DX(10000,"电信");
	
	private int id;
	private String name;
	
	private ISPEnum(int id,String name){
		this.id = id;
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
