package com.android.override;

import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.R;
import com.android.listener.MainListener;
import com.android.listener.TextViewListener;

/**
 * 自定义对话框
 * @author sunjuncai
 */
public class CustomDialog extends Dialog {
	
	/** 对话框提示信息 */
	private static TextView dialogInfoTV;
	
	/** 对话框图标  */
	private static ImageView dialogIconIV;
	
	/** 对话框确定按钮  */
	private static Button dialogEnterBtn;
	
	/** 对话框提示信息  */
	public static final String DIALOG_INFO = "info";
	
	/** 对话框确定按钮 */
	public static final String DIALOG_ENTER = "enter";
	
	/** 对话框取消按钮  */
	public static final String DIALOG_CANCEL = "cancel";
	
	/** 对话框提示信息颜色 */
	public static final String DIALOG_COLOR = "color";
	
	/** 对话框图标 */
	public static final String DIALOG_ICON = "icon";
	
	/** 成功 */
	public static final String ICON_SUCCESS = "success";
	
	/** 失败 */
	public static final String ICON_ERROR = "error";
	
    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }
 
    public CustomDialog(Context context) {
        super(context);
    }
 
    public static class Builder {
    	
    	/** 上下文 */
        private Context context;
        
        /** 主监听 */
        private MainListener mainListener = new MainListener();
        
        public Builder(Context context) {
            this.context = context;
        }
        
        /**
         * 创建对话框
         * @param info
         * @param clickListener
         * @return
         */
        public CustomDialog createDialog(Map<String, String> map,View.OnClickListener clickListener) {
        	 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             final CustomDialog dialog = new CustomDialog(context,R.style.CustomDialogWindow);
             View layout = inflater.inflate(R.layout.dialog_custom, null);
             TextView infoTv = (TextView) layout.findViewById(R.id.custom_dialog_info);
             Button enterBtn = (Button) layout.findViewById(R.id.btn_enter);
             Button cancelBtn = (Button) layout.findViewById(R.id.btn_cancel);
             ImageView imageView = (ImageView) layout.findViewById(R.id.custom_dialog_img);
             setDialogInfoTV(infoTv);
             setDialogIconIV(imageView);
             setDialogEnterBtn(enterBtn);
             String info = null;
             String icon = null;
             String enter = null;
             String cancel = null;
             String textColor = null;
             if(null != map && !map.isEmpty()){
            	 info = map.get(DIALOG_INFO);
            	 icon = map.get(DIALOG_ICON);
            	 enter = map.get(DIALOG_ENTER);
            	 cancel = map.get(DIALOG_CANCEL);
            	 textColor = map.get(DIALOG_COLOR);
             }
             infoTv.setText(info);
             if(!TextUtils.isEmpty(textColor)){
            	 infoTv.setTextColor(Color.parseColor(textColor)); 
             }
             if(!TextUtils.isEmpty(icon)){
            	 imageView.setVisibility(View.VISIBLE);
            	 if(icon.equals(ICON_SUCCESS)){
            		 imageView.setBackgroundResource(R.drawable.dialog_success_icon);
            	 }else if(icon.equals(ICON_ERROR)){
            		 imageView.setBackgroundResource(R.drawable.dialog_fail_icon);
            	 }
             }else{
            	 imageView.setVisibility(View.GONE);
             }
             enterBtn.setText(enter);
             cancelBtn.setText(cancel);
             enterBtn.setOnClickListener(clickListener);
             cancelBtn.setOnClickListener(clickListener);
             dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
             dialog.setContentView(layout);
             dialog.setCanceledOnTouchOutside(true);
             return dialog;
        }
        
        /**
         * 创建对话框
         * @param info
         * @param clickListener
         * @return
         */
        public CustomDialog createProgressDialog() {
        	 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             final CustomDialog dialog = new CustomDialog(context,R.style.CustomDialogWindow);
             View layout = inflater.inflate(R.layout.dialog_progress, null);
             dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
             dialog.setContentView(layout);
             dialog.setCanceledOnTouchOutside(true);
             return dialog;
        }
        
        
        
        /**
         * 创建动态文本对话框
         * @param info
         * @param clickListener
         * @return
         */
        public CustomDialog createDialog(Map<String, String> map,View.OnClickListener clickListener,TextViewListener changeTextViewListener) {
        	 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             final CustomDialog dialog = new CustomDialog(context,R.style.CustomDialogWindow);
             View layout = inflater.inflate(R.layout.dialog_custom, null);
             TextView infoTv = (TextView) layout.findViewById(R.id.custom_dialog_info);
             //传递textview
             changeTextViewListener.setTextView(infoTv);
             Button enterBtn = (Button) layout.findViewById(R.id.btn_enter);
             Button cancelBtn = (Button) layout.findViewById(R.id.btn_cancel);
             String info = null;
             String enter = null;
             String cancel = null;
             if(null != map && !map.isEmpty()){
            	 info = map.get(DIALOG_INFO);
            	 enter = map.get(DIALOG_ENTER);
            	 cancel = map.get(DIALOG_CANCEL);
             }
             infoTv.setText(info);
             enterBtn.setText(enter);
             cancelBtn.setText(cancel);
             enterBtn.setOnClickListener(clickListener);
             cancelBtn.setOnClickListener(clickListener);
             dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
             dialog.setContentView(layout);
             dialog.setCanceledOnTouchOutside(true);
             return dialog;
        }
        
        /**
         * 创建网络设置对话框
         * @return
         */
        public CustomDialog createNetSelectDialog() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context,R.style.CustomDialogWindow);
            View layout = inflater.inflate(R.layout.dialog_network, null);
            Button configBtn = (Button) layout.findViewById(R.id.dialog_network_config_btn);
            Button cancelBtn = (Button) layout.findViewById(R.id.dialog_network_cancel_btn);
            mainListener.setDialog(dialog);
            configBtn.setOnClickListener(mainListener);
            cancelBtn.setOnClickListener(mainListener);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(true);
            return dialog;
        }
        
        /**
         * 创建退出对话框
         * @return
         */
        public CustomDialog createExitDialog() {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context,R.style.CustomDialogWindow);
            View layout = inflater.inflate(R.layout.dialog_exit, null);
            mainListener.setDialog(dialog);
            ((Button)layout.findViewById(R.id.dialog_exit_btn_enter)).setOnClickListener(mainListener);
            ((Button)layout.findViewById(R.id.dialog_exit_btn_cancle)).setOnClickListener(mainListener);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(true);
            return dialog;
        }
        
        /**
         * 创建Gps对话框
         * @return
         */
        public CustomDialog createGpsDialog() {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context,R.style.CustomDialogWindow);
            View layout = inflater.inflate(R.layout.dialog_gps, null);
            mainListener.setDialog(dialog);
            ((Button)layout.findViewById(R.id.dialog_gps_btn_outside)).setOnClickListener(mainListener);
            ((Button)layout.findViewById(R.id.dialog_gps_btn_inside)).setOnClickListener(mainListener);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(true);
            return dialog;
        }
        
        /**
         * 创建Gps对话框
         * @return
         */
        public CustomDialog createGpsOpenDialog() {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context,R.style.CustomDialogWindow);
            View layout = inflater.inflate(R.layout.dialog_gps_open, null);
            mainListener.setDialog(dialog);
            ((Button)layout.findViewById(R.id.dialog_gps_open_btn_enter)).setOnClickListener(mainListener);
            ((Button)layout.findViewById(R.id.dialog_gps_open_btn_cancle)).setOnClickListener(mainListener);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(true);
            return dialog;
        }
        
        /**
         * 创建编辑对话框
         * @return
         */
        public CustomDialog createEditDialog(String title,final TextView editText,boolean isEdit,String hint) {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context,R.style.CustomDialogWindow);
            View layout = inflater.inflate(R.layout.dialog_edit, null);
            TextView titleTv = (TextView) layout.findViewById(R.id.dialog_edit_title);
            final EditText dialog_edit_content = ((EditText)layout.findViewById(R.id.dialog_edit_content));
            Button dialog_edit_enter = (Button) layout.findViewById(R.id.dialog_edit_enter);
            Button dialog_btn_cancle = (Button) layout.findViewById(R.id.dialog_edit_cancle);
            if(isEdit){
            	dialog_edit_enter.setVisibility(View.VISIBLE);
            }else{
            	dialog_edit_enter.setVisibility(View.GONE);
            }
            titleTv.setText(title);
            if(null != editText){
            	String startText = editText.getText().toString().trim();
            	if(!TextUtils.isEmpty(startText)){
            		dialog_edit_content.setText(startText);
            	}else{
            		dialog_edit_content.setText(null);
            	}
            	dialog_edit_content.setHint(hint);
            }
            dialog_edit_enter.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				if(null != editText){
    					String endText = dialog_edit_content.getText().toString().trim();
    					if(TextUtils.isEmpty(endText)){
    						editText.setText("");
    					}else{
    						editText.setText(endText);
    					}
    					dialog.cancel();
    				}
    			}
    		});
            dialog_btn_cancle.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				dialog.cancel();
    			}
            });
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(true);
            return dialog;
        }
        
        /**
         * 创建编辑对话框
         * @return
         */
        public CustomDialog createEditDialog(String title,final TextView editText,boolean isEdit,String hint,int size) {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context,R.style.CustomDialogWindow);
            View layout = inflater.inflate(R.layout.dialog_edit, null);
            TextView titleTv = (TextView) layout.findViewById(R.id.dialog_edit_title);
            final EditText dialog_edit_content = ((EditText)layout.findViewById(R.id.dialog_edit_content));
            Button dialog_edit_enter = (Button) layout.findViewById(R.id.dialog_edit_enter);
            Button dialog_btn_cancle = (Button) layout.findViewById(R.id.dialog_edit_cancle);
            dialog_edit_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(size)});
            if(isEdit){
            	dialog_edit_enter.setVisibility(View.VISIBLE);
            }else{
            	dialog_edit_content.setEnabled(false);
            	dialog_edit_enter.setVisibility(View.GONE);
            }
            titleTv.setText(title+"(限制"+size+"个字)");
            if(null != editText){
            	String startText = editText.getText().toString().trim();
            	if(!TextUtils.isEmpty(startText)){
            		dialog_edit_content.setText(startText);
            	}else{
            		dialog_edit_content.setText(null);
            	}
            	dialog_edit_content.setHint(hint);
            }
            dialog_edit_enter.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				if(null != editText){
    					String endText = dialog_edit_content.getText().toString().trim();
    					if(TextUtils.isEmpty(endText)){
    						editText.setText("");
    					}else{
    						editText.setText(endText);
    					}
    					dialog.cancel();
    				}
    			}
    		});
            dialog_btn_cancle.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				dialog.cancel();
    			}
            });
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(true);
            return dialog;
        }
        
        
        
    }
   
	public static TextView getDialogInfoTV() {
		return dialogInfoTV;
	}

	public static void setDialogInfoTV(TextView dialogInfoTV) {
		CustomDialog.dialogInfoTV = dialogInfoTV;
	}

	public static ImageView getDialogIconIV() {
		return dialogIconIV;
	}

	public static void setDialogIconIV(ImageView dialogIconIV) {
		CustomDialog.dialogIconIV = dialogIconIV;
	}

	public static Button getDialogEnterBtn() {
		return dialogEnterBtn;
	}

	public static void setDialogEnterBtn(Button dialogEnterBtn) {
		CustomDialog.dialogEnterBtn = dialogEnterBtn;
	}
	

}