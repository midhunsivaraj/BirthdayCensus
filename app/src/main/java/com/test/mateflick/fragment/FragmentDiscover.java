package com.test.mateflick.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.test.mateflick.R;
import com.test.mateflick.adapter.ViewPagerAdapter;
import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.ui.ViewDiemensionHelper;

@Deprecated
public class  FragmentDiscover extends BaseFragment implements TabLayout.OnTabSelectedListener {


    public FragmentDiscover() {
    }

    private float tabX, tabY, tabHeight, tabHalfHeight;
    private float imagex, imageY, imageHeight, imageHalfHeight;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mImageTabSelectedBackground;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        mViewPager = (ViewPager) activity.findViewById(R.id.viewPager);
        mImageTabSelectedBackground = (ImageView) activity.findViewById(R.id.imgTabSelector);

        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) activity.findViewById(R.id.tabProfile);
        mTabLayout.setupWithViewPager(mViewPager);

        this.getView().getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Layout has happened here.
                        int screenWidth = ViewDiemensionHelper.getWidth(getActivity());
                        int tabWidth = screenWidth / 3;
                        int tabHalfWidth = tabWidth / 2;

                        tabX = mTabLayout.getX();
                        tabY = mTabLayout.getY();
                        tabHeight = mTabLayout.getHeight();
                        tabHalfHeight = tabHeight / 2;
                        M.log("dimen tab", "x = " + tabX + " " + "Y = " + tabY + " " + "tabheight = " + tabHeight + " ");
                        imagex = mImageTabSelectedBackground.getX();
                        imageY = mImageTabSelectedBackground.getY();
                        imageHeight = mImageTabSelectedBackground.getHeight();
                        imageHalfHeight = imageHeight / 2;

                        M.log("dimen image", "x = " + imagex + " " + "Y = " + imageY + " " + "image height = " + imageHeight + " ");
                        //mImageTabSelectedBackground.setMaxWidth(screenWidth);
                        //mImageTabSelectedBackground.setMaxHeight(screenWidth);
                        mImageTabSelectedBackground.setX(imagex - imageHalfHeight + tabHalfHeight);
                        mImageTabSelectedBackground.setY(imageY - imageHalfHeight + tabHalfHeight);
                        // Don't forget to remove your listener when you are done with it.
                        FragmentDiscover.this.getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });


        mTabLayout.setOnTabSelectedListener(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover_new, container, false);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentDiscoverBirthmates(), "BIRTHMATES");
        adapter.addFragment(new FragmentUserWishList(), "LIVESTREAM");
        adapter.addFragment(new FragmentUserFriends(), "WISHES");

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int screenWidth = ViewDiemensionHelper.getWidth(getActivity());
        int tabWidth = screenWidth / 3;
        int tabHalfWidth = tabWidth / 2;
        int halfWidth = screenWidth / 2;
        int index = tab.getPosition();
        int imageHalfWidth = mImageTabSelectedBackground.getWidth() / 2;
        tabX = mTabLayout.getX();
        tabY = mTabLayout.getY();
        tabHeight = mTabLayout.getHeight();
        tabHalfHeight = tabHeight / 2;
        int toX = 0;
        if (index == 0) {
            toX = tabWidth / 2;
        } else if (index == 1) {
            toX = tabWidth + tabWidth / 2;
        } else if (index == 2) {
            toX = tabWidth + tabWidth + tabWidth / 2;
        }

        float currentY = mImageTabSelectedBackground.getY();

        TranslateAnimation translateAnimation = new TranslateAnimation(mImageTabSelectedBackground.getX(), toX, currentY, currentY);
        translateAnimation.setDuration(800);
        //mImageTabSelectedBackground.startAnimation(translateAnimation);


        M.log("dimen tab", "x = " + tabX + " " + "Y = " + tabY + " " + "tabheight = " + tabHeight + " ");
        imagex = mImageTabSelectedBackground.getX();
        imageY = mImageTabSelectedBackground.getY();
        imageHeight = mImageTabSelectedBackground.getHeight();
        imageHalfHeight = imageHeight / 2;
        if (index == 0) {
            mImageTabSelectedBackground.setX(imagex - imageHalfHeight + tabHalfHeight);
            //mImageTabSelectedBackground.setY(imageY-imageHalfHeight+tabHalfHeight);
        } else if (index == 1) {
            mImageTabSelectedBackground.setX(0);
            //mImageTabSelectedBackground.setY(imageY-imageHalfHeight+tabHalfHeight);
        } else if (index == 2) {
            mImageTabSelectedBackground.setX(imageHeight - imageHalfHeight + tabHalfHeight);
            //mImageTabSelectedBackground.setY(imageY-imageHalfHeight+tabHalfHeight);
        }


        M.log("dimen image", "x = " + imagex + " " + "Y = " + imageY + " " + "image height = " + imageHeight + " ");

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
