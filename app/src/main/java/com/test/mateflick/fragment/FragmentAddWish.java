package com.test.mateflick.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.mateflick.R;
import com.test.mateflick.activity.ActivityUserHome;
import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.network.request.CreateWishListRequest;
import com.test.mateflick.utils.network.request.response.CreateWishListResponse;
import com.test.mateflick.utils.preference.IPreference;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xtron Labs 05 on 6/17/2016.
 */
public class FragmentAddWish extends Fragment {


    private static final int SELECT_FILE = 1;
    private File mSelectedImage;


    @BindView(R.id.imgTabSelector)
    ImageView imgTabSelector;
    @BindView(R.id.imgToggleNavigation)
    ImageButton imgToggleNavigation;
    @BindView(R.id.txtEventName)
    EditText txtEventName;
    @BindView(R.id.txtEventHeading)
    EditText txtEventHeading;
    @BindView(R.id.txtEventLink)
    EditText txtEventLink;
    @BindView(R.id.btnEventOk)
    Button btnEventOk;

    String latitude = "8.96018";
    String longitude = "76.6788";

    public FragmentAddWish() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_wish, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.imgToggleNavigation, R.id.btnEventOk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgToggleNavigation:
                Intent homeIntent = new Intent(getActivity(), ActivityUserHome.class);
                getActivity().finish();
                startActivity(homeIntent);
                break;
            case R.id.btnEventOk: {
                if (mSelectedImage == null) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                    return;
                }

                createWish();
                break;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Uri imageUri = data.getData();

        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        // Get the cursor
        Cursor cursor = getActivity().getContentResolver().query(imageUri,
                filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);


        mSelectedImage = new File(imgDecodableString);


        if (mSelectedImage != null) {
            createWish();
        } else {
            M.showAlert(getActivity(), getString(R.string.app_name), "Please select an image", "OK",
                    null, null, null, false);
        }


    }


    private void createWish() {
        String text = txtEventName.getText().toString();
        String heading = txtEventHeading.getText().toString();
        String link = txtEventLink.getText().toString();
        String userId = PreferenceManager.getStringPreference(getActivity(),
                IPreference.KEY_USER_ID, "");
        new CreateWishListRequest(getActivity(), userId, text, heading, link,
                latitude, longitude, mSelectedImage);

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

    @Keep
    @Subscribe
    public void onWishListCreated(CreateWishListResponse response) {
        if (response != null) {
            Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Keep
    @Subscribe
    public void onError(ErrorEvent error) {
        M.showAlert(getActivity(), "CREATE WISHLIST", error.errorMessage, "OK", null,
                null, null, false);
    }


}
