package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.network.request.response.LoginResponse;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class LoginRequest extends AbstractRequest implements Callback<LoginResponse> {


    public LoginRequest(Context mContext, String email, String password) {
        super(mContext);
        Call<LoginResponse> responseCall = mBikeNetworkInterface.login(email, password);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        LoginResponse loginResponse = response.body();
        if (loginResponse != null) {
            if (loginResponse.getId() == null) {
                EventBus.getDefault().post(new ErrorEvent("Invalid Username or Password"));
            } else {
                EventBus.getDefault().post(loginResponse);
            }
        } else {
            String errorBody = null;
            try {
                errorBody = response.errorBody().string();
                JSONObject main = new JSONObject(errorBody);
                int id = main.optInt("_id", 0);
                String message = main.optString("message", "");
                LoginResponse loginErrorResponse = new LoginResponse();
                loginErrorResponse.setId(id + "");
                loginErrorResponse.message = message;
                loginErrorResponse.setStatus(0);
                EventBus.getDefault().post(loginErrorResponse);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent("Network error occurred, please try again later"));
    }
}
