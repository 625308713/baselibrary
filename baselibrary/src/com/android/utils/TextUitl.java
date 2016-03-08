package com.android.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.A.Value;
import com.android.base.R;

/**
 * 文本框、编辑框工具
 * @author sunjuncai
 *
 */
public class TextUitl {

	/**
	 * 获取文本框内容
	 * @return
	 */
	public static String getTextView(TextView textView){
		String text = textView.getText().toString().trim();
		if(!TextUtils.isEmpty(text)){
			return text;
		}else{
			return Value.EMPTY;
		}
	}
	
	/**
	 * 获取编辑框内容
	 * @return
	 */
	public static String getEditText(TextView editText){
		String text = editText.getText().toString().trim();
		if(!TextUtils.isEmpty(text)){
			return text;
		}else{
			setEditTextFocus(editText);
			return Value.EMPTY;
		}
	}
	
	/**
	 * 编辑框光标定位
	 * @param editText
	 */
	public static void setEditTextFocus(TextView editText){
		editText.setFocusable(true);   
		editText.setFocusableInTouchMode(true);   
		editText.requestFocus();  
	}
	
	/**
	 * 设置可编辑
	 */
	public static void setEditYes(TextView textView,Context _context){
		textView.setTextColor(_context.getResources().getColor(R.color.black));
	}
	
	/**
	 * 设置不可编辑
	 */
	public static void setEditNo(TextView textView,Context _context){
		textView.setTextColor(_context.getResources().getColor(R.color.grey));
	}
	
}
