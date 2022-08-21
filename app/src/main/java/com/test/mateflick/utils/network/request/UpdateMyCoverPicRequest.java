package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.network.request.response.UpdateCoverResponse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateMyCoverPicRequest extends AbstractRequest implements Callback<UpdateCoverResponse> {


    public UpdateMyCoverPicRequest(Context mContext, String userId, File image) {
        super(mContext);
        RequestBody img = RequestBody.create(MediaType.parse("image/*"), image.getAbsoluteFile());
        MultipartBody.Part imgPart = MultipartBody.Part.createFormData("cover", image.getName(), img);
        Call<UpdateCoverResponse> responseCall = mBikeNetworkInterface.updateMyCover(userId, imgPart);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<UpdateCoverResponse> call, Response<UpdateCoverResponse> response) {
        UpdateCoverResponse updateCoverResponse = response.body();
        if (updateCoverResponse != null) {
            EventBus.getDefault().post(updateCoverResponse);
        } else {
            sendError();
        }
    }

    @Override
    public void onFailure(Call<UpdateCoverResponse> call, Throwable t) {
        sendError();
    }
}
