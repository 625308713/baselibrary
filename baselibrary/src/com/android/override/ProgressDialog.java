package com.android.override;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.android.base.R;


/**
 * 自定义进度框
 * @author daijinge
 */
public class ProgressDialog extends Dialog {
	
	@SuppressWarnings("unused")
	private Context context = null;
	
	private static ProgressDialog progressDialog = null;
	
	public ProgressDialog(Context context){
		super(context);
		this.context = context;
	}
	
	public ProgressDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public static ProgressDialog createDialog(Context context){
		//每次都需要初始化
//		if(null == progressDialog){
			progressDialog = new ProgressDialog(context,R.style.CustomProgressDialog);
			progressDialog.setContentView(R.layout.dialog_progress);
			progressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//		}
		return progressDialog;
	}
	
    //提示内容
    public ProgressDialog setMessage(String strMessage){
    	if(null != progressDialog){
    		TextView msgTv = (TextView) progressDialog.findViewById(R.id.loading_msg_tv);
    		if (msgTv != null){
    			msgTv.setText(strMessage);
    		}
    	}
    	return progressDialog;
    }
}
