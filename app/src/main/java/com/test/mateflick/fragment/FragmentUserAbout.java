package com.test.mateflick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.test.mateflick.R;
import com.test.mateflick.utils.event.ErrorEvent;
import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.network.NetworkManager;
import com.test.mateflick.utils.network.request.UpdateMyAboutRequest;
import com.test.mateflick.utils.network.request.response.UpdateAboutResponse;
import com.test.mateflick.utils.preference.IPreference;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xtron Labs 05 on 3/22/2016.
 */
public class FragmentUserAbout extends BaseFragment {


    @BindView(R.id.lblUserAbout)
    TextView mLblUserAbout;
    @BindView(R.id.btnEditUserAbout)
    ImageButton mBtnEditUserAbout;
    private AlertDialog mAlertDialog;
    private AlertDialog mNoNetworkDialog;


    public FragmentUserAbout() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_about, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mLblUserAbout != null) {
            mLblUserAbout.setText(PreferenceManager.getStringPreference(getActivity(), KEY_ABOUT, ""));
        }
    }

    @OnClick(R.id.btnEditUserAbout)
    public void onClick() {
        View v = getActivity().getLayoutInflater().inflate(R.layout.extra_edit_about, null);
        Button ok = (Button) v.findViewById(R.id.btnEditAboutOk);
        Button cancel = (Button) v.findViewById(R.id.btnEditAboutCancel);
        final EditText txtAbout = (EditText) v.findViewById(R.id.txtEditAbout);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String about = txtAbout.getText().toString();
                if (about.equals("")) {
                    txtAbout.setError("Cannot be empty");
                } else {
                    if (!NetworkManager.isNetworkAvailable(getActivity())) {
                        if (mAlertDialog != null)
                            mAlertDialog.dismiss();
                        View dialogView = getActivity().getLayoutInflater()
                                .inflate(R.layout.extra_network_not_available, null);
                        Button b = (Button) dialogView.findViewById(R.id.btnRetry);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (NetworkManager.isNetworkAvailable(getActivity()))
                                    if (mNoNetworkDialog != null)
                                        mNoNetworkDialog.dismiss();
                            }
                        });


                        mNoNetworkDialog = new AlertDialog.Builder(getActivity(),
                                R.style.Birthday_dialog_style)
                                .setView(dialogView)
                                .setCancelable(false)
                                .show();

                        return;
                    }

                    String userID = PreferenceManager.getStringPreference(getActivity(),
                            KEY_USER_ID, "");
                    if (userID.equals("")) return;

                    new UpdateMyAboutRequest(getActivity(), userID, about);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlertDialog != null) {
                    mAlertDialog.dismiss();
                }
            }
        });

        mAlertDialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Describe Yourself")
                .setCancelable(false)
                .create();
        mAlertDialog.show();
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
    public void onError(ErrorEvent errorEvent) {
        M.showAlert(getActivity(), getString(R.string.app_name), errorEvent.errorMessage, "OK",
                null, null, null, false);
    }

    @Subscribe
    public void onAboutUpdate(UpdateAboutResponse response) {
        if (mAlertDialog != null) mAlertDialog.dismiss();

        Toast t = Toast.makeText(getActivity(),
                response == null ? "Unable to update about" : "Updated successfully",
                Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP, 0, 100);
        t.show();
        if (response != null) {
            PreferenceManager.saveStringPreference(getActivity(), IPreference.KEY_ABOUT,
                    response.getAbout());
            mLblUserAbout.setText(PreferenceManager.getStringPreference(getActivity(), KEY_ABOUT, ""));
        }
    }

}
