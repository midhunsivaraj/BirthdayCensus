package com.test.mateflick.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.mateflick.R;
import com.test.mateflick.utils.network.request.SortUserByDistanceRequest;
import com.test.mateflick.utils.network.request.response.model.User;
import com.test.mateflick.utils.preference.IPreference;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.test.mateflick.R.id.map;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 */
public class ActivityBirthmates extends AppCompatActivity implements OnMapReadyCallback {

    private User[] mUsers;
    String latitude, longitude;
    GoogleMap maap;
    Bitmap bitmap = null;
    String imgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_discover_birthmates);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        latitude = PreferenceManager.getStringPreference(getApplicationContext(), IPreference.KEY_LAST_KNOWN_LATITUDE, "");
        longitude = PreferenceManager.getStringPreference(getApplicationContext(), IPreference.KEY_LAST_KNOWN_LONGITUDE, "");
       // Log.e("LATLON",latitude+"  "+longitude);
        String userID = PreferenceManager.getStringPreference(getApplicationContext(), IPreference.KEY_USER_ID, "");

        new SortUserByDistanceRequest(getApplicationContext(), userID, latitude, longitude);
    }

    @Override
    public void onMapReady(GoogleMap map) {
       // map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng HAMBURG = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        Log.e("DEFAULT_COORDS",HAMBURG.toString());
        maap=map;
        UiSettings settings = map.getUiSettings();
        settings.setZoomControlsEnabled(false);
        settings.setCompassEnabled(false);
        settings.setRotateGesturesEnabled(true);
        settings.setScrollGesturesEnabled(true);
        settings.setTiltGesturesEnabled(true);
        settings.setZoomGesturesEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

        map.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Intent ii = new Intent(ActivityBirthmates.this, ActivityUserHome.class);
                ii.putExtra("mapuid", marker.getSnippet());
                ii.putExtra("res", "yes");
                ii.putExtra("where", "map");
                startActivity(ii);
                Log.e("MAP_INFOCLICK",marker.getSnippet());
            }
        });

    }

    @Subscribe
    public void showUsers(User[] users) {
        mUsers = users;
        if (users == null) return;
        if (users.length <= 0) return;
        if (maap == null) return;
        new LoadUsersAsync().execute(users);
    }


    class LoadUsersAsync extends AsyncTask<User[], User, Void> {

        @Override
        protected Void doInBackground(User[]... params) {
            User[] users = params[0];
            for (int i = 0; i < users.length; i++) {
                publishProgress(users[i]);
            }

            return null;
        }


        @Override
        protected void onProgressUpdate(User... values) {
            super.onProgressUpdate(values);

            Log.e("NAME_USER",values[0].getName());
            Log.e("IMAGE_USER",values[0].getId());
            imgURL = ("http://bdc.mod.bz/"+values[0].getImage());
            if (maap != null) {
                LatLng latLang = new LatLng(
                        Double.parseDouble(values[0].getLatitude()),
                        Double.parseDouble(values[0].getLongitude())
                );

                MarkerOptions markerOptions = new MarkerOptions().position(latLang);
                                                markerOptions.title(values[0].getName());
                                                markerOptions.snippet(values[0].getId());
                Marker marker = maap.addMarker(markerOptions);
                loadMarkerIcon(marker);
            }

        }
    }
    private void loadMarkerIcon(final Marker marker) {
        Glide.with(this).load(imgURL)
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


                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(output);
                marker.setIcon(icon);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }


}