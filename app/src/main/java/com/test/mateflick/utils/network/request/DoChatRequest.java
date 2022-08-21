package com.test.mateflick.utils.network.request;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xtron005 on 28-06-2016.
 */
public class DoChatRequest extends AbstractRequest implements Callback<String> {


    public DoChatRequest(Context mContext, String senderId,String receiverId, String msg) {
        super(mContext);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Calendar c = Calendar.getInstance();

        //RequestBody message = RequestBody.create(MediaType.parse("text/plain"), msg);
        //RequestBody date = RequestBody.create(MediaType.parse("text/plain"), dateFormat.format(c.getTime()));
        //RequestBody time = RequestBody.create(MediaType.parse("text/plain"), timeFormat.format(c.getTime()));


        Call<String> responseCall =
                mBikeNetworkInterface.doChat(senderId,receiverId,
                        dateFormat.format(c.getTime()),timeFormat.format(c.getTime()),msg);
        responseCall.enqueue(this);
    }



    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response.body());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject==null){
            sendError();
            return;
        }
        String id = jsonObject.optString("conversation_id");
        EventBus.getDefault().post(id);

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        //sendError();
    }
}
