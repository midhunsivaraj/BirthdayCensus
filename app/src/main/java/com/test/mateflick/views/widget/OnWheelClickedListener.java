package com.test.mateflick.views.widget;

/**
 * Created by Xtron Labs 05 on 3/21/2016.
 */
/**
 * Wheel clicked listener interface.
 * <p>The onItemClicked() method is called whenever a wheel item is clicked
 * <li> New Wheel position is set
 * <li> Wheel view is scrolled
 */
public interface OnWheelClickedListener {
    /**
     * Callback method to be invoked when current item clicked
     * @param wheel the wheel view
     * @param itemIndex the index of clicked item
     */
    void onItemClicked(WheelView wheel, int itemIndex);
}
