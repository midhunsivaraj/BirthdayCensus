package com.test.mateflick.utils.ui;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Xtron Labs 05 on 3/24/2016.
 */
public final class ViewDiemensionHelper {

    public static int getWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

}
