package com.test.mateflick.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.test.mateflick.R;
import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.helper.FileSystemHelper;
import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.network.NetworkManager;
import com.test.mateflick.utils.network.request.UpdateMyCoverPicRequest;
import com.test.mateflick.utils.network.request.UpdateMyNameRequest;
import com.test.mateflick.utils.network.request.UpdateMyProfilePicRequest;
import com.test.mateflick.utils.network.request.response.LoginResponse;
import com.test.mateflick.utils.network.request.response.UpdateCoverResponse;
import com.test.mateflick.utils.network.request.response.UpdateProfilePicResponse;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Xtron Labs 05 on 3/25/2016.
 */
public class FragmentNewUserProfile extends BaseFragment implements
        CompoundButton.OnCheckedChangeListener, View.OnLongClickListener {

    private static final int SELECT_FILE = 1;
    private static final int REQUEST_COVER = 2;
    private static final int REQUEST_PROFILE = 3;

    private LoginResponse mLoginResoponse;

    private File mCoverImage;
    private File mProfileImage;
    private Uri mProPicPath, mCoverPath;

    private RadioButton rdoAbout, rdoWishlist, rdoFriends;
    private CircleImageView mImgProPic;
    private ImageView mImgCoverImage;
    private TextView mLblUserName;

    private ImageButton mBtnEditCover;

    private AlertDialog mNameEditDialog;
    private AlertDialog mNoNetworkDialog;

    public FragmentNewUserProfile() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLoginResoponse = getArguments().getParcelable("response");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_user_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setHasOptionsMenu(true);
        Activity activity = getActivity();
        rdoAbout = (RadioButton) activity.findViewById(R.id.rdoAbout);
        //rdoWishlist = (RadioButton) activity.findViewById(R.id.rdoWishlist);
        rdoFriends = (RadioButton) activity.findViewById(R.id.rdoFriends);

        rdoAbout.setOnCheckedChangeListener(this);
        //rdoWishlist.setOnCheckedChangeListener(this);
        rdoFriends.setOnCheckedChangeListener(this);

        mImgCoverImage = (ImageView) activity.findViewById(R.id.imgCoverImage);
        mImgProPic = (CircleImageView) activity.findViewById(R.id.imgProPic);

        mLblUserName = (TextView) activity.findViewById(R.id.lblUserName2);
        mLblUserName.setOnClickListener(this);

        mImgProPic.setOnLongClickListener(this);
        mImgCoverImage.setOnClickListener(this);

        mBtnEditCover = (ImageButton) activity.findViewById(R.id.btnEditCover);
        mBtnEditCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(REQUEST_COVER);
            }
        });

        rdoAbout.setChecked(true);

        rdoAbout.post(new Runnable() {
            @Override
            public void run() {
                animateToPosition(rdoAbout);
            }
        });


        if (mLoginResoponse != null) {
            downloadProfileImage();
            downloadCoverImage();
            PreferenceManager.saveStringPreference(activity, KEY_COUNTRY, mLoginResoponse.getCountry());
            PreferenceManager.saveStringPreference(activity, KEY_EMAIL, mLoginResoponse.getEmail());
            PreferenceManager.saveStringPreference(activity, KEY_LAST_KNOWN_LATITUDE, mLoginResoponse.getLatitude());
            PreferenceManager.saveStringPreference(activity, KEY_LAST_KNOWN_LONGITUDE, mLoginResoponse.getLongitude());
            PreferenceManager.saveStringPreference(activity, KEY_ABOUT, mLoginResoponse.getAbout());
            PreferenceManager.saveStringPreference(activity, KEY_COVER_NAME, mLoginResoponse.getCover());
            PreferenceManager.saveStringPreference(activity, KEY_PROFILE_IMAGE, mLoginResoponse.getImage());
            PreferenceManager.saveStringPreference(activity, KEY_USER_NAME, mLoginResoponse.getName());

            PreferenceManager.saveBooleanPreference(activity, KEY_LOGGED_IN, true);
        } else {
            try {
                mLoginResoponse = new LoginResponse();
                mLoginResoponse.setImage(PreferenceManager.getStringPreference(activity, KEY_PROFILE_IMAGE, ""));
                mLoginResoponse.setCover(PreferenceManager.getStringPreference(activity, KEY_COVER_NAME, ""));
                downloadCoverImage();
                downloadProfileImage();
            } catch (Exception e) {
                //Ignore
            }
        }
        mLblUserName.setText(PreferenceManager.getStringPreference(getActivity(), KEY_USER_NAME, ""));

    }

    public static FragmentNewUserProfile newInstance(LoginResponse loginResponse) {
        FragmentNewUserProfile instance = new FragmentNewUserProfile();
        Bundle args = new Bundle();
        args.putParcelable("response", loginResponse);
        instance.setArguments(args);
        return instance;
    }

    private void goToChat() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();

        switch (id) {
            case R.id.rdoAbout: {
                if (isChecked) {
                    animateToPosition(rdoAbout);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentHolder, new FragmentUserAbout())
                            .commit();

                }
                break;
            }
            /*case R.id.rdoWishlist: {
                if (isChecked) {
                    animateToPosition(rdoWishlist);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentHolder, new FragmentUserWishList())
                            .commit();
                }
                break;
            }*/
            case R.id.rdoFriends: {
                if (isChecked) {
                    animateToPosition(rdoFriends);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentHolder, new FragmentUserFriends())
                            .commit();
                }
                break;
            }
        }


    }

    @Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.imgProPic: {
                pickImage(REQUEST_PROFILE);
                return true;
            }

            case R.id.lblUserName2: {
                changeUserName();
            }
        }

        return false;
    }

    private void changeUserName() {
        View v = getActivity().getLayoutInflater().inflate(R.layout.extra_edit_name, null);

        final EditText txtName = (EditText) v.findViewById(R.id.txtEditName);
        Button btnOK = (Button) v.findViewById(R.id.btnEditNameOk);
        Button btnCancel = (Button) v.findViewById(R.id.btnEditNameCancel);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtName.getText().toString().equals("")) {
                    txtName.setError(" New name required");
                    return;
                }

                if (!NetworkManager.isNetworkAvailable(getActivity())) {
                    if (mNameEditDialog != null) {
                        mNameEditDialog.dismiss();
                    }
                    View dialogView = getActivity().getLayoutInflater()
                            .inflate(R.layout.extra_network_not_available, null);

                    Button retry = (Button) dialogView.findViewById(R.id.btnRetry);
                    retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!NetworkManager.isNetworkAvailable(getActivity())) return;
                            if (mNoNetworkDialog != null) mNoNetworkDialog.dismiss();
                        }
                    });

                    mNoNetworkDialog = new AlertDialog.Builder(getActivity())
                            .setView(dialogView)
                            .setCancelable(false)
                            .show();
                    return;
                }


                String userId = PreferenceManager.getStringPreference(getActivity(), KEY_USER_ID, "");
                if (userId.equals("")) return;
                new UpdateMyNameRequest(getActivity(), userId, txtName.getText().toString());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNameEditDialog != null)
                    mNameEditDialog.dismiss();
            }
        });

        mNameEditDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Enter New Name")
                .setView(v)
                .setCancelable(false)
                .show();


    }

    private void pickImage(int request) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_COVER: {
                if (data != null)
                    onImageSelected(data, REQUEST_COVER);
                return;
            }
            case REQUEST_PROFILE: {
                if (data != null)
                    onImageSelected(data, REQUEST_PROFILE);
                return;
            }
        }
    }

    private void onImageSelected(Intent data, int requestCode) {

        Uri imageUri = data.getData();
        if (requestCode == REQUEST_COVER) {
            mCoverPath = imageUri;
        } else if (requestCode == REQUEST_PROFILE) {
            mProPicPath = imageUri;
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        // Get the cursor
        Cursor cursor = getActivity().getContentResolver().query(imageUri,
                filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);

        if (requestCode == REQUEST_COVER) {
            mCoverImage = new File(imgDecodableString);
            if (mCoverImage != null) {
                updateCover();
            } else {
                M.showAlert(getActivity(), getString(R.string.app_name),
                        "Please select an image", "OK", null, null, null, false);
            }

        } else if (requestCode == REQUEST_PROFILE) {
            mProfileImage = new File(imgDecodableString);
            if (mProfileImage != null) {
                updateProfilePic();
            } else {
                M.showAlert(getActivity(), getString(R.string.app_name),
                        "Please select an image", "OK", null, null, null, false);
            }
        }

    }

    private void updateProfilePic() {
        String userID = PreferenceManager.getStringPreference(getActivity(), KEY_USER_ID, "");
        if (userID.equals("")) {
            M.log("userid : ", "Empty user id");
            return;
        }
        new UpdateMyProfilePicRequest(getActivity(), userID, mProfileImage);
    }

    private void updateCover() {
        String userID = PreferenceManager.getStringPreference(getActivity(), KEY_USER_ID, "");
        if (userID.equals("")) {
            M.log("userid : ", "Empty user id");
            return;
        }
        new UpdateMyCoverPicRequest(getActivity(), userID, mCoverImage);
    }

    private void setCover() {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()
                    + FileSystemHelper.BASE_DIR
                    + File.separator
                    + FileSystemHelper.IMAGES_FOLDER
                    + File.separator
                    + FileSystemHelper.COVER_IMAGE);
            if (bitmap != null)
                mImgCoverImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setProfilePic() {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()
                    + FileSystemHelper.BASE_DIR
                    + File.separator
                    + FileSystemHelper.IMAGES_FOLDER
                    + File.separator
                    + FileSystemHelper.PROFILE_PIC);
            if (bitmap != null)
                mImgProPic.setImageBitmap(bitmap);
        } catch (Exception e) {
            M.log("ex", e.getMessage());
        }
    }

    private void downloadProfileImage() {
        try {
            Glide.with(getActivity())
                    .load(getString(R.string.base_url) + mLoginResoponse.getImage())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(100, 100) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mImgProPic.setImageBitmap(resource);
                        }
                    });
        } catch (Exception e) {
            //ignore
        }

    }

    private void downloadCoverImage() {
        try {
            Glide.with(getActivity())
                    .load(getString(R.string.base_url) + mLoginResoponse.getCover())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mImgCoverImage.setImageBitmap(resource);
                        }
                    });
        } catch (Exception e) {
            //Ignore
        }
    }

    @Keep
    @Subscribe
    public void onCoverPicUpdate(UpdateCoverResponse response) {
        if (response != null) {
            Toast.makeText(getActivity(), "Updated successfully", Toast.LENGTH_SHORT).show();
            try {
                FileSystemHelper.copyImage(mCoverImage, FileSystemHelper.COVER_IMAGE);
            } catch (IOException e) {
                M.log("ex", e.getMessage());
            }
            //setCover();
            mImgCoverImage.setImageURI(mCoverPath);
        } else {
            M.showAlert(getActivity(), getString(R.string.app_name), "Network error occurred", "OK",
                    null, null, null, false);
        }
    }

    @Keep
    @Subscribe
    public void onProfilePicUpdate(UpdateProfilePicResponse updateProfilePicResponse) {
        if (updateProfilePicResponse != null) {
            Toast.makeText(getActivity(), "Updated successfully", Toast.LENGTH_SHORT).show();
            try {
                FileSystemHelper.copyImage(mProfileImage, FileSystemHelper.PROFILE_PIC);
            } catch (IOException e) {
                M.log("ex", e.getMessage());
            }
            //setProfilePic();
            mImgProPic.setImageURI(mProPicPath);
        } else {
            M.showAlert(getActivity(), getString(R.string.app_name), "Network error occurred", "OK",
                    null, null, null, false);
        }
    }

    @Keep
    @Subscribe
    public void onError(ErrorEvent error) {
        M.showAlert(getActivity(), getString(R.string.app_name), error.errorMessage, "OK",
                null, null, null, false);
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
}
