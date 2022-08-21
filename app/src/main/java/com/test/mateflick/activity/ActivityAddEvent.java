package com.test.mateflick.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.mateflick.R;
import com.test.mateflick.fragment.FragmentAddEvent;

public class ActivityAddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainContentHolder,new FragmentAddEvent())
                .commit();
    }
}
