package com.test.mateflick.utils.helper;

import android.view.View;

/**
 * Created by Xtron005 on 04-07-2016.
 */
public class UiHelper {

    private static UiHelper mUiHelper = new UiHelper();

    private UiHelper() {
    }

    public static UiHelper getInstance() {
        return mUiHelper;
    }

    public void toggleViewEnabled(boolean enable, View[] viewsToToggleEnabled) {
        for (View v : viewsToToggleEnabled) {
            v.setEnabled(enable);
        }
    }

}
