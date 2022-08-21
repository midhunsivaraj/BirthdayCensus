package com.test.mateflick.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.mateflick.R;
import com.test.mateflick.adapter.WishesListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishesFragment extends BaseFragment {


    @BindView(R.id.wishesList)
    RecyclerView wishesList;

    private WishesListAdapter mAdapter;

    public WishesFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new WishesListAdapter(getActivity());
        wishesList.setAdapter(mAdapter);
        wishesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
