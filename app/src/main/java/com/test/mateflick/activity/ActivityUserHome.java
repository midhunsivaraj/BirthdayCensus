package com.test.mateflick.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.test.mateflick.R;
import com.test.mateflick.adapter.ChatListAdapter;
import com.test.mateflick.fragment.BaseFragment;
import com.test.mateflick.fragment.FragmentChat;
import com.test.mateflick.fragment.FragmentChatList;
import com.test.mateflick.fragment.FragmentDashBoard;
import com.test.mateflick.fragment.FragmentDiscoverNew;
import com.test.mateflick.fragment.FragmentFavourites;
import com.test.mateflick.fragment.FragmentLiveStream;
import com.test.mateflick.fragment.FragmentNotifications;
import com.test.mateflick.fragment.FragmentPreferences;
import com.test.mateflick.utils.messages.M;
import com.test.mateflick.utils.preference.PreferenceManager;
import com.test.mateflick.views.ResideLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityUserHome extends BaseActivity implements
        BaseFragment.IToggle, CompoundButton.OnCheckedChangeListener, View.OnClickListener, ChatListAdapter.IDirectToChat {

    private Toolbar mToolbar;
    private RadioButton rdoDashboard, rdoDiscover, rdoBM, rdoLiveStream,
            rdoFavourites, rdoChat, rdoNotifications, rdoPreferences, rdoLogout;

    private ImageView mImgHalo;
    private CircleImageView mImgProPic;
    private BaseFragment lastFragment;

    private int checkedID;
    private static int checkedIndex;
    private ResideLayout mResideLayout;
    private TextView mLblNavigationName;
    private String mapUid = "0101";
    String resq = "no";

    @Override
    void OnBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mResideLayout = (ResideLayout) findViewById(R.id.mainLayout);
        mLblNavigationName = (TextView) findViewById(R.id.lblNavigationUserName);

        rdoDashboard = (RadioButton) findViewById(R.id.rdoDashBoard);
        rdoDiscover = (RadioButton) findViewById(R.id.rdoDiscover);
        rdoBM = (RadioButton) findViewById(R.id.rdoBM);
        rdoLiveStream = (RadioButton) findViewById(R.id.rdoLive);
        rdoFavourites = (RadioButton) findViewById(R.id.rdoFavourites);
        rdoChat = (RadioButton) findViewById(R.id.rdoChats);
        rdoNotifications = (RadioButton) findViewById(R.id.rdoNotifications);
        rdoPreferences = (RadioButton) findViewById(R.id.rdoPreferences);
        rdoLogout = (RadioButton) findViewById(R.id.rdoLogout);
        mImgHalo = (ImageView) findViewById(R.id.imgPropicHalo);
        mImgProPic = (CircleImageView) findViewById(R.id.imgNavigationProPic);

        mImgProPic.setOnClickListener(this);

        rdoDashboard.setOnCheckedChangeListener(this);
        rdoDiscover.setOnCheckedChangeListener(this);
        rdoBM.setOnCheckedChangeListener(this);
        rdoLiveStream.setOnCheckedChangeListener(this);
        rdoFavourites.setOnCheckedChangeListener(this);
        rdoChat.setOnCheckedChangeListener(this);
        rdoNotifications.setOnCheckedChangeListener(this);
        rdoPreferences.setOnCheckedChangeListener(this);
        rdoLogout.setOnCheckedChangeListener(this);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            try {
                String history = (String) b.get("where");
                if (history.equals("map")) {
                    mapUid = b.get("mapuid").toString();
                    Log.e("BUNDLE", "" + mapUid);
                    FragmentChat fragmentChat = new FragmentChat();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContentHolder, fragmentChat.newInstance(mapUid))
                            .commit();
                    lastFragment = fragmentChat;
                }
                else{
                    loadHome();
                }
            } catch (Exception e) {

            }}

        else{
            loadHome();

        }



        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        /*if (getIntent() != null) {
            checkedIndex = getIntent().getIntExtra("index", -1);
            if (checkedIndex != -1)
                rdoDiscover.setChecked(true);
        }*/
    }

    private void loadHome() {

            FragmentDashBoard fragmentDashBoard = new FragmentDashBoard();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainContentHolder, fragmentDashBoard)
                    .commit();
            lastFragment = fragmentDashBoard;

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mImgHalo != null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int top = mImgHalo.getTop();
                    int left = mImgHalo.getLeft();
                    int height = mImgHalo.getMeasuredHeight();
                    int width = mImgHalo.getMeasuredWidth();


                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mImgProPic.getLayoutParams();
                    int marginTop = lp.topMargin;
                    int marginLeft = lp.leftMargin;
                    int proPicHeight = lp.height;
                    int requiredHeight = proPicHeight / 2;

                    int calculatedTop = top - (height / 2) + marginTop + requiredHeight;
                    int calculatedLeft = left - (width / 2) + marginLeft + requiredHeight;

                    RelativeLayout.LayoutParams lpp = new RelativeLayout.LayoutParams(width, height);
                    lpp.setMargins(calculatedLeft, calculatedTop, calculatedLeft, calculatedTop);

                    mImgHalo.setLayoutParams(lpp);

                    //mImgHalo.setTop(calculatedTop);
                    //mImgHalo.setLeft(calculatedLeft);
                    //mImgHalo.requestLayout();
                    M.log("position", "top : " + top + "\nleft : " + left + "\ncalculated top : " +
                            calculatedTop + "\nMargin top : " + marginTop + "\nMargin  left : " + marginLeft +
                            "\npropic height : " + proPicHeight + "\nRequired height : " + requiredHeight +
                            "\ncalculated left : " + calculatedLeft + "\ncurrent top : " +
                            mImgHalo.getTop() + "\ncurrent left : " + mImgHalo.getLeft());

                    Glide.with(ActivityUserHome.this)
                            .load(getString(R.string.base_url)
                                    + PreferenceManager.getStringPreference(ActivityUserHome.this,
                                    KEY_PROFILE_IMAGE, ""))
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource,
                                                            GlideAnimation<? super Bitmap>
                                                                    glideAnimation) {
                                    mImgProPic.setImageBitmap(resource);
                                }
                            });

                    mLblNavigationName.setText(PreferenceManager
                            .getStringPreference(ActivityUserHome.this, KEY_USER_NAME, ""));

                }
            }, 1000);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void toggleNavigation() {
        if (mResideLayout.isOpen()) {
            mResideLayout.closePane();
        } else {
            mResideLayout.openPane();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (isChecked) {
            checkedID = id;
        }
        switch (id) {
            case R.id.rdoDashBoard: {
                if (isChecked) {
                    FragmentDashBoard fragmentDashBoard = new FragmentDashBoard();
                    getSupportFragmentManager().beginTransaction()
                            .remove(lastFragment)
                            .add(R.id.mainContentHolder, fragmentDashBoard)
                            .commit();
                    lastFragment = fragmentDashBoard;
                    break;
                }
            }
            case R.id.rdoDiscover: {
                if (isChecked) {
                    FragmentDiscoverNew fragmentDiscover = new FragmentDiscoverNew();
                            //.getInstance(checkedIndex == -1 ? 0 : checkedIndex);
                    getSupportFragmentManager().beginTransaction()
                            .remove(lastFragment)
                            .add(R.id.mainContentHolder, fragmentDiscover)
                            .commit();
                    lastFragment = fragmentDiscover;
                    // startActivity(new Intent(this, DiscoverActivity.class));
                    break;
                }
            }
            case R.id.rdoBM: {
                if (isChecked) {
                    /*FragmentDiscoverNew fragmentDiscover = FragmentDiscoverNew
                            .getInstance(checkedIndex == -1 ? 0 : checkedIndex);
                    getSupportFragmentManager().beginTransaction()
                            .remove(lastFragment)
                            .add(R.id.mainContentHolder, fragmentDiscover)
                            .commit();
                    lastFragment = fragmentDiscover;*/
                    startActivity(new Intent(this, ActivityBirthmates.class));
                    return;
                }
            }
            case R.id.rdoLive: {
                if (isChecked) {
                    FragmentLiveStream fragmentLiveStream = new FragmentLiveStream();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContentHolder, fragmentLiveStream)
                            .commit();
                    lastFragment = fragmentLiveStream;

                    /*FragmentEventList fragmentEventList = new FragmentEventList();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContentHolder, fragmentEventList)
                            .commit();
                    lastFragment = fragmentEventList;*/

                    break;
                }
            }
            case R.id.rdoFavourites: {
                if (isChecked) {
                    FragmentFavourites fragmentFavourites = new FragmentFavourites();
                    //FragmentUserWishList fragmentFavourites = new FragmentUserWishList();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContentHolder, fragmentFavourites)
                            .commit();
                    lastFragment = fragmentFavourites;
                    break;
                }
            }
            case R.id.rdoChats: {
                if (isChecked) {
                    FragmentChatList fragmentChatList = new FragmentChatList();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContentHolder, fragmentChatList)
                            .commit();
                    lastFragment = fragmentChatList;
                    break;
                }
            }
            case R.id.rdoNotifications: {
                if (isChecked) {
                    FragmentNotifications fragmentNotifications = new FragmentNotifications();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContentHolder, fragmentNotifications)
                            .commit();
                    lastFragment = fragmentNotifications;
                    break;
                }
            }
            case R.id.rdoPreferences: {
                if (isChecked) {
                    FragmentPreferences fragmentPreferences = new FragmentPreferences();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContentHolder, fragmentPreferences)
                            .commit();
                    lastFragment = fragmentPreferences;
                    break;
                }
            }
            case R.id.rdoLogout: {
                if (isChecked) {
                    PreferenceManager.saveBooleanPreference(this, KEY_LOGGED_IN, false);
                    Intent login = new Intent(this, ActivityLogin.class);
                    login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    startActivity(login);
                    break;
                }
            }

        }
        toggleNavigation();
    }

    @Override
    public void onBackPressed() {
        FragmentDashBoard fragmentDashBoard = new FragmentDashBoard();
        getSupportFragmentManager().beginTransaction()
                .remove(lastFragment)
                .add(R.id.mainContentHolder, fragmentDashBoard)
                .commit();
        lastFragment = fragmentDashBoard;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgNavigationProPic) {
            loadHome();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MAIN", "RESUME_CALLED");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            try {
                String history = (String) b.get("where");
                if (history.equals("map")) {
                    mapUid = b.get("mapuid").toString();
                    Log.e("BUNDLE", "" + mapUid);
                    FragmentChat fragmentChat = new FragmentChat();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainContentHolder, fragmentChat.newInstance(mapUid))
                            .commit();
                    lastFragment = fragmentChat;
                }
            } catch (Exception e) {

            }

        }
    }

    @Override
    public void directToChat(String id, String convid) {

        FragmentChat fragmentChat = FragmentChat.newInstance(convid,id);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContentHolder, fragmentChat)
                .commit();
        lastFragment = fragmentChat;

    }
}
