package com.test.mateflick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.mateflick.R;

/**
 * Created by Xtron Labs 05 on 6/17/2016.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    Context context;

    public NotificationsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false);
        return new NotificationsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder {

        public NotificationsViewHolder(View itemView) {
            super(itemView);
        }
    }

}
