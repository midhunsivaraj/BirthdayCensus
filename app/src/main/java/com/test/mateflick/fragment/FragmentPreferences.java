package com.test.mateflick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.mateflick.R;

/**
 * Created by Xtron005 on 22-08-2016.
 */
public class FragmentPreferences extends com.test.mateflick.fragment.BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preferences,container,false);
    }
}
