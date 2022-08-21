package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.network.request.response.ChatItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class GetUnreadMessagesForChatRequest extends AbstractRequest implements Callback<ChatItem[]> {


    public GetUnreadMessagesForChatRequest(Context mContext, String chatId, String readIds) {
        super(mContext);
        Call<ChatItem[]> responseCall = mBikeNetworkInterface.getUnreadMessagesForChat(chatId, readIds);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<ChatItem[]> call, Response<ChatItem[]> response) {

//        EventBus.getDefault().post(response.body().toString());
    }

    @Override
    public void onFailure(Call<ChatItem[]> call, Throwable t) {
        //do something or log error
    }
}
