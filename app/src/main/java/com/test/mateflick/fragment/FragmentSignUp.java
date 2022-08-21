package com.test.mateflick.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.test.mateflick.R;
import com.test.mateflick.activity.ActivityLogin;
import com.test.mateflick.adapter.CountryListAdapter;
import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.helper.PermissionsHelper;
import com.test.mateflick.utils.helper.UiHelper;
import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.network.NetworkManager;
import com.test.mateflick.utils.network.request.RegisterUserRequest;
import com.test.mateflick.utils.network.request.response.UserRegistrationResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FragmentSignUp extends Fragment implements
        CountryListAdapter.ISelectCountry, DatePickerDialog.OnDateSetListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    @BindView(R.id.imgForward)
    ImageButton mImgForward;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.btnFacebookLogin)
    ImageButton mBtnFacebookLogin;
    @BindView(R.id.btnTwitterLogin)
    ImageButton mBtnTwitterLogin;
    @BindView(R.id.lblLogin)
    TextView mLblLogin;
    @BindView(R.id.txtName)
    EditText mTxtName;
    @BindView(R.id.txtEmail)
    EditText mTxtEmail;
    @BindView(R.id.txtPassword)
    EditText mTxtPassword;
    @BindView(R.id.txtDob)
    EditText mTxtDob;
    @BindView(R.id.btnCountry)
    EditText mBtnCountry;
    @BindView(R.id.btnOkSignup)
    Button mBtnOkSignup;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String selectedCountry;
    private View mCountryDialogView;
    private CountryListAdapter mCountryListAdapter;
    private AlertDialog mAlertDialog;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationRequest mLocationRequest;
    private LocationManager mLocationManager;
    private String userChoosenTask;
    private File imageFile;
    private AlertDialog mNoNetworkDialog;
    private boolean mLocationEnabled;
    private AlertDialog noGpsDialog;
    String name;
    String email;
    String password;
    String dob;
    String country;
    String latitude;
    String longitude;

    public FragmentSignUp() {
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10 * 1000); //10 seconds
        mLocationRequest.setFastestInterval(5 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(10); // 10 METER
    }

    @Keep
    @Subscribe
    public void onUserRegister(UserRegistrationResponse response) {
        if (response.getId().equals("")) {
            M.showAlert(getActivity(), "Register user", response.getMessage(), "OK", null, null, null, false);
        } else {
            //PreferenceManager.saveBooleanPreference(getActivity(), IPreference.KEY_LOGGED_IN, true);
            //PreferenceManager.saveStringPreference(getActivity(), IPreference.KEY_USER_ID, response.getId());
            DialogInterface.OnClickListener positiveClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent loginIntent = new Intent(getActivity(), ActivityLogin.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            };
            String message = "You have successfully registered with username : "
                    + mTxtName.getText().toString() + " Please login.";
            M.showAlert(getActivity(), getString(R.string.app_name), message, "OK", null,
                    positiveClickListener, null, false);
        }

        UiHelper.getInstance().toggleViewEnabled(true, new View[]
                {mBtnFacebookLogin, mBtnTwitterLogin, mBtnOkSignup, mBtnLogin});
    }

    @Keep
    @Subscribe
    public void onError(ErrorEvent event) {
        M.showAlert(getActivity(), getString(R.string.app_name), event.errorMessage, "OK", null,
                null, null, false);
        UiHelper.getInstance().toggleViewEnabled(true, new View[]
                {mBtnFacebookLogin, mBtnTwitterLogin, mBtnOkSignup, mBtnLogin});
    }

    @OnClick({R.id.imgForward, R.id.btnLogin, R.id.btnFacebookLogin, R.id.btnTwitterLogin,
            R.id.btnCountry, R.id.btnOkSignup, R.id.txtDob})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgForward:
                break;
            case R.id.btnLogin:
                getActivity().onBackPressed();
                break;
            case R.id.btnFacebookLogin:
                break;
            case R.id.btnTwitterLogin:
                break;
            case R.id.txtDob:
                showDatePicker();
                break;
            case R.id.btnCountry:
                showCountrySelector();
                break;
            case R.id.btnOkSignup: {
                if (imageFile == null)
                    //galleryIntent();
                    selectImage();
                else
                    register();
                break;
            }
        }
    }

    private void showCountrySelector() {
        mCountryDialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.countries_dialog, null, false);
        mCountryListAdapter = new CountryListAdapter(getActivity());
        mCountryListAdapter.setmISelectCountry(this);


        RecyclerView countryList = (RecyclerView) mCountryDialogView.findViewById(R.id.countriesList);
        countryList.setAdapter(mCountryListAdapter);
        countryList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAlertDialog = new AlertDialog.Builder(getActivity(), R.style.Birthday_dialog_style)
                .setView(mCountryDialogView)
                .setTitle("Select Country")
                .show();
    }

    @Override
    public void setSelectedCountry(String country) {
        selectedCountry = country;
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        mBtnCountry.setText(selectedCountry);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            mLocationEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            mLocationEnabled = false;
        }

        if (!mLocationEnabled)
            noGpsDialog = new AlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .setMessage("GPS is required for completing registration.\nPlease enable GPS by going to settings->Location")
                    .setTitle("GPS NOT ENABLED")
                    .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                if (noGpsDialog != null)
                                    noGpsDialog.dismiss();

                            }
                        }
                    })
                    .show();


    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //M.log("locations: ", mLocation.getLatitude() + "(lat)" + mLocation.getLongitude() + "(long)");
    }

    @Override
    public void onConnectionSuspended(int i) {
        M.log("Google ", "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        M.log("Google ", "Connection failed");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsHelper.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionsHelper.checkStoragePermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                try {
                    onSelectFromGalleryResult(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ivImage.setImageBitmap(thumbnail);
        imageFile = destination;
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) throws FileNotFoundException {


        Uri selectedImage = data.getData();
        String asd = getPath(getActivity(), selectedImage);
        Log.e("img", asd);
        imageFile = new File(asd);

        if (imageFile != null) {
            register();
        } else {
            M.showAlert(getActivity(), getString(R.string.app_name), "Please select an image", "OK", null, null, null, false);
        }


    }
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    private void register() {
        name = mTxtName.getText().toString();
        email = mTxtEmail.getText().toString();
        password = mTxtPassword.getText().toString();
        dob = mTxtDob.getText().toString();
        country = mBtnCountry.getText().toString();
        latitude = "8.96018";
        longitude = "76.6788";
        try {
            latitude = mLocation.getLatitude() + "";
            longitude = mLocation.getLongitude() + "";
        } catch (Exception e) {
        }
        //String latitude = "8.96018";
        //String longitude = "76.6788";


        if (name.equals("") || password.equals("") || dob.equals("") || country.equals("")) {
            M.showAlert(getActivity(), getString(R.string.app_name), "Please fill all fields", "OK",
                    null, null, null, false);
            return;
        }

        if (!NetworkManager.isNetworkAvailable(getActivity())) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.extra_network_not_available
                    , null);
            Button b = (Button) v.findViewById(R.id.btnRetry);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkManager.isNetworkAvailable(getActivity()))
                        if (mNoNetworkDialog != null)
                            mNoNetworkDialog.dismiss();
                }
            });

            mNoNetworkDialog = new AlertDialog.Builder(getActivity(), R.style.Birthday_dialog_style)
                    .setView(v)
                    .setCancelable(false)
                    .show();

            return;

        }
          new RegisterUserRequest(getActivity()).registerUser(name, email, password, country, dob, imageFile, latitude, longitude);
        GetExample example = new GetExample();
        try {
         //   example.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UiHelper.getInstance().toggleViewEnabled(false, new View[]{mBtnFacebookLogin, mBtnTwitterLogin, mBtnOkSignup, mBtnLogin});
    }


    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        M.log("location changed:", mLocation.getLatitude() + " " + mLocation.getLongitude());
    }


    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.setTitle("Select your birthday");
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
                getResources().getConfiguration().locale);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        mTxtDob.setText(dateFormat.format(calendar.getTime()));
    }

    public class GetExample {

        private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

        private final OkHttpClient client = new OkHttpClient();

        public void run() throws Exception {
            // name, email, password, country, dob, imageFile, latitude, longitude
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", name)
                    .addFormDataPart("email", email)
                    .addFormDataPart("password", password)
                    .addFormDataPart("country", country)
                    .addFormDataPart("dob", dob)
                    .addFormDataPart("image",imageFile.getName(),
                            RequestBody.create(MEDIA_TYPE_PNG, imageFile))
                    .addFormDataPart("latitude", latitude)
                    .addFormDataPart("longitude", longitude)
                    .build();

            Request request = new Request.Builder()
                    .url("http://bdc.mod.bz/register")
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
                        Toast.makeText(getActivity(),"Server error! Please try again later.",Toast.LENGTH_LONG).show();
                        throw new IOException("Unexpected code " + response);

                    }

                    String jsonData = response.body().string();
                    System.out.println(jsonData);
                /*    try {
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
                    }*/

                }
            });

        }

    }
}