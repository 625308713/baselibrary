package com.android.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.android.data.ResponseData;
import com.android.enums.ResponseEnum;
import com.google.gson.Gson;

/**
 * GSON 工具类
 * @author sunjuncai
 *
 */
public class GsonUtil {
	
	/** JSON解析失败 */
	public static final String JSON_EXCEPTION ="JSON解析失败";
	
	/**
	 * JSON 转换为对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> ResponseData<T> fromJson(String json, Class<T> clazz) {
		ResponseData<T> data = null;
		try {
			Gson gson = new Gson();
			Type objectType = type(ResponseData.class, clazz);
			data = gson.fromJson(json, objectType);
		} catch (Exception e) {
			e.printStackTrace();
			data = new ResponseData<T>();
			data.setErrorMsg(e.getMessage());
			data.setMessage(JSON_EXCEPTION);
			data.setResultType(ResponseEnum.JSONException);
		}
		return data;
	}
	
	/**
	 * 对象转换为JSON
	 * @param clazz
	 * @return
	 */
	public static String toJson(Object obj){
		if (null != obj) {
			Gson gson = new Gson();
			String str = gson.toJson(obj);
			return str;
		}
		return null;
	}
	
	private static <T> ParameterizedType type(final Class<T> raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw;
			}
			public Type[] getActualTypeArguments() {
				return args;
			}
			public Type getOwnerType() {
				return null;
			}
		};
	}
	
}
