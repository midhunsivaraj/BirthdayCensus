package com.test.mateflick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.test.mateflick.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xtron Labs 05 on 6/16/2016.
 */
public class FragmentEventList extends BaseFragment {


    @BindView(R.id.imgTabSelector)
    ImageView mImgTabSelector;
    @BindView(R.id.imgToggleNavigation)
    ImageButton mImgToggleNavigation;
    @BindView(R.id.btnEventAdd)
    Button mBtnEventAdd;
    @BindView(R.id.eventsList)
    RecyclerView mEventsList;
    @BindView(R.id.toolbarHolder)
    RelativeLayout mToolbarHolder;

    private AlertDialog mAddEventDialog;



    public FragmentEventList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.imgTabSelector, R.id.btnEventAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgTabSelector: {
                break;
            }
            case R.id.btnEventAdd: {
                break;
            }
        }
    }


    private void showAddEvent(){
        View addEventDialog = getActivity().getLayoutInflater().inflate(R.layout.add_event,null);
    }


}
