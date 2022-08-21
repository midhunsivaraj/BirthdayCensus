package com.test.mateflick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.test.mateflick.R;
import com.test.mateflick.adapter.ChatAdapter;
import com.test.mateflick.utils.network.request.DoChatRequest;
import com.test.mateflick.utils.network.request.GetChatMessagesForChatRequest;
import com.test.mateflick.utils.network.request.GetUnreadMessagesForChatRequest;
import com.test.mateflick.utils.network.request.response.ChatItem;
import com.test.mateflick.utils.preference.IPreference;
import com.test.mateflick.utils.preference.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xtron Labs 05 on 3/28/2016.
 */
public class FragmentChat extends BaseFragment {

    public static final String KEY = "USER_ID";
    public static final String KEY_CONV_ID = "convId";
    @BindView(R.id.lblUserName09)
    TextView mLblUserName;
    @BindView(R.id.txtMessage)
    EditText mTxtMessage;
    @BindView(R.id.send)
    ImageButton mSend;
    @BindView(R.id.chatMessages)
    RecyclerView mChatMessages;
    @BindView(R.id.imgToggleNavigation)
    ImageButton togn;
    private String userId, conversationId;

    private String ownId;
    private ChatAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;

    public FragmentChat() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public static FragmentChat newInstance(String userId) {
        FragmentChat instance = new FragmentChat();
        Bundle args = new Bundle();
        args.putString(KEY, userId);
        instance.setArguments(args);
        return instance;
    }

    public static FragmentChat newInstance(String convId, String userId) {
        FragmentChat instance = new FragmentChat();
        Bundle b = new Bundle();
        b.putString(KEY_CONV_ID, convId);
        b.putString(KEY, userId);
        instance.setArguments(b);
        return instance;
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b == null) return;
        userId = b.getString(KEY, "");
        conversationId = b.getString(KEY_CONV_ID, "");
        if (b.equals("")) return;

        mAdapter = new ChatAdapter(getActivity(), new ArrayList<ChatItem>());
        mLayoutManger = new LinearLayoutManager(getActivity());
        ownId = PreferenceManager.getStringPreference(getActivity(), IPreference.KEY_USER_ID, "");
        new GetChatMessagesForChatRequest(getActivity(), conversationId);


        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(new Runnable() {
            public void run() {
                new GetUnreadMessagesForChatRequest(getActivity(), conversationId, mAdapter.getIds());
                //new GetChatMessagesForChatRequest(getActivity(),conversationId);
            }
        }, 0, 10, TimeUnit.SECONDS);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mChatMessages.setAdapter(mAdapter);
        mChatMessages.setLayoutManager(mLayoutManger);
        togn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("POP BACK", "STACKK");
                FragmentChatList fragmentChatList = new FragmentChatList();
                getFragmentManager().beginTransaction()
                        .replace(R.id.mainContentHolder, fragmentChatList)
                        .commit();
            }
        });
    }

    @Subscribe
    public void onChatResponse(String id) {
        if (id != null && !id.equals("")) {
            conversationId = id;
        }
    }

    @Subscribe
    public void onChatListReceived(ChatItem[] chatItems) {

        if (chatItems == null) return;
        if (chatItems.length == 0) return;
        mAdapter.add(chatItems);
        if (mAdapter.getItemCount() == 0) return;
        mChatMessages.setAdapter(mAdapter);
        mChatMessages.setLayoutManager(mLayoutManger);
        mChatMessages.smoothScrollToPosition(mAdapter.getItemCount());
    }


    @Subscribe
    public void onChatListReceived(List<ChatItem> chatItems) {
        if (chatItems == null) return;
        if (chatItems.size() == 0) return;
        mAdapter.add(chatItems);
        if (mAdapter.getItemCount() == 0) return;
        mChatMessages.setAdapter(mAdapter);
        mChatMessages.setLayoutManager(mLayoutManger);
        mChatMessages.smoothScrollToPosition(mAdapter.getItemCount());
    }


    @OnClick(R.id.send)
    public void onClick() {
        if (userId == null || userId.equals(""))
            userId = PreferenceManager.getStringPreference(getActivity(), KEY_USER_ID, "");

        new DoChatRequest(getActivity(), userId, ownId, mTxtMessage.getText().toString());
        ChatItem chatItem = new ChatItem();
        chatItem.setConversationId(conversationId);
        chatItem.setReply(mTxtMessage.getText().toString());
        chatItem.setUserIdFk(PreferenceManager.getStringPreference(getActivity(), KEY_USER_ID, ""));
        chatItem.setImage(PreferenceManager.getStringPreference(getActivity(), KEY_PROFILE_IMAGE, ""));
        mAdapter.add(chatItem);
        if (mAdapter.getItemCount() == 0) return;
        mChatMessages.smoothScrollToPosition(mAdapter.getItemCount());
        mTxtMessage.setText("");
    }
}
