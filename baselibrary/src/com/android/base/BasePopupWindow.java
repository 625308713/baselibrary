package com.android.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * 公共的底部弹出框
 * @author sunjuncai
 */
public abstract class BasePopupWindow extends PopupWindow {

	/** 上下文*/
	protected Context _context;
	
	private boolean flag = false;
	/**
	 * 控制是否显示背景
	 * 
	 * @param context
	 * @param resId
	 * @param showBackground
	 */
	public BasePopupWindow(Context context, int resId,Object ... param) {
		super(context);
		this._context = context;
		initParam(param);
		View parent = setCustomLayout(resId);
		initView(parent);
		bindComponentEvent();
		initData();
	}

	protected View setCustomLayout(int resId) {
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View popupMainView = inflater.inflate(resId, null);
		// 设置SelectPicPopupWindow的View
		this.setContentView(popupMainView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setOutsideTouchable(true);     
		popupMainView.setFocusableInTouchMode(true);
		popupMainView.setFocusable(true);
	    // 设置此参数获得焦点，否则无法点击     
	    this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		// 设置SelectPicPopupWindow弹出窗体的背
		this.setBackgroundDrawable(new ColorDrawable(Color.argb(136, 0, 0, 0)));
		popupMainView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = popupMainView.getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y>height){
						dismiss();
					}
				}				
				return true;
			}
		});
		popupMainView.setFocusableInTouchMode(true); 
		popupMainView.setOnKeyListener(new OnKeyListener(){
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event){
                	if (keyCode == KeyEvent.KEYCODE_MENU && isShowing() && flag) {
                		dismiss();
                	}
                	flag = true;
                	return false;
                }
        });
		return popupMainView;
	}
	
	/**
	 * 初始化参数
	 * @param param
	 */
	protected abstract void initParam(Object... param);

	/**
	 * 初始化界面
	 */
	protected abstract void initView(View parent);

	/**
	 * 绑定控件事件
	 */
	protected abstract void bindComponentEvent();
	
	/**
	 * 初始化数据
	 */
	protected abstract void initData();

}
