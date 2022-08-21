package com.test.mateflick.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.mateflick.R;
import com.test.mateflick.utils.messages.M;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class BirthDayDateSelector extends LinearLayout implements View.OnClickListener,
        View.OnTouchListener {

    private TextView mLblMonth;
    private TextView mLblDate;
    private TextView mLblYear;
    private TextView mLblSelectedDate;
    private LinearLayout mHolder;
    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;
    private GestureDetectorCompat mGestureDetector;

    public String getBirthDay() {
        return mLblSelectedDate.getText().toString();
    }

    public BirthDayDateSelector(Context context) {
        super(context);
        init(context);
    }

    public BirthDayDateSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BirthDayDateSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.birthday_date_selector, this);
        mCalendar = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("MMM");
        mGestureDetector = new GestureDetectorCompat(context, new OurGestureListener());
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLblMonth = (TextView) this.findViewById(R.id.bDay_lblMonth);
        mLblDate = (TextView) this.findViewById(R.id.bDay_lblDat);
        mLblYear = (TextView) this.findViewById(R.id.bDay_lblYear);
        mLblSelectedDate = (TextView) this.findViewById(R.id.bDay_lblSelectedDate);
        mHolder = (LinearLayout) this.findViewById(R.id.bDay_Holder);

        mLblSelectedDate.setOnClickListener(this);
        mLblSelectedDate.setText("DATE OF BIRTH");

        updateDate(mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DATE),
                mCalendar.get(Calendar.YEAR));

        mLblSelectedDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        mLblMonth.setOnTouchListener(this);
        mLblYear.setOnTouchListener(this);
        mLblDate.setOnTouchListener(this);
    }

    private void updateDate(int month, int date, int year) {
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DATE, date);
        mCalendar.set(Calendar.YEAR, year);

        mLblDate.setText(date + "");
        mLblYear.setText(year + "");
        mLblMonth.setText(mDateFormat.format(mCalendar.getTime()));


    }

    @Override
    public void onClick(View v) {
        boolean dateSelectorVisible = mHolder.getVisibility() == View.VISIBLE;
        mHolder.setVisibility(dateSelectorVisible ? View.GONE : View.VISIBLE);
        if (dateSelectorVisible) {
            mLblSelectedDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_black_36dp, 0);
        } else {
            mLblSelectedDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.bDay_lblMonth: {
                processTouch(mLblMonth, event);
                break;
            }
            case R.id.bDay_lblDat: {
                processTouch(mLblDate, event);
                break;
            }
            case R.id.bDay_lblYear: {
                processTouch(mLblYear, event);
                break;
            }
        }
        return false;
    }


    private void processTouch(TextView tv, MotionEvent event) {
        Drawable[] drawables = tv.getCompoundDrawables();
        Rect topRect = drawables[1].getBounds();
        Rect bottomRect = drawables[3].getBounds();

        float x = event.getX();
        float y = event.getY();

        if (touchedAtTop(tv, topRect.height(), x, y)) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                increment(tv.getId());
            }
        } else if (touchedAtBottom(tv, bottomRect.height(), x, y)) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                decrement(tv.getId());
            }
        } else if (touchedInBetween(tv, x, y)) {
            M.log("in between : ", "YES");
            M.log("action :" , event.getAction()+"");
            //if (event.getAction() == MotionEvent.ACTION_MASK) {
                M.log("in between : ", "MOVING");
                mGestureDetector.onTouchEvent(event);
            //}
        }
    }

    private void setDate(Calendar calendar) {
        int date = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);

        mLblMonth.setText(mDateFormat.format(mCalendar.getTime()));
        mLblDate.setText(date + "");
        mLblYear.setText(year + "");
        mLblSelectedDate.setText(mLblMonth.getText() + "/" + date + "/" + year);
    }

    private void increment(int textViewId) {
        switch (textViewId) {
            case R.id.bDay_lblMonth: {
                mCalendar.add(Calendar.MONTH, 1);
                break;
            }
            case R.id.bDay_lblDat: {
                mCalendar.add(Calendar.DATE, 1);
                break;
            }
            case R.id.bDay_lblYear: {
                mCalendar.add(Calendar.YEAR, 1);
                break;
            }
        }
        setDate(mCalendar);
    }

    private void decrement(int textViewId) {
        switch (textViewId) {
            case R.id.bDay_lblMonth: {
                mCalendar.add(Calendar.MONTH, -1);
                break;
            }
            case R.id.bDay_lblDat: {
                mCalendar.add(Calendar.DATE, -1);
                break;
            }
            case R.id.bDay_lblYear: {
                mCalendar.add(Calendar.YEAR, -1);
                break;
            }
        }
        setDate(mCalendar);
    }


    private boolean touchedInBetween(TextView tv, float x, float y) {
        int xMin = tv.getPaddingLeft();
        int xMax = tv.getWidth() - tv.getPaddingRight();
        int yMin = tv.getPaddingTop();
        int yMax = tv.getHeight() - tv.getPaddingBottom();
        return x > xMin && x < xMax && y > yMin && y < yMax;
    }

    private boolean touchedAtTop(TextView tv, int drawableHeight, float x, float y) {
        int xMin = tv.getPaddingLeft();
        int xMax = tv.getWidth() - tv.getPaddingRight();
        int yMin = tv.getPaddingTop();
        int yMax = tv.getPaddingTop() + drawableHeight;
        return x > xMin && x < xMax && y > yMin && y < yMax;
    }

    private boolean touchedAtBottom(TextView tv, int drawableHeight, float x, float y) {
        int xMin = tv.getPaddingLeft();
        int xMax = tv.getWidth() - tv.getPaddingRight();
        int yMax = tv.getHeight() - tv.getPaddingBottom();
        int yMin = yMax - drawableHeight;
        return x > xMin && x < xMax && y > yMin && y < yMax;
    }

    private final class OurGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            //return super.onDown(e);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffY = e2.getY() - e1.getY();
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeUp();
                } else {
                    onSwipeDown();
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        void onSwipeUp() {
            M.log("swipe : ", "Up");
        }

        void onSwipeDown() {
            M.log("swipe : ", "Down");
        }

    }

}
