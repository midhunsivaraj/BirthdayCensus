package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.network.request.response.model.User;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class SortUserByDistanceRequest extends AbstractRequest implements Callback<User[]> {


    public SortUserByDistanceRequest(Context mContext, String userId, String latitude, String longitude) {
        super(mContext);
        Call<User[]> responseCall = mBikeNetworkInterface.sortUserByDistance(userId, latitude, longitude);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<User[]> call, Response<User[]> response) {
        EventBus.getDefault().post(response.body());
    }

    @Override
    public void onFailure(Call<User[]> call, Throwable t) {
        M.log("User list", "error : " + t.getMessage());
    }
}
