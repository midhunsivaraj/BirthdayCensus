package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.network.request.response.CreateWishListResponse;

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
public class CreateWishListRequest extends AbstractRequest implements Callback<CreateWishListResponse> {


    //text,heading,link,latitude,longitude,image
    public CreateWishListRequest(Context mContext, String userId, String text, String heading,
                                 String link, String latitude, String longitude, File image) {
        super(mContext);
        //Call<ResponseBody> responseCall = mBikeNetworkInterface.createWishList(userId, params);
        //responseCall.enqueue(this);

        RequestBody mText = RequestBody.create(MediaType.parse("text/plain"), text);
        RequestBody mHeading = RequestBody.create(MediaType.parse("text/plain"), heading);
        RequestBody mLink = RequestBody.create(MediaType.parse("text/plain"), link);
        RequestBody mLatitude = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody mLongitude = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody mImage = RequestBody.create(MediaType.parse("image/*"), image.getAbsoluteFile());
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", image.getName(), mImage);

        Call<CreateWishListResponse> responseCall = mBikeNetworkInterface.createWishList(userId,
                mText, mHeading,mLink,mLatitude,mLongitude,imagePart );
        responseCall.enqueue(this);

    }


    @Override
    public void onResponse(Call<CreateWishListResponse> call, Response<CreateWishListResponse> response) {
        CreateWishListResponse createWishListResponse = response.body();
        if (createWishListResponse != null) {
            EventBus.getDefault().post(createWishListResponse);
        } else {
            sendError();
        }
    }

    @Override
    public void onFailure(Call<CreateWishListResponse> call, Throwable t) {
        sendError();
    }
}
