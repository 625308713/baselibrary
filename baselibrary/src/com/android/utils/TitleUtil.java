package com.android.utils;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.BaseChannel.PullView;
import com.android.base.R;

/**
 * 标题工具类
 * @author sunjuncai
 *
 */
public class TitleUtil {
	
	
	/**
	 *  设置标题栏背景颜色
	 *  @param color res 颜色resid
	 */
	public static void setTitleBarBackground(PullView pull,int colorRes){
		RelativeLayout topLL = (RelativeLayout) pull.find(R.id.tower_title_top);
		topLL.setBackgroundResource(colorRes);
	}
	
	/**
	 *  设置标题栏背景
	 * @param context 上下文
	 * @param drawableRes 背景图片resid
	 */
	public static void setTitleBarDrawable(PullView pull,Context context, int drawableRes){
		RelativeLayout topLL = (RelativeLayout) pull.find(R.id.tower_title_top);
		topLL.setBackgroundDrawable(context.getResources().getDrawable(drawableRes));
	}
	
	/**
	 *设置标题
	 */
	public static void setTitleCenter(PullView pull,String title){
		//标题居中
		TextView titleCenterTV = (TextView) pull.find(R.id.title_center_tv);
		titleCenterTV.setText(title.replaceAll("\\n", ""));
	}
	
	/**
	 *设置标题
	 */
	public static void setTitleCenter(PullView pull,int resId){
		setTitleCenter(pull,pull.getS(resId));
	}
	
	/***
	 * 设置标题返回按钮
	 */
	public static void setTitleBack(PullView pull,int resId){
		Button titleLeftBtn = (Button) pull.find(R.id.title_back_btn);
		titleLeftBtn.setVisibility(View.VISIBLE);
		titleLeftBtn.setText(pull.getS(resId));
		titleLeftBtn.setOnClickListener(pull.returnListener());
	}
	
	/**
	 * 设置标题查询按钮
	 * @param pull
	 * @param resId
	 */
	public static void setTitleQuery(PullView pull,int resId){
		Button titleQueryBtn = (Button) pull.find(R.id.title_query_btn);
		titleQueryBtn.setVisibility(View.VISIBLE);
		titleQueryBtn.setText(pull.getS(resId));
		titleQueryBtn.setOnClickListener(pull.returnListener());
	}
	
	/***
	 * 设置标题右按钮
	 */
	public static void setTitleRight(PullView pull,int resId){
		Button titleRightBtn = (Button) pull.find(R.id.title_right_btn);
		titleRightBtn.setVisibility(View.VISIBLE);
		titleRightBtn.setText(pull.getS(resId));
		titleRightBtn.setOnClickListener(pull.returnListener());
	}
	
	/***
	 * 设置标题右按钮的显示隐藏
	 */
	public static void setTitleRightVisible(PullView pull,int id){
		Button titleRightBtn = (Button) pull.find(R.id.title_right_btn);
		titleRightBtn.setOnClickListener(pull.returnListener());
		switch (id) {
			case View.VISIBLE:
				titleRightBtn.setVisibility(View.VISIBLE);
				break;
			case View.GONE:
				titleRightBtn.setVisibility(View.GONE);
				break;
		}
		
	}
	
}
