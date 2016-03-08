package com.android.update;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.base.R;

/**
 * 
 *  弹出dlg强制更新
 *  @author yaguang.wang
 *
 */
public class UpdateDialog extends AlertDialog {
	
	private Activity context;
	
	private HashMap<String, Object> args;
	
    public UpdateDialog(Activity context,HashMap<String, Object> args) {
		super(context);
		this.context = context;
		this.args = args;
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.newUpdateAvailable);
        builder.setMessage((String)args.get(Constants.APK_UPDATE_CONTENT))
                .setPositiveButton(R.string.dialogPositiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToDownload();
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.dialogNegativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    	
                    	((Activity) context).finish();
                    }
                });
    	builder.create();
    }

    private void goToDownload() {
    	Intent intent=new Intent(context,DownloadService.class);
    	intent.putExtra(Constants.APK_DOWNLOAD_URL, (String)args.get(Constants.APK_DOWNLOAD_URL));
    	context.startService(intent);
    }
}
