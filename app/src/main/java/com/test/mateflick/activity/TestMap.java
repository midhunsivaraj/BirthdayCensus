package com.test.mateflick.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.mateflick.R;
import com.test.mateflick.utils.network.request.SortUserByDistanceRequest;
import com.test.mateflick.utils.network.request.response.model.User;
import com.test.mateflick.utils.preference.IPreference;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Xtron Labs 05 on 3/28/2016.
 */
public class TestMap extends SupportMapFragment implements OnMapReadyCallback {


    String latitude, longitude;

    private LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);

    private GoogleMap mGoogleMap;
    private User[] mUsers;
    private boolean mLoadedByMap, mLoadedBySubscriber;

    public TestMap() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover_birthmates, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        Log.e("MAP_00","ALMOST");
        super.onActivityCreated(bundle);
        latitude = PreferenceManager.getStringPreference(getActivity(), IPreference.KEY_LAST_KNOWN_LATITUDE, "");
        longitude = PreferenceManager.getStringPreference(getActivity(), IPreference.KEY_LAST_KNOWN_LONGITUDE, "");
        String userID = PreferenceManager.getStringPreference(getActivity(), IPreference.KEY_USER_ID, "");

        new SortUserByDistanceRequest(getActivity(), userID, latitude, longitude);

        /*if (latitude.equals("")|| longitude.equals("")) {
            M.showAlert(getActivity(), getString(R.string.app_name), "Unable to get location, " +
                    "Please enable location and try again",
                    "OK", null, null, null, false);
            return;
        }*/

        //  if (mGoogleMap == null) {

        //     Log.e("MAP_NULL","ALMOST");

        SupportMapFragment m = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        m.getMapAsync(this);

        //  }

        //  getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("MAP_01","ALMOST");
        mGoogleMap = googleMap;
        //if (mLoadedBySubscriber) return;
        Marker hamburg = googleMap.addMarker(new MarkerOptions().position(HAMBURG)
                .title("Hamburg"));
        Marker kiel = googleMap.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.propic)));
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings settings = mGoogleMap.getUiSettings();
        settings.setZoomControlsEnabled(false);
        settings.setCompassEnabled(false);
        settings.setRotateGesturesEnabled(true);
        settings.setScrollGesturesEnabled(true);
        settings.setTiltGesturesEnabled(true);
        settings.setZoomGesturesEnabled(true);


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    @Subscribe
    public void showUsers(User[] users) {
        Log.e("MAP_03","ALMOST");
        mUsers = users;
        if (users == null) return;
        if (users.length <= 0) return;
        if (mGoogleMap == null) return;
        if (mLoadedByMap) return;
        new LoadUsersAsync().execute(users);
    }


    class LoadUsersAsync extends AsyncTask<User[], User, Void> {

        @Override
        protected Void doInBackground(User[]... params) {
            User[] users = params[0];
            for (int i = 0; i < users.length; i++) {
                publishProgress(users[i]);
                Log.e("MAP_04","ALMOST");
            }

            return null;
        }


        @Override
        protected void onProgressUpdate(User... values) {
            super.onProgressUpdate(values);
            if (mGoogleMap != null) {
                LatLng latLang = new LatLng(
                        Double.parseDouble(values[0].getLatitude()),
                        Double.parseDouble(values[0].getLongitude())
                );
                mGoogleMap.addMarker(new MarkerOptions().position(latLang)
                        .title(values[0].getName()));
            }
        }
    }

/*

package com.test.mateflick.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.test.mateflick.R;


    public class VideoUploadActivity extends Activity {

        String userID;
        String eventID;
        private android.graphics.Camera mCamera;
        private CameraPreview mPreview;
        private MediaRecorder mediaRecorder;
        private Button capture, switchCamera;
        private Context myContext;
        private LinearLayout cameraPreview;
        private boolean cameraFront = false;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.camera_upload);

            Bundle b = new Bundle();
            b = getIntent().getExtras();
            userID = b.getString("userID");
            eventID = b.getString("eventID");

 */


}
