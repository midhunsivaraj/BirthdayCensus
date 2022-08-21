package com.test.mateflick.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecSelector;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.test.mateflick.R;
import com.test.mateflick.adapter.CommentsListAdapter;
import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.google.android.exoplayer.util.Util.getUserAgent;
import static com.test.mateflick.utils.preference.IPreference.KEY_USER_ID;

public class VideoPlayActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener,ExoPlayer.Listener{



    private final OkHttpClient client = new OkHttpClient();
    private CommentsListAdapter mAdapter;

    Button myButton;
    boolean recording;
    private  String userID,eventID,eventName;
    ImageView dp;
    TextView event,user;
    int vcal = 0;
    String url;
    File fileName;
    VideoView videoview;
    String VideoURL = "nil";
    MediaPlayer mediaPlayer;
    SurfaceHolder surfaceHolder;
    SurfaceView playerSurfaceView;
    RecyclerView discoverList;
    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int BUFFER_SEGMENT_COUNT = 256;
    private static final int RENDERER_COUNT = 3;
    ExoPlayer player = null;
    int stct = 1;
    EditText cstring;
    Button send;
    String cms;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recording = false;

        setContentView(R.layout.activity_videoplayer);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        userID = b.getString("userID");
        eventID = b.getString("eventID");
        eventName = b.getString("eventName");
        System.out.println(userID);
        System.out.println(eventID);
        System.out.println(eventName);
        dp = (ImageView) findViewById(R.id.textView);
        event = (TextView) findViewById(R.id.textView2);
        user = (TextView) findViewById(R.id.textView3);
        user.setText("By "+userID);
        event.setText(eventName);
        discoverList = (RecyclerView) findViewById(R.id.discoverList2);
        cstring = (EditText) findViewById(R.id.editText);
        send = (Button) findViewById(R.id.button2);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cstring.getText().toString().equals("")){
                    cms = cstring.getText().toString();
                    GetExample2 example2 = new GetExample2();
                    try {
                           example2.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cstring.setText("");
                    hideSoftKeyboard(VideoPlayActivity.this);
                }
            }


        });
        loadMarkerIcon();
        GetExample example = new GetExample();
        try {
            example.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerSurfaceView = (SurfaceView)findViewById(R.id.CameraView);
        surfaceHolder = playerSurfaceView.getHolder();
        surfaceHolder.addCallback(this);

        mAdapter = new CommentsListAdapter(VideoPlayActivity.this,eventID);
        discoverList.setAdapter(mAdapter);
        discoverList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //videoview = (VideoView) findViewById(R.id.CameraView);
        myButton = (Button)findViewById(R.id.button_capture);
        myButton.setOnClickListener(myButtonOnClickListener);

    }



    Button.OnClickListener myButtonOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            myButton.setVisibility(View.INVISIBLE);

            startStream();

        }};

    @Override
    protected void onPause() {
        super.onPause();
    }


    public class GetExample2 {


        private final OkHttpClient client = new OkHttpClient();

        public void run() throws Exception {

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("comments", cms)
                    .build();

            Request request = new Request.Builder()
                    .url("http://bdc.mod.bz/comments_events/"+ PreferenceManager.getStringPreference(getApplicationContext(),
                            KEY_USER_ID, "000")+"/"+eventID)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Server error! Please try again later.",Toast.LENGTH_LONG).show();
                        throw new IOException("Unexpected code " + response);

                    }

                    String jsonData = response.body().string();
                    System.out.println(jsonData);

                }
            });

        }

    }


    private void loadMarkerIcon() {
        Glide.with(VideoPlayActivity.this).load("http://s5.favim.com/orig/51/cool-boy-cute-Favim.com-546527.jpg")
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

                dp.setImageBitmap(output);
            }
        });}

    private void startStream() {


        if(stct == 1){

            int gh = 2;
            Log.e("MHGYGH",gh+"");
            if(VideoURL.length()==66){String vl = VideoURL.substring(0,VideoURL.length()-5);VideoURL=vl+gh+".mp4";}
            else if(VideoURL.length()==67){String vl = VideoURL.substring(0,VideoURL.length()-6);VideoURL=vl+gh+".mp4";}
            else{String vl = VideoURL.substring(0,VideoURL.length()-7);VideoURL=vl+gh+".mp4";}
        }
        stct = 0;
        Uri uri = Uri.parse("http://"+VideoURL);

        Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);

        DataSource dataSource = new DefaultUriDataSource(this, null, getUserAgent(this, "ExoPlayerExample"));

        ExtractorSampleSource sampleSource = new ExtractorSampleSource(
                uri, dataSource, allocator, BUFFER_SEGMENT_COUNT * BUFFER_SEGMENT_SIZE);

        MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(
                this, sampleSource, MediaCodecSelector.DEFAULT, MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(
                sampleSource, MediaCodecSelector.DEFAULT);
        player = ExoPlayer.Factory.newInstance(RENDERER_COUNT);
        player.addListener(this);
        player.prepare(videoRenderer, audioRenderer);
        player.sendMessage(videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, surfaceHolder.getSurface());
        player.setPlayWhenReady(true);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.e("aaa","jjolnknkjjknbkhbjhvhjhjhbjh");
        mediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        switch (player.getPlaybackState()) {
            case ExoPlayer.STATE_BUFFERING:

                break;
            case ExoPlayer.STATE_ENDED:
                player.release();
                String as[] = VideoURL.split("/");
                int len = as.length-1;
                String df = as[len];
                String nm = df.substring(0,df.length()-4);
                int gh = Integer.valueOf(nm)+1;
                Log.e("VLASUBC",gh+"");
                if(VideoURL.length()==66){String vl = VideoURL.substring(0,VideoURL.length()-5);VideoURL=vl+gh+".mp4";}
                else if(VideoURL.length()==67){String vl = VideoURL.substring(0,VideoURL.length()-6);VideoURL=vl+gh+".mp4";}
                else{String vl = VideoURL.substring(0,VideoURL.length()-7);VideoURL=vl+gh+".mp4";}
                startStream();

                break;
            case ExoPlayer.STATE_IDLE:

                break;
            case ExoPlayer.STATE_PREPARING:

                break;
            case ExoPlayer.STATE_READY:

                break;
        }
    }

    @Override
    public void onPlayWhenReadyCommitted() {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }


    public class GetExample {
        private final OkHttpClient client = new OkHttpClient();


        public void run() throws Exception {
            Request request = new Request.Builder()
                    .url("http://bdc.mod.bz/get_video_events/"+eventID)
                    //57bed92bde2b1af100346acc
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        M.showAlert(VideoPlayActivity.this, "LIVESTREAM EVENT", "The selected event is not started yet. ",
                                "OK", null, null, null, false);

                        throw new IOException("Unexpected code " + response);
                    }
                    String jsonData = response.body().string();
                    try {
                        //JSONArray Jarray = new JSONArray(jsonData);

                        JSONObject object = new JSONObject(jsonData);
                            //JSONObject object = Jarray.getJSONObject(0);
                            if(object!=null) {
                                VideoURL = "bdc.mod.bz/"+object.get("link").toString();
                            }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            });
        }

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    @Override
    public void onBackPressed() {
        
    }
}