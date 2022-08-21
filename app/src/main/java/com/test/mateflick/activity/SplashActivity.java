package com.test.mateflick.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.mateflick.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, ActivityUserHome.class));
                SplashActivity.this.finish();

                Intent homeIntent = new Intent(SplashActivity.this, ActivityUserHome.class);
                homeIntent.putExtra("res", "no");
                SplashActivity.this.finish();
                startActivity(homeIntent);

                Intent ii = new Intent(SplashActivity.this, ActivityUserHome.class);
                ii.putExtra("res", "yes");
                ii.putExtra("where", "map");
                startActivity(ii);
            }
        },3000);

    }
}
