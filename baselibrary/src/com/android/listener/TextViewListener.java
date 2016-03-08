package com.android.listener;

import android.widget.TextView;

/**
 * 动态改变文本内容
 * @author sunjuncai
 *
 */
public interface TextViewListener {
	
	/**
	 * 传递文本框
	 * @param textView
	 */
	public void setTextView(TextView textView);
}
