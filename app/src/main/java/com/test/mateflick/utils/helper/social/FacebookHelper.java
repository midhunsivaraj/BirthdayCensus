package com.test.mateflick.utils.helper.social;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.List;

public final class FacebookHelper extends AbstractSocialIntegrator {

    private CallbackManager mCallbackManager;
    private LoginManager mLoginManager;

    public FacebookHelper(ISocialIntegrationCallBacks socialIntegrationCallBacks, Context context) {
        super(socialIntegrationCallBacks, context);
    }

    @Override
    void performInitializationIfAny() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException e) {

                    }
                });
    }

    @Override
    public void authenticate(Fragment fragment) {
        List<String> permissions = Arrays.asList("user_photos", "friends_photos", "email",
                "user_birthday", "user_friends");
        mLoginManager.logInWithReadPermissions(fragment, permissions);
    }
}
