package com.test.mateflick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.test.mateflick.utils.preference.IPreference;
import com.test.mateflick.utils.preference.PreferenceManager;

/**
 * Base class for all {@link android.app.Activity} s in the application
 */
public abstract class BaseActivity extends AppCompatActivity implements IPreference{

    abstract void OnBaseCreate(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!PreferenceManager.getBooleanPreference(this,KEY_LOGGED_IN,false)){
            startActivity(new Intent(this,ActivityLogin.class));
            finish();
            return;
        }
        OnBaseCreate(savedInstanceState);
    }
}
