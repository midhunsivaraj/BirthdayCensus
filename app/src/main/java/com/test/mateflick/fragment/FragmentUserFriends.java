package com.test.mateflick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.mateflick.R;

/**
 * Created by Xtron Labs 05 on 3/22/2016.
 */
public class FragmentUserFriends extends BaseFragment {


    public FragmentUserFriends() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_friends,container,false);
    }
}
