package com.test.mateflick.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.mateflick.R;
import com.test.mateflick.fragment.FragmentDiscoverBirthmates;
import com.test.mateflick.fragment.FragmentLiveStream;
import com.test.mateflick.fragment.WishesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {




    @BindView(R.id.rdoBirthMates)
    RadioButton mRdoBirthMates;
    @BindView(R.id.rdoLiveStream)
    RadioButton mRdoLiveStream;
    @BindView(R.id.rdoWishes)
    RadioButton mRdoWishes;
    @BindView(R.id.rdoGrpDiscover)
    RadioGroup mRdoGrpDiscover;
    @BindView(R.id.fragmentHolder)
    FrameLayout mFragmentHolder;

    private float lastY;
    private float lastX;
    private int imageWidth, imageHeight;
    private float offset;

    private int index;
    float imageX;
    float imageY;

    @Override
    void OnBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_discover_new);
        ButterKnife.bind(this);
        mRdoBirthMates.setOnCheckedChangeListener(this);
        mRdoWishes.setOnCheckedChangeListener(this);
        mRdoLiveStream.setOnCheckedChangeListener(this);
        //mRdoBirthMates.setChecked(true);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.rdoBirthMates: {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentHolder, new FragmentDiscoverBirthmates(), "mapFragment")
                            .commit();

                }
                break;
            }

            case R.id.rdoLiveStream: {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentHolder, new FragmentLiveStream())
                            .commit();

                }
                break;
            }

            case R.id.rdoWishes: {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentHolder, new WishesFragment())
                            .commit();

                }
                break;
            }

        }
    }





}
