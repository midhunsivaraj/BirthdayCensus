package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.network.request.response.UpdateNameResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateMyNameRequest extends AbstractRequest implements Callback<UpdateNameResponse> {


    public UpdateMyNameRequest(Context mContext, String userId, String name) {
        super(mContext);
        Call<UpdateNameResponse> responseCall = mBikeNetworkInterface.updateMyName(userId, name);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<UpdateNameResponse> call, Response<UpdateNameResponse> response) {
        UpdateNameResponse updateNameResponse = response.body();
        if (updateNameResponse != null){
            EventBus.getDefault().post(updateNameResponse);
        } else {
            EventBus.getDefault().post(new ErrorEvent("Failed to update name, Please try again later"));
        }
    }

    @Override
    public void onFailure(Call<UpdateNameResponse> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent("Network error occurred, please try again later"));
    }
}
