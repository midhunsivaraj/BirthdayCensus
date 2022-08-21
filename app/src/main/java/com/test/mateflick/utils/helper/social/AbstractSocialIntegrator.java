package com.test.mateflick.utils.helper.social;


import android.content.Context;
import android.support.v4.app.Fragment;

public abstract class AbstractSocialIntegrator {

    protected ISocialIntegrationCallBacks mSocialIntegrationCallBacks;
    protected Context mContext;


    public AbstractSocialIntegrator(ISocialIntegrationCallBacks socialIntegrationCallBacks
            , Context context) {
        mSocialIntegrationCallBacks = socialIntegrationCallBacks;
        mContext = context;
        performInitializationIfAny();
    }

    abstract void performInitializationIfAny();

    public abstract void authenticate(Fragment fragment);

}
