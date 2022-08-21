package com.test.mateflick.utils.helper.social;

/**
 * Created by Xtron005 on 28-07-2016.
 */
public interface ISocialIntegrationCallBacks {
    void onAuthenticationSuccess();
    void onAuthenticationFailure();
    void onAuthenticationDenied();
}
