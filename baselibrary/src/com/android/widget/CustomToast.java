package com.android.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.base.R;

/**
 *  自定义Toast
 *  @author yaguang.wang
 *
 */
public class CustomToast extends Toast {
	
	public static  CustomToast customToast;
	private Context context;
	
	private LayoutInflater inflater;

	private View toastLayout;
	
	private TextView toastText;
	
	public synchronized static CustomToast getInstance(Context context){
		if (null == customToast) {
			customToast = new CustomToast(context);
		}
		return customToast;
	}
	
	public CustomToast(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	private void init(){
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		toastLayout = inflater.inflate(R.layout.custom_toast, null);
		toastText = (TextView) toastLayout.findViewById(R.id.toast_text);
		setView(toastLayout);
		setGravity(Gravity.TOP, 0, 95);
	}
	
	
	public void ToastText(CharSequence text){
		toastText.setText(text);
		show();
	}
	
	public void ToastText(CharSequence text,int Time){
		toastText.setText(text);
		Animation inAnimation = AnimationUtils.makeInChildBottomAnimation(context);
		getView().setAnimation(inAnimation);
		setDuration(Time);
		show();
	}
	
	public void ToastImgText(CharSequence text,int resId){
		toastText.setText(text);
		toastText.setCompoundDrawablePadding(10);
		toastText.setCompoundDrawables(context.getResources().getDrawable(resId),null, null, null);
		Animation inAnimation = AnimationUtils.makeInChildBottomAnimation(context);
		getView().setAnimation(inAnimation);
		show();
	}
}
