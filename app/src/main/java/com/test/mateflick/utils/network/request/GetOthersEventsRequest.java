package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.network.request.response.model.Event;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class GetOthersEventsRequest extends AbstractRequest implements Callback<Event[]> {


    public GetOthersEventsRequest(Context mContext, String userId) {
        super(mContext);
        Call<Event[]> responseCall = mBikeNetworkInterface.getOthersEvents(userId);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<Event[]> call, Response<Event[]> response) {

    }

    @Override
    public void onFailure(Call<Event[]> call, Throwable t) {

    }
}
