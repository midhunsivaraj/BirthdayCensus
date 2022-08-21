package com.test.mateflick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.test.mateflick.R;
import com.test.mateflick.adapter.LivestreamListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xtron Labs 05 on 3/28/2016.
 */
public class FragmentDiscoverNew extends BaseFragment  {


    @BindView(R.id.rdoLive)
    RadioButton rdoLive;
    @BindView(R.id.rdoWatched)
    RadioButton rdoWatched;
    @BindView(R.id.discoverList)
    RecyclerView discoverList;
    @BindView(R.id.imgToggleNavigation)
    ImageButton mImgToggleNavigation;
    @BindView(R.id.toolbarHolder)
    RelativeLayout mToolbarHolder;

    private LivestreamListAdapter mAdapter;


    public FragmentDiscoverNew() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_new, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // rdoLive.setOnCheckedChangeListener(this);
       // rdoUpComing.setOnCheckedChangeListener(this);
        //rdoWatched.setOnCheckedChangeListener(this);
        mAdapter = new LivestreamListAdapter(getActivity());
        discoverList.setAdapter(mAdapter);
        discoverList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
