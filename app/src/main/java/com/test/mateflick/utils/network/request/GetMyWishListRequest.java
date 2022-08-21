package com.test.mateflick.utils.network.request;

import android.content.Context;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class GetMyWishListRequest extends AbstractRequest implements Callback<ResponseBody> {


    public GetMyWishListRequest(Context mContext, String myId) {
        super(mContext);
        Call<ResponseBody> responseCall = mBikeNetworkInterface.getMyWishLists(myId);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
