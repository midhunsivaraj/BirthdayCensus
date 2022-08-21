package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.network.request.response.GetMyEventsResponse;
import com.test.mateflick.utils.network.request.response.model.Event;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class GetMyEventsRequest extends AbstractRequest implements Callback<GetMyEventsResponse> {


    public GetMyEventsRequest(Context mContext, String userId) {
        super(mContext);
        Call<GetMyEventsResponse> responseCall = mBikeNetworkInterface.getMyEvents(userId);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<GetMyEventsResponse> call, Response<GetMyEventsResponse> response) {
        if (response.body() != null){
            EventBus.getDefault().post(response.body());
        }
    }

    @Override
    public void onFailure(Call<GetMyEventsResponse> call, Throwable t) {

    }
}
