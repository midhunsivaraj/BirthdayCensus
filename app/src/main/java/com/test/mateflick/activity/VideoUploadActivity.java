package com.test.mateflick.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.test.mateflick.R;
import com.test.mateflick.utils.preference.PreferenceManager;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.test.mateflick.utils.preference.IPreference.KEY_PROFILE_IMAGE;
import static com.test.mateflick.utils.preference.IPreference.KEY_USER_NAME;

public class VideoUploadActivity extends Activity implements MediaRecorder.OnInfoListener{


    private Camera myCamera;
    private MyCameraSurfaceView myCameraSurfaceView;
    private MediaRecorder mediaRecorder;
    private final OkHttpClient client = new OkHttpClient();

    Button myButton;
    SurfaceHolder surfaceHolder;
    boolean recording;
    private  String userID,eventID,eventName;
    ImageView dp;
    TextView event,user;
    int vcal = 0;
    int vcalUp = 1;
    String url;
    File fileName;
    String vcalSt = "Yup";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recording = false;

        setContentView(R.layout.camera_upload);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        new File(Environment.getExternalStorageDirectory().toString()+"/Mateflick").mkdirs();

        Log.e((getString(R.string.base_url))+PreferenceManager.getStringPreference(VideoUploadActivity.this, KEY_PROFILE_IMAGE, "jjj"),"kkk");
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
        user.setText("By "+PreferenceManager.getStringPreference(VideoUploadActivity.this, KEY_USER_NAME, "jjj"));
        event.setText(eventName);
        loadMarkerIcon();

        //Get Camera for preview
        myCamera = getCameraInstance();
        if(myCamera == null){
            Toast.makeText(VideoUploadActivity.this,
                    "Fail to get Camera",
                    Toast.LENGTH_LONG).show();
        }
        ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_RING,true);
        myCameraSurfaceView = new MyCameraSurfaceView(this, myCamera);
        FrameLayout myCameraPreview = (FrameLayout)findViewById(R.id.CameraView);
        myCameraPreview.addView(myCameraSurfaceView);

        myButton = (Button)findViewById(R.id.button_capture);
        myButton.setOnClickListener(myButtonOnClickListener);

     }

    Button.OnClickListener myButtonOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            startRecording();

        }};
    private  void startRecording()
    {

        try{
            if(recording){
                // stop recording and release camera
                mediaRecorder.stop();  // stop the recording
                releaseMediaRecorder(); // release the MediaRecorder object
                ((AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_RING,false);
                //Exit after saved
                //finish();
                myButton.setText("REC");
                recording = false;

            }else{

                //Release Camera before MediaRecorder start
                releaseCamera();
                prepareMediaRecorder();

                if(!prepareMediaRecorder()){
                    Toast.makeText(VideoUploadActivity.this,
                            "Fail in prepareMediaRecorder()!\n - Ended -",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                mediaRecorder.start();
                recording = true;
                myButton.setText("STOP");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    private Camera getCameraInstance(){
        // TODO Auto-generated method stub
         Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            Log.e("FILE_NAME","DONE SELECTING CAM");
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.e("FILE_NAME",e.toString());
        }
        return c; // returns null if camera is unavailable
    }

    private String getFileName_CustomFormat(){
        String strDate = "0";
        if(vcal==0){strDate = "1";vcal = vcal+1;}
        else{

         //   fileName = new File(Environment.getExternalStorageDirectory().toString()+"/Mateflick/" +vcalUp+".mp4");
         //   url = "http://bdc.mod.bz/upload_video_events/"+userID+"/"+eventID;
            vcal = vcal+1;
            strDate = String.valueOf(vcal);
            Log.e("FILE_NAME",strDate);
            UpStream ups = new UpStream();
            try {
                ups.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
                //upload("http://bdc.mod.bz/upload_video_events/"+userID+"/"+eventID,fs);


        }
        return strDate;
    }

    private boolean prepareMediaRecorder(){
       // myCamera = getCameraInstance();
        mediaRecorder = new MediaRecorder();
      //  myCamera.unlock();

        try {

            mediaRecorder.setCamera(myCamera);
        }
        catch (Exception e){
            Log.e("VIDEOCAPTURE","Cannot unlock. Null");
        }

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_480P));




        mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().toString()
                                    +"/Mateflick/" + getFileName_CustomFormat() + ".mp4");
        mediaRecorder.setMaxDuration(7000); // Set max duration 60 sec.
        mediaRecorder.setMaxFileSize(50000000); // Set max file size 50M
        mediaRecorder.setOnInfoListener(this);

        mediaRecorder.setPreviewDisplay(myCameraSurfaceView.getHolder().getSurface());


        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            releaseMediaRecorder();
            return false;
        }
        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder(){
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = new MediaRecorder();
            try{
            myCamera.lock(); }
            catch(Exception e){
                Log.e("VIDEOCAPTURE","Cannot lock. Null");
            }
        }
    }

    private void releaseCamera(){
        if (myCamera != null){
            myCamera.release();        // release the camera for other applications
            myCamera = null;
        }
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {

        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
            Log.e("VIDEOCAPTURE","Maximum Duration Reached");
            releaseMediaRecorder();
            releaseCamera();
            prepareMediaRecorder();
            mediaRecorder.start();
            recording = true;
            //mr.stop();
        }
    }

    public class MyCameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

        private SurfaceHolder mHolder;
        private Camera mCamera;

        public MyCameraSurfaceView(Context context, Camera camera) {
            super(context);
            mCamera = camera;

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
            // deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int weight,
                                   int height) {
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.

            if (mHolder.getSurface() == null){
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                mCamera.stopPreview();
            } catch (Exception e){
                // ignore: tried to stop a non-existent preview
            }

            mCamera.setDisplayOrientation(90);
            // make any resize, rotate or reformatting changes here

            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();

            } catch (Exception e){
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // TODO Auto-generated method stub
            // The Surface has been created, now tell the camera where to draw the preview.
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // TODO Auto-generated method stub

        }
    }
    private void loadMarkerIcon() {
        Glide.with(VideoUploadActivity.this).load("http://s5.favim.com/orig/51/cool-boy-cute-Favim.com-546527.jpg")
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


                //BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(output);
                // marker.setIcon(icon);
                dp.setImageBitmap(output);
            }
        });}



public class UpStream{

    public void run() throws Exception {

        if(vcalSt.equals("Yup")){
            vcalSt="Nop";

        fileName = new File(Environment.getExternalStorageDirectory().toString()+"/Mateflick/" +vcalUp+".mp4");
        url = "http://bdc.mod.bz/upload_video_events/"+userID+"/"+eventID;

        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("video", fileName.getName(),
                        RequestBody.create(MediaType.parse("text/plain"), fileName))
                .build();

        Request request = new Request.Builder().url(url).post(formBody).build();

        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                e.printStackTrace();
                                            }

                                            @Override
                                            public void onResponse(Call call, final Response response) throws IOException {
                                                if (!response.isSuccessful()) {
                                                    vcalSt="Yup";
                                                    throw new IOException("Unexpected code " + response);
                                                }
                                            else{
                                                    vcalSt="Yup";
                                                    vcalUp = vcalUp+1;
                                                    System.out.println(response);
                                                }
                                            }});
    }
}}
}