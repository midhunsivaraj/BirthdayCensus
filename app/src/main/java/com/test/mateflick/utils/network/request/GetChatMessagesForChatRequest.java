package com.test.mateflick.utils.network.request;

import android.content.Context;

import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.network.request.response.ChatItem;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetChatMessagesForChatRequest extends AbstractRequest implements Callback<ChatItem[]> {


    public GetChatMessagesForChatRequest(Context mContext, String chatId) {
        super(mContext);
        Call<ChatItem[]> responseCall = mBikeNetworkInterface.getChatMessagesForChat(chatId);
        responseCall.enqueue(this);
    }


    @Override
    public void onResponse(Call<ChatItem[]> call, Response<ChatItem[]> response) {
        if (response.body() != null)
            EventBus.getDefault().post(response.body());
    }

    @Override
    public void onFailure(Call<ChatItem[]> call, Throwable t) {
        M.log("Chat Response", t.getMessage());
    }
}
