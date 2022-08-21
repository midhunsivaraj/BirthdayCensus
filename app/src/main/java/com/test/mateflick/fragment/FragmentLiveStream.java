package com.test.mateflick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.test.mateflick.R;
import com.test.mateflick.activity.ActivityAddEvent;
import com.test.mateflick.adapter.DiscoverListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xtron Labs 05 on 3/28/2016.
 */
public class FragmentLiveStream extends BaseFragment implements CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.rdoLive)
    RadioButton rdoLive;
    @BindView(R.id.rdoUpComing)
    RadioButton rdoUpComing;
    @BindView(R.id.rdoWatched)
    RadioButton rdoWatched;
    @BindView(R.id.discoverList)
    RecyclerView discoverList;
    @BindView(R.id.imgToggleNavigation)
    ImageButton mImgToggleNavigation;
    @BindView(R.id.toolbarHolder)
    RelativeLayout mToolbarHolder;
    @BindView(R.id.btnAddWishList)
    Button mBtnAddWishList;

    private DiscoverListAdapter mAdapter;


    public FragmentLiveStream() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_stream, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rdoLive.setOnCheckedChangeListener(this);
        rdoUpComing.setOnCheckedChangeListener(this);
        rdoWatched.setOnCheckedChangeListener(this);
        mAdapter = new DiscoverListAdapter(getActivity());
        discoverList.setAdapter(mAdapter);
        discoverList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @OnClick({R.id.imgToggleNavigation, R.id.btnAddWishList})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgToggleNavigation:
                break;
            case R.id.btnAddWishList:
                startActivity(new Intent(getActivity(), ActivityAddEvent.class));
                break;
        }
    }
}
