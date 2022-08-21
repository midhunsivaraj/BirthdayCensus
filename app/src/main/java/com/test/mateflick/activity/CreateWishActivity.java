package com.test.mateflick.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.mateflick.R;

public class CreateWishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wish);
    }


    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(this, ActivityUserHome.class);
        this.finish();
        startActivity(homeIntent);
    }
}
