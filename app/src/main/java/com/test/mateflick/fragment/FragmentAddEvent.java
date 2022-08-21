package com.test.mateflick.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.test.mateflick.R;
import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.network.NetworkManager;
import com.test.mateflick.utils.network.request.CreateEventRequest;
import com.test.mateflick.utils.network.request.response.CreateEventResponse;
import com.test.mateflick.utils.preference.IPreference;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentAddEvent extends Fragment {


    @BindView(R.id.imgTabSelector)
    ImageView mImgTabSelector;
    @BindView(R.id.imgToggleNavigation)
    ImageButton mImgToggleNavigation;
    @BindView(R.id.txtEventName)
    EditText mTxtEventName;
    @BindView(R.id.txtEventPlace)
    EditText mTxtEventPlace;
    @BindView(R.id.txtEventDate)
    EditText mTxtEventDate;
    @BindView(R.id.txtEventTime)
    EditText mTxtEventTime;
    @BindView(R.id.btnAddEventOk)
    Button mBtnAddEventOk;

    private AlertDialog mNoNetworkDialog;


    public FragmentAddEvent() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnAddEventOk)
    public void onClick() {

        String eventName = mTxtEventName.getText().toString();
        String eventPlace = mTxtEventPlace.getText().toString();
        String eventDate = mTxtEventDate.getText().toString();
        String eventTime = mTxtEventTime.getText().toString();


        if (eventName.equals("")) {
            mTxtEventName.setError("required");
            return;
        }

        if (eventPlace.equals("")) {
            mTxtEventPlace.setError("required");
            return;
        }

        if (eventDate.equals("")) {
            mTxtEventDate.setError("required");
            return;
        }

        if (eventTime.equals("")) {
            mTxtEventName.setError("required");
            return;
        }

        if (!NetworkManager.isNetworkAvailable(getActivity())) {
            View dialogView = getActivity().getLayoutInflater()
                    .inflate(R.layout.extra_network_not_available, null);
            Button retry = (Button) dialogView.findViewById(R.id.btnRetry);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkManager.isNetworkAvailable(getActivity())) {
                        if (mNoNetworkDialog != null) mNoNetworkDialog.dismiss();
                    }
                }
            });

            mNoNetworkDialog = new AlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .setView(dialogView)
                    .show();


            return;
        }
        //name,date,time,place
        HashMap<String, String> params = new HashMap<>();
        params.put("name", eventName);
        params.put("date", eventDate);
        params.put("time", eventTime);
        params.put("place", eventPlace);

        String userID = PreferenceManager.getStringPreference(getActivity(),
                IPreference.KEY_USER_ID, "");

        new CreateEventRequest(getActivity(), userID, params);

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

    @Subscribe
    public void onEventCreated(CreateEventResponse response) {
        if (response != null) {

            AlertDialog ad = new AlertDialog.Builder(getActivity())
                    .create();
            ad.setCancelable(false);
            ad.setTitle("LIVESTREAM EVENT");
            ad.setMessage(response.getMessage());
            ad.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getActivity().finish();
                }
            });
            ad.show();

        } else {
            M.showAlert(getActivity(), "LIVESTREAM EVENT", "An error occurred. Please try again later",
                    "OK", null, null, null, false);
        }
    }


    @Subscribe
    public void onError(ErrorEvent errorEvent) {
        M.showAlert(getActivity(), "CREATE EVENT", errorEvent.errorMessage,
                "OK", null, null, null, false);
    }
}
