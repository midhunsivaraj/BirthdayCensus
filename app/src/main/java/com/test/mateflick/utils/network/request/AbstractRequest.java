package com.test.mateflick.utils.network.request;


import android.content.Context;

import com.test.mateflick.R;
import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.network.BirthDayNetworkInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public abstract class AbstractRequest {

    protected final String BASE_URL;
    protected final Context mContext;

    protected Retrofit mRetrofit;
    protected BirthDayNetworkInterface mBikeNetworkInterface;

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build();

    public AbstractRequest(Context mContext) {
        this.BASE_URL = mContext.getResources().getString(R.string.base_url);
        this.mContext = mContext;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        mBikeNetworkInterface = mRetrofit.create(BirthDayNetworkInterface.class);
    }


    protected void sendError(String errorMessage) {
        EventBus.getDefault().post(new ErrorEvent(errorMessage));
    }

    protected void sendError() {
        sendError("Network error occurred. Please try again.");
    }

}
