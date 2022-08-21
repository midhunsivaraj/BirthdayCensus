package com.test.mateflick.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.test.mateflick.R;
import com.test.mateflick.utils.network.request.response.GetMyChatsResponse;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Me on 28-08-2016.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>{

    private GetMyChatsResponse[] mChatList;
    private Context mContext;
    private IDirectToChat mDirectToChat;

    public ChatListAdapter(GetMyChatsResponse[] mChatList, Context mContext) {
        this.mChatList = mChatList;
        this.mContext = mContext;
    }

    public void setmDirectToChat(IDirectToChat directToChat){
        mDirectToChat = directToChat;
    }

    @Override
    public ChatListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.chat_list_item, parent,false));
    }

    @Override
    public void onBindViewHolder(final ChatListViewHolder holder, int position) {
        if (mChatList == null) return;
        Glide.with(mContext)
                .load(mContext.getString(R.string.base_url)+mChatList[position].getImage())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.imgProfile.setImageBitmap(resource);
                    }
                });
        holder.lblName.setText(mChatList[position].getName());
        holder.lblMessage.setText(mChatList[position].getLastMsg());
        holder.lblTime.setText(mChatList[position].getTime());
    }

    @Override
    public int getItemCount() {
        return mChatList.length;
    }

    class ChatListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView imgProfile;
        TextView lblName, lblMessage, lblTime;


        public ChatListViewHolder(View itemView) {
            super(itemView);
            imgProfile = (CircleImageView) itemView.findViewById(R.id.imgChatListImage);
            lblName = (TextView) itemView.findViewById(R.id.lblChatListName);
            lblMessage = (TextView) itemView.findViewById(R.id.lblchatListLastMessage);
            lblTime = (TextView) itemView.findViewById(R.id.lblchatListMessageTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            GetMyChatsResponse x = mChatList[getAdapterPosition()];
            if (x == null){
                //do something here or show a toast
                return;
            }
            if (mDirectToChat != null){
                mDirectToChat.directToChat(x.getUserId(),x.getConversationId());

            }
        }
    }

    public interface IDirectToChat{
        void directToChat(String id, String convid);

    }

}
