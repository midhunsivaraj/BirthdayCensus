package com.test.mateflick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.test.mateflick.R;
import com.test.mateflick.activity.ActivityUserHome;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xtron Labs 05 on 6/17/2016.
 */
public class FragmentWishListItem extends Fragment {

    @BindView(R.id.imgTabSelector)
    ImageView imgTabSelector;
    @BindView(R.id.imgToggleNavigation)
    ImageButton imgToggleNavigation;

    public FragmentWishListItem() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list_item, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.imgToggleNavigation)
    public void onClick() {
        Intent discoverIntent = new Intent(getActivity(), ActivityUserHome.class);
        discoverIntent.putExtra("index", 2);
        startActivity(discoverIntent);
        getActivity().finish();
    }
}
