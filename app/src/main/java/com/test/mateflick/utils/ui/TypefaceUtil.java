package com.test.mateflick.utils.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.test.mateflick.utils.messages.M;

import java.util.Hashtable;

/**
 * Created by Xtron Labs 05 on 3/21/2016.
 */
public abstract class TypefaceUtil {

    public static final String APP_FONT = "font.ttf";

    private static final String TAG = "TypeFace";
    private static final Hashtable<String, Typeface> typefaceCache = new Hashtable<>();

    public static void setTypeFace(Context context, TextView v, String typefacePath) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), typefacePath);
        v.setTypeface(typeface);
    }



    public static @Nullable
    Typeface get(@NonNull Context context, @NonNull String typefacePath) {
        synchronized (typefaceCache) {
            if (!typefaceCache.containsKey(typefacePath)) {
                try {
                    Typeface t = Typeface.createFromAsset(context.getAssets(), typefacePath);
                    typefaceCache.put(typefacePath, t);
                } catch (Exception e) {
                    M.log(TAG, "Could not load typeface, \"" + typefacePath + "\" because " + e.getMessage());
                    return Typeface.DEFAULT;
                }
            }
        }
        return typefaceCache.get(typefacePath);
    }

}

