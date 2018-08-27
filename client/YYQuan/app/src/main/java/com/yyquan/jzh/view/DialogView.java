package com.yyquan.jzh.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by jzh on 2015/11/2.
 */
public class DialogView {

    public static SweetAlertDialog pDialog;
    public static String TAG = "DialogView";

    public static void Initial(Context context, String message) {
        try {
            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#1ed6ff"));
            pDialog.setTitleText(message);
            pDialog.setCancelable(false);
        }catch (Exception e){
            Log.d(TAG, "DialogView , Initial: ");
            e.printStackTrace();
        }
    }
    public static void show() {
        try {
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.show();
            }
        }catch (Exception e){
            Log.d(TAG, "DialogView , show: ");
            e.printStackTrace();
        }
    }

    public static void dismiss() {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }catch (Exception e){
            Log.d(TAG, "DialogView , dismiss: ");
            e.printStackTrace();
        }
    }
}
