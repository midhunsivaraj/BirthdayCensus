package com.test.mateflick.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.mateflick.R;
import com.test.mateflick.activity.ActivityWishList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Xtron Labs 05 on 6/16/2016.
 */
public class WishesListAdapter extends RecyclerView.Adapter<WishesListAdapter.DiscoverListViewHolder> {

    private Context mContext;

    public WishesListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public DiscoverListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiscoverListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.extra_wishes_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DiscoverListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class DiscoverListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.video)
        ImageView video;
        @BindView(R.id.imgLiveStreamUploader)
        CircleImageView imgLiveStreamUploader;
        @BindView(R.id.lblLiveStreamFileName)
        TextView lblLiveStreamFileName;
        @BindView(R.id.lblLiveStreamUploader)
        TextView lblLiveStreamUploader;
        @BindView(R.id.videoHolder)
        RelativeLayout videoHolder;

        public DiscoverListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(new Intent(mContext, ActivityWishList.class));
        }
    }
}
