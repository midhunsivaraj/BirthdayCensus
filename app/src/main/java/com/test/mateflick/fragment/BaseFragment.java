package com.test.mateflick.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.test.mateflick.R;
import com.test.mateflick.utils.preference.IPreference;

/**
 * Base class for all {@link android.app.Fragment} in the application
 */
public abstract class BaseFragment extends Fragment implements IPreference, View.OnClickListener {


    protected IToggle mIToggle;
    protected ImageButton imgToggleNavigation;
    private ImageView imgTabBackground;
    private float lastY, lastX;
    float imageX;
    float imageY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mIToggle != null)
            mIToggle = (IToggle) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mIToggle = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imgToggleNavigation = (ImageButton) getActivity().findViewById(R.id.imgToggleNavigation);
        imgTabBackground = (ImageView) getActivity().findViewById(R.id.imgTabSelector);
        if (imgToggleNavigation != null)
            imgToggleNavigation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mIToggle != null) {
            mIToggle.toggleNavigation();
        }
    }

    public interface IToggle {
        void toggleNavigation();
    }


    protected void animateToPosition(CompoundButton buttonView) {
        if (imgToggleNavigation != null) {

            int imageWidth = imgTabBackground.getWidth();
            int imageHeight = imgTabBackground.getHeight();
            //region commented
            /*lastY = imgTabBackground.getY();
            lastX = imgTabBackground.getX();

            int height = buttonView.getHeight();
            int width = buttonView.getWidth();

            float toX = lastX - (imageWidth / 2) + buttonView.getX() + width / 2;
            float toY = 0 - (imageHeight / 2) + (height / 2);

            TranslateAnimation translateAnimation = new TranslateAnimation(imageX, toX, imageY, toY);
            translateAnimation.setDuration(400);
            translateAnimation.setFillAfter(true);
            imgTabBackground.startAnimation(translateAnimation);

            imageX = toX;
            imageY = toY;*/
            //endregion

            int top = imgTabBackground.getTop();
            int btnLeft = buttonView.getLeft();
            int btnHeight = buttonView.getHeight();
            int btnWidth = buttonView.getWidth();

            int requiredLeft = btnLeft - ((imageWidth / 2) + (btnWidth / 2));
            int requiredTop = top - ((imageHeight / 2) + (btnHeight / 2));

            ObjectAnimator topAnimator = ObjectAnimator.ofInt(imgTabBackground, "TranslateY", requiredTop);

            ObjectAnimator leftAnimator = ObjectAnimator.ofInt(imgTabBackground, "TranslateX", requiredLeft);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(topAnimator, leftAnimator);
            animatorSet.setDuration(400);
            animatorSet.start();

        }
    }

}
