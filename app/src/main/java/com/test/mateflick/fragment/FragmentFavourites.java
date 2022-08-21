package com.test.mateflick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.test.mateflick.R;
import com.test.mateflick.adapter.DiscoverListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xtron Labs 05 on 5/21/2016.
 */
public class FragmentFavourites extends BaseFragment {


    private DiscoverListAdapter mAdapter;



    @BindView(R.id.imgToggleNavigation)
    ImageButton mImgToggleNavigation;
    @BindView(R.id.toolbarHolder)
    RelativeLayout mToolbarHolder;
    @BindView(R.id.favouritesList)
    RecyclerView mFavouritesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.imgToggleNavigation)
    public void onClick() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new DiscoverListAdapter(getActivity());
        mFavouritesList.setAdapter(mAdapter);
        mFavouritesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
