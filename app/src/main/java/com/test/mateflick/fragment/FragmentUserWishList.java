package com.test.mateflick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.Toast;

import com.test.mateflick.R;
import com.test.mateflick.activity.CreateWishActivity;
import com.test.mateflick.adapter.CoverFlowAdapter;
import com.test.mateflick.model.GameEntity;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by Xtron Labs 05 on 3/22/2016.
 */
public class FragmentUserWishList extends BaseFragment {

    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<>(0);
    private TextSwitcher mTitle;
    private Button mCreateWish;

    public FragmentUserWishList() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_wish_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mData.add(new GameEntity(R.drawable.image3, R.string.title1));
        mData.add(new GameEntity(R.drawable.image4, R.string.title2));
        mData.add(new GameEntity(R.drawable.image3, R.string.title1));
        mData.add(new GameEntity(R.drawable.image4, R.string.title2));

        mCreateWish = (Button) getActivity().findViewById(R.id.btnCreateWish);
        mCreateWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateWishActivity.class));
            }
        });

        mAdapter = new CoverFlowAdapter(getActivity());
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) getActivity().findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),
                        getResources().getString(mData.get(position).titleResId),
                        Toast.LENGTH_SHORT).show();
                mCoverFlow.scrollToPosition(position);
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                //mTitle.setText(getResources().getString(mData.get(position).titleResId));
            }

            @Override
            public void onScrolling() {
                //mTitle.setText("");
            }
        });
    }
}
