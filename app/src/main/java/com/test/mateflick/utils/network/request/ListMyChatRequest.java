package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.network.request.response.GetMyChatsResponse;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class ListMyChatRequest extends AbstractRequest implements Callback<GetMyChatsResponse[]> {


    public ListMyChatRequest(Context mContext, String myId) {
        super(mContext);
        Call<GetMyChatsResponse[]> responseCall = mBikeNetworkInterface.listMyChat(myId);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<GetMyChatsResponse[]> call, Response<GetMyChatsResponse[]> response) {
        GetMyChatsResponse[] arr = response.body();
        if (arr != null){
            EventBus.getDefault().post(arr);
            return;
        }
        sendError();
    }

    @Override
    public void onFailure(Call<GetMyChatsResponse[]> call, Throwable t) {
        sendError();
    }
}
