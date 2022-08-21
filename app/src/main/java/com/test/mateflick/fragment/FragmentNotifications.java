package com.test.mateflick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.test.mateflick.R;
import com.test.mateflick.adapter.NotificationsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xtron Labs 05 on 6/17/2016.
 */
public class FragmentNotifications extends BaseFragment {


    @BindView(R.id.imgTabSelector)
    ImageView imgTabSelector;
    @BindView(R.id.imgToggleNavigation)
    ImageButton imgToggleNavigation;
    @BindView(R.id.notificationsList)
    RecyclerView notificationsList;

    private NotificationsAdapter adapter ;

    public FragmentNotifications() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new NotificationsAdapter(getActivity());
        notificationsList.setAdapter(adapter);
        notificationsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
