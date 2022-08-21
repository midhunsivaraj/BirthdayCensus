package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.network.request.response.CreateEventResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateEventRequest extends AbstractRequest implements Callback<CreateEventResponse> {


    public CreateEventRequest(Context mContext, String userId, HashMap<String, String> params) {
        super(mContext);
        Call<CreateEventResponse> responseCall = mBikeNetworkInterface.createEvent(userId, params);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<CreateEventResponse> call, Response<CreateEventResponse> response) {
        CreateEventResponse createEventResponse = response.body();
        if (createEventResponse != null) {
            EventBus.getDefault().post(createEventResponse);
        } else {
            sendError();
        }
    }

    @Override
    public void onFailure(Call<CreateEventResponse> call, Throwable t) {
        sendError();
    }
}
