package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.network.request.response.UpdateAboutResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class UpdateMyAboutRequest extends AbstractRequest implements Callback<UpdateAboutResponse> {


    public UpdateMyAboutRequest(Context mContext, String userId, String name) {
        super(mContext);
        Call<UpdateAboutResponse> responseCall = mBikeNetworkInterface.updateMyAbout(userId, name);
        responseCall.enqueue(this);
    }



    @Override
    public void onResponse(Call<UpdateAboutResponse> call, Response<UpdateAboutResponse> response) {
        UpdateAboutResponse aboutResponse = response.body();
        EventBus.getDefault().post(aboutResponse);
        /*if (aboutResponse != null) {
            EventBus.getDefault().post(aboutResponse);
        } else {
            M.showAlert(mContext, "Update About", "Network error occurred", "OK",
                    null, null, null, false);
        }*/
    }

    @Override
    public void onFailure(Call<UpdateAboutResponse> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent("Network error occurred.Please try again later"));
    }
}
