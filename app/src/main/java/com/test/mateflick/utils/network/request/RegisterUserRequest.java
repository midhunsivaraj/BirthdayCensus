package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.R;
import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.network.request.response.UserRegistrationResponse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Xtron005 on 27-06-2016.
 */
public class RegisterUserRequest extends AbstractRequest implements Callback<UserRegistrationResponse> {

    public RegisterUserRequest(Context mContext) {
        super(mContext);
    }

    /*public void registerUser(HashMap<String, String> params, File file) {
        RequestBody image = RequestBody.create(MediaType.parse("image"), file.getAbsoluteFile());
        Call<UserRegistrationResponse> responseCall = mBikeNetworkInterface.registerUser(params, image);
        responseCall.enqueue(this);
    }*/


    public void registerUser(String name, String email, String password, String country, String dob,
                             File file, String latitude, String longitude) {
        RequestBody image = RequestBody.create(MediaType.parse("image/*"), file.getAbsoluteFile());
        MultipartBody.Part requestBody = MultipartBody.Part.createFormData("image", file.getName(), image);
        RequestBody mName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody mEmail = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody mPassword = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody mDob = RequestBody.create(MediaType.parse("text/plain"), dob);
        RequestBody mCountry = RequestBody.create(MediaType.parse("text/plain"), country);
        RequestBody mLatitude = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody mLongitude = RequestBody.create(MediaType.parse("text/plain"), longitude);
        Call<UserRegistrationResponse> responseCall = mBikeNetworkInterface.registerUser(
                mName, mEmail, mPassword, mDob, mCountry,requestBody, mLatitude, mLongitude);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {
        UserRegistrationResponse userRegistrationResponse = response.body();
        if (userRegistrationResponse != null) {//request send successfully and got a valid response
            if (userRegistrationResponse.getId() == null)//registration failed
                EventBus.getDefault().post(new ErrorEvent(userRegistrationResponse.getMessage()));
            else // registration successful
                EventBus.getDefault().post(userRegistrationResponse);
        } else {    //Either the request failed or no valid response
            sendErrorMessage();
        }
    }

    private void sendErrorMessage() {
        if (mContext != null)
            EventBus.getDefault().post(new ErrorEvent(mContext.getResources().getString(R.string.network_error_message)));
    }

    @Override
    public void onFailure(Call<UserRegistrationResponse> call, Throwable t) {
        sendErrorMessage();
    }
}
