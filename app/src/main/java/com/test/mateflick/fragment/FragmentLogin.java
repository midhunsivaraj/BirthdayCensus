package com.test.mateflick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.FacebookSdk;
import com.test.mateflick.R;
import com.test.mateflick.activity.ActivitySignUp;
import com.test.mateflick.activity.ActivityUserHome;
import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.helper.social.AbstractSocialIntegrator;
import com.test.mateflick.utils.helper.social.FacebookHelper;
import com.test.mateflick.utils.helper.social.ISocialIntegrationCallBacks;
import com.test.mateflick.utils.helper.social.TwitterHelper;
import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.misc.AuthManager;
import com.test.mateflick.utils.helper.UiHelper;
import com.test.mateflick.utils.network.NetworkManager;
import com.test.mateflick.utils.network.request.LoginRequest;
import com.test.mateflick.utils.network.request.response.LoginResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Xtron Labs 05 on 3/21/2016.
 */
public class FragmentLogin extends Fragment implements View.OnClickListener, ISocialIntegrationCallBacks {

    private Button btnSignUp, btnSkip, btnOk;
    private EditText txtUserName;
    private EditText txtPassword;
    private AlertDialog mNoNetworkDialog;

    private AbstractSocialIntegrator mSocialIntegrator;

    public FragmentLogin() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        btnSkip = (Button) view.findViewById(R.id.btnSkip);
        btnOk = (Button) view.findViewById(R.id.btnOk);
        txtUserName = (EditText) view.findViewById(R.id.txtEmail);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);


        btnSignUp.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignUp) {
            startActivity(new Intent(getActivity(), ActivitySignUp.class));
        } else if (v.getId() == R.id.btnSkip) {
            startActivity(new Intent(getActivity(), ActivityUserHome.class));
        } else if (v.getId() == R.id.btnOk) {
            String userName = txtUserName.getText().toString();
            String password = txtPassword.getText().toString();

            if (userName.equals("") || password.equals("")) {
                M.showAlert(getActivity(), "LOGIN", "Username and password required", "OK",
                        null, null, null, false);
                return;
            }

            if (!NetworkManager.isNetworkAvailable(getActivity())) {
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.extra_network_not_available, null);
                Button retry = (Button) dialogView.findViewById(R.id.btnRetry);
                retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (NetworkManager.isNetworkAvailable(getActivity())) {
                            if (mNoNetworkDialog != null) {
                                mNoNetworkDialog.dismiss();
                            }
                        }
                    }
                });

                mNoNetworkDialog = new AlertDialog.Builder(getActivity(), R.style.Birthday_dialog_style)
                        .setView(dialogView)
                        .setCancelable(false)
                        .show();
                return;

            }

            new LoginRequest(getActivity(), userName, password);
            UiHelper.getInstance().toggleViewEnabled(false, new View[]{btnOk, btnSignUp, btnSkip});
        } else if (v.getId() == R.id.btnFacebookLogin) {
            if (mSocialIntegrator == null)
                mSocialIntegrator = new FacebookHelper(this, getActivity());
            mSocialIntegrator.authenticate(this);//pass parameters
        } else if (v.getId() == R.id.btnTwitterLogin) {
            if (mSocialIntegrator == null)
                mSocialIntegrator = new TwitterHelper(this, getActivity());
            mSocialIntegrator.authenticate(this);//pass parameters
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Keep
    @Subscribe
    public void onLogin(LoginResponse loginResponse) {
        UiHelper.getInstance().toggleViewEnabled(true, new View[]{btnSignUp, btnSkip, btnOk});
        if (loginResponse.getStatus() == 1) {//logged in successfully
            AuthManager.getInstance().setUserLoggedIn(getActivity(), loginResponse);
            Intent homeIntent = new Intent(getActivity(), ActivityUserHome.class);
            homeIntent.putExtra("response", loginResponse);
            homeIntent.putExtra("res", "no");
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getActivity().finish();
            startActivity(homeIntent);
        } else if (loginResponse.getId().equals("0")) {
            M.showAlert(getActivity(), "LOGIN", loginResponse.message, "OK",
                    null, null, null, false);
            M.log("Login error", loginResponse.message);
        } else {
            M.showAlert(getActivity(), "LOGIN", "Invalid username or password.", "OK",
                    null, null, null, false);
        }

    }

    @Keep
    @Subscribe
    public void onError(ErrorEvent errorEvent) {
        UiHelper.getInstance().toggleViewEnabled(true, new View[]{btnSignUp, btnSkip, btnOk});
        M.showAlert(getActivity(), "LOGIN", errorEvent.errorMessage, "OK", null, null, null, false);
    }

    @Override
    public void onAuthenticationSuccess() {

    }

    @Override
    public void onAuthenticationFailure() {

    }

    @Override
    public void onAuthenticationDenied() {

    }
}

