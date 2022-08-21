package com.test.mateflick.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.mateflick.R;
import com.test.mateflick.activity.VideoUploadActivity;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.test.mateflick.utils.preference.IPreference.KEY_USER_ID;

/**
 * Created by Xtron Labs 05 on 6/16/2016.
 */
public class DiscoverListAdapter extends RecyclerView.Adapter<DiscoverListAdapter.DiscoverListViewHolder> {

    private Context mContext;
    ArrayList<String> dateData = new ArrayList<String>();
    ArrayList<String> titleData = new ArrayList<String>();
    ArrayList<String> placeData = new ArrayList<String>();
    ArrayList<String> userID = new ArrayList<String>();
    ArrayList<String> eventID = new ArrayList<String>();
    int dataLength = 0;




    public DiscoverListAdapter(Context mContext) {
        this.mContext = mContext;
        GetExample example = new GetExample();
        try {
            example.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public DiscoverListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiscoverListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DiscoverListViewHolder holder, int position) {

        if(dataLength!=0) {

            String [] titleData1 = titleData.toArray(new String[dataLength]);
            String [] placeData1 = placeData.toArray(new String[dataLength]);
            String [] dateData1 = dateData.toArray(new String[dataLength]);

            holder.title.setText(titleData1[position]);
            holder.place.setText(placeData1[position]);
            holder.time.setText(dateData1[position]);
        }
    }

    @Override
    public int getItemCount() {
        return dataLength;
    }

    public class DiscoverListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @BindView(R.id.notification_title)
        TextView title;
        @BindView(R.id.notification_sub)
        TextView place;
        @BindView(R.id.lblNotificationTime)
        TextView time;



        public DiscoverListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            System.out.println(getAdapterPosition());
            String [] userData1 = userID.toArray(new String[dataLength]);
            String [] eventData1 = eventID.toArray(new String[dataLength]);
            String [] titleData1 = titleData.toArray(new String[dataLength]);

            Intent intent = new Intent(((Activity)mContext), VideoUploadActivity.class);
            intent.putExtra("userID",userData1[getAdapterPosition()]);
            intent.putExtra("eventID",eventData1[getAdapterPosition()]);
            intent.putExtra("eventName",titleData1[getAdapterPosition()]);
            mContext.startActivity(intent);

            }
        }
    public class GetExample {
        private final OkHttpClient client = new OkHttpClient();


        public void run() throws Exception {
            Request request = new Request.Builder()
                    .url("http://bdc.mod.bz/list_event/"+ PreferenceManager.getStringPreference(mContext, KEY_USER_ID, "000"))
                    //57bed92bde2b1af100346acc
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    String jsonData = response.body().string();
                    try {
                        JSONObject Jobject = new JSONObject(jsonData);
                        JSONArray Jarray = Jobject.getJSONArray("Events");

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            System.out.println(object.get("name"));
                            dataLength = Jarray.length();
                            if(object!=null) {
                                String recDT = (object.get("date")).toString() + " " + (object.get("time")).toString();
                                titleData.add((object.get("name")).toString());
                                placeData.add((object.get("place")).toString());
                                dateData.add(recDT);
                                userID.add((object.get("user_id")).toString());
                                eventID.add((object.get("_id")).toString());
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(mContext == null)
                        return;
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        }

    }
}

