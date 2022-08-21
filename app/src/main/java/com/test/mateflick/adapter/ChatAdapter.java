package com.test.mateflick.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.test.mateflick.R;
import com.test.mateflick.utils.network.request.response.ChatItem;
import com.test.mateflick.utils.preference.IPreference;
import com.test.mateflick.utils.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Me on 28-08-2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder> {

    private Context mContext;
    private ArrayList<ChatItem> mChatItems;

    private static final int FROM_SERVER = 0;
    private static final int FROM_CLIENT = 1;
    private boolean isFirst = true;
    private String userID;

    private int lastCount;
    private StringBuilder lastIds = new StringBuilder();

    public ChatAdapter(Context mContext, ArrayList<ChatItem> mChatItems) {
        this.mContext = mContext;
        this.mChatItems = mChatItems;
        userID = PreferenceManager.getStringPreference(mContext, IPreference.KEY_USER_ID, "");


    }

    public void add(ChatItem[] chatItems) {
        lastIds = new StringBuilder();
        mChatItems.clear();
        for (int i = 0; i < chatItems.length; i++) {
            mChatItems.add(chatItems[i]);
        }
        notifyItemRangeInserted(lastCount, chatItems.length);
    }

    public void add(List<ChatItem> chatItems) {
        lastCount = mChatItems.size();
        lastIds = new StringBuilder();
        for (int i = 0; i < chatItems.size(); i++) {
            mChatItems.add(chatItems.get(i));
            boolean isFirst = true;
            if (!isFirst) {
                lastIds.append(",");
            }
            isFirst = false;
            lastIds.append(mChatItems.get(i).getCIdFk());
        }
        notifyItemRangeInserted(lastCount, chatItems.size());
        isFirst = false;
    }

    public void add(ChatItem chatItem){
        mChatItems.add(chatItem);
        notifyItemInserted(getItemCount());
    }


    public String getIds() {
        if (isFirst) {
            return "0";
        }
        return lastIds.toString().equals("") ? "0" : lastIds.toString();
    }

    @Override
    public ChatItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FROM_SERVER) {
            return new ChatItemViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.extra_chat_item_left, parent, false));
        }
        return new ChatItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.extra_chat_item_right, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        ChatItem chatItem = mChatItems.get(position);
        if (chatItem.getUserIdFk().equals(userID)) {
            return FROM_CLIENT;
        }
        return FROM_SERVER;
    }

    @Override
    public void onBindViewHolder(final ChatItemViewHolder holder, int position) {
        ChatItem chatItem = mChatItems.get(position);
        if (chatItem == null) return;
        Glide.with(mContext)
                .load(mContext.getString(R.string.base_url) + chatItem.getImage())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.profileImage.setImageBitmap(resource);
                    }
                });
        holder.lblMessage.setText(chatItem.getReply());


    }

    @Override
    public int getItemCount() {
        return mChatItems.size();
    }

    class ChatItemViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView profileImage;
        private TextView lblMessage;

        public ChatItemViewHolder(View itemView) {
            super(itemView);
            profileImage = (CircleImageView) itemView.findViewById(R.id.imgChatImage);
            lblMessage = (TextView) itemView.findViewById(R.id.lblChatMesage);

        }
    }
}
