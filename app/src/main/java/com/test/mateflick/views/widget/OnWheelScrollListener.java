package com.test.mateflick.views.widget;

/**
 * Created by Xtron Labs 05 on 3/21/2016.
 */
/**
 * Wheel scrolled listener interface.
 */
public interface OnWheelScrollListener {
    /**
     * Callback method to be invoked when scrolling started.
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingStarted(WheelView wheel);

    /**
     * Callback method to be invoked when scrolling ended.
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingFinished(WheelView wheel);
}
