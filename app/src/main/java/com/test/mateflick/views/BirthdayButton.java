package com.test.mateflick.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.test.mateflick.R;
import com.test.mateflick.utils.ui.TypefaceUtil;

/**
 * Created by Xtron Labs 05 on 3/21/2016.
 */
public class BirthdayButton extends Button {
    public BirthdayButton(Context context) {
        super(context);
    }

    public BirthdayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BirthdayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BirthdayButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.View,0,0);
        try {
            String font = ta.getString(R.styleable.View_customFont);
            if (font!= null)
                setTypeface(TypefaceUtil.get(context,font));
        }finally {
            ta.recycle();
        }
    }
}
