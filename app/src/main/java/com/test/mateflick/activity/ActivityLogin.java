package com.test.mateflick.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.mateflick.R;
import com.test.mateflick.fragment.FragmentLogin;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainContentHolder, new FragmentLogin(),"FragmentLogin")
                .commit();

    }
}
