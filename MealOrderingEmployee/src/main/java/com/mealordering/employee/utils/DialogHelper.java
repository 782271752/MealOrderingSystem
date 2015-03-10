package com.mealordering.employee.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Anthonit on 2014/3/26.
 */
public class DialogHelper {

    public static Dialog buildLoadingDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static Dialog buildYesOrNoDialog(Context context, String message,
                                            DialogInterface.OnClickListener positiveListener,
                                            DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("是", positiveListener);
        builder.setNegativeButton("否", negativeListener);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
