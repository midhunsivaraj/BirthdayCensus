package com.test.mateflick.utils.messages;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.test.mateflick.BuildConfig;

/**
 * Created by Xtron Labs 05 on 3/21/2016.
 */
public abstract class M {
    public static void log(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }


    public static void showAlert(Context context, String title, String message, String positiveText,
                                 String negativeText, DialogInterface.OnClickListener positiveClickListener,
                                 DialogInterface.OnClickListener negativeClickLIstener, boolean cancellable) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveClickListener)
                .setNegativeButton(negativeText, negativeClickLIstener)
                .setCancelable(cancellable)
                .show();
    }
}
