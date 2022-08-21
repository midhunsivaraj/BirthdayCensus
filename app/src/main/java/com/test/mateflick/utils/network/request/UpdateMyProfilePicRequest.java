package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.network.request.response.UpdateProfilePicResponse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class UpdateMyProfilePicRequest extends AbstractRequest implements Callback<UpdateProfilePicResponse> {


    public UpdateMyProfilePicRequest(Context mContext, String userId, File image) {
        super(mContext);
        RequestBody img = RequestBody.create(MediaType.parse("image/*"), image.getAbsoluteFile());
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", image.getName(), img);
        Call<UpdateProfilePicResponse> responseCall =
                mBikeNetworkInterface.updateMyProfilePic(userId, imagePart);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<UpdateProfilePicResponse> call, Response<UpdateProfilePicResponse> response) {
        UpdateProfilePicResponse updateProfilePicResponse = response.body();
        if (updateProfilePicResponse != null) {
            EventBus.getDefault().post(updateProfilePicResponse);
        } else {
            sendError();
        }
    }

    @Override
    public void onFailure(Call<UpdateProfilePicResponse> call, Throwable t) {
        sendError();
    }
}
