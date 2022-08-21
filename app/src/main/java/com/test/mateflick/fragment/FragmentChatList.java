package com.test.mateflick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.mateflick.R;
import com.test.mateflick.adapter.ChatListAdapter;
import com.test.mateflick.utils.network.request.ListMyChatRequest;
import com.test.mateflick.utils.network.request.response.GetMyChatsResponse;
import com.test.mateflick.utils.preference.IPreference;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Me on 27-08-2016.
 */
public class FragmentChatList extends BaseFragment {

    @BindView(R.id.imgToggleNavigation)
    ImageButton mImgToggleNavigation;
    @BindView(R.id.toolbarHolder)
    RelativeLayout mToolbarHolder;
    @BindView(R.id.lblnoChat)
    TextView mLblnoChat;
    @BindView(R.id.chatList)
    RecyclerView mChatList;

    public FragmentChatList() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userID = PreferenceManager.getStringPreference(getActivity(), IPreference.KEY_USER_ID,"");
        new ListMyChatRequest(getActivity(),userID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onListArrived(GetMyChatsResponse[] response) {
        if (response == null || response.length<=0){
            mLblnoChat.setVisibility(View.VISIBLE);
            mChatList.setVisibility(View.GONE);
            return;
        }

        ChatListAdapter chatListAdapter = new ChatListAdapter(response, getActivity());
        mChatList.setAdapter(chatListAdapter);
        chatListAdapter.setmDirectToChat((ChatListAdapter.IDirectToChat) getActivity());
        mChatList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }

}
