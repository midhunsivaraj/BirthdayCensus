package com.test.mateflick.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.mateflick.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Xtron Labs 05 on 6/16/2016.
 */
public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentsListViewHolder> {

    private Context mContext;
    ArrayList<String> dateData = new ArrayList<String>();
    ArrayList<String> titleData = new ArrayList<String>();
    ArrayList<String> textData = new ArrayList<String>();
    ArrayList<String> userID = new ArrayList<String>();
    ArrayList<String> eventID = new ArrayList<String>();
    int dataLength = 0;
    String eventID2;
    Timer myTimer;
    int ttck = 1;




    public CommentsListAdapter(Context mContext, String ev) {
        this.mContext = mContext;
        eventID2=ev;

        myTimer = new Timer("MyTimer", true);
        myTimer.scheduleAtFixedRate(new MyTask(), 1000, 5000);
        Log.e("MYTiMMERR","AAAA");


    }

    @Override
    public CommentsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentsListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final CommentsListViewHolder holder, int position) {

        if(dataLength!=0) {

            String [] titleData1 = titleData.toArray(new String[dataLength]);
            String [] textData1 = textData.toArray(new String[dataLength]);
            String [] dateData1 = userID.toArray(new String[dataLength]);

            holder.title.setText(titleData1[position]);
            holder.place.setText(textData1[position]);

        /*    Glide.with(mContext).load("https://www.mautic.org/media/images/default_avatar.png")
                    .asBitmap().fitCenter().override(100, 100).into(new SimpleTarget<Bitmap>() {

                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {

                    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                            bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(output);

                    final int color = 0xff424242;
                    final Paint paint = new Paint();
                    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                    paint.setAntiAlias(true);
                    canvas.drawARGB(0, 0, 0, 0);
                    paint.setColor(color);
                    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                            bitmap.getWidth() / 2, paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    canvas.drawBitmap(bitmap, rect, rect, paint);

                    holder.imgProfile.setImageBitmap(output);
                }
            });*/


        }
    }

    @Override
    public int getItemCount() {
        return dataLength;
    }

    public class CommentsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @BindView(R.id.lblChatMesage)
        TextView title;
        @BindView(R.id.commentString)
        TextView place;
        ImageView imgProfile;


        public CommentsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            System.out.println(getAdapterPosition());


            }
        }
    public class GetExample {
        private final OkHttpClient client = new OkHttpClient();


        public void run() throws Exception {
            Request request = new Request.Builder()
                    .url("http://bdc.mod.bz/get_comments_details/"+ eventID2)//PreferenceManager.getStringPreference(mContext, KEY_USER_ID, "000"))
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
                    titleData.clear();
                    textData.clear();

                    String jsonData = response.body().string();
                    try {
                        JSONArray Jarray = new JSONArray(jsonData);

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                           // System.out.println(object.get("name"));
                            dataLength = Jarray.length();
                            if(object!=null) {
                                //String recDT = (object.get("date_created")).toString();
                                titleData.add((object.get("name")).toString());
                                textData.add((object.get("comment")).toString());
                                //dateData.add(recDT);
                                //userID.add((object.get("image")).toString());
                                //System.out.println(object.get("image"));

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
    class MyTask extends TimerTask {

        public void run(){
            Log.e("MYTiMMERR","SSS");
            ttck = ttck+1;
            if(ttck>60){
                myTimer.cancel();
            }
            else{
            GetExample example = new GetExample();
            try {
                example.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}

    }

}

