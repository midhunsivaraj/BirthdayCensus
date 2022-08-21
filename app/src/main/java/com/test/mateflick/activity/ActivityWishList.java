package com.test.mateflick.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.mateflick.R;

public class ActivityWishList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
    }


    @Override
    public void onBackPressed() {
        Intent discoverIntent = new Intent(this, ActivityUserHome.class);
        discoverIntent.putExtra("index", 2);
        startActivity(discoverIntent);
        finish();

    }
}
