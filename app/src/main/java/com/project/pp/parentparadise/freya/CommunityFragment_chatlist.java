package com.project.pp.parentparadise.freya;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.project.pp.parentparadise.R;
import com.project.pp.parentparadise.utl.Common;
import com.project.pp.parentparadise.utl.MyTask;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class CommunityFragment_chatlist extends Fragment {

    private static final String TAG = "ChatListFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView  rvChatList;
    private MyTask ChatGetAllTask;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        onCreateView自動幫忙建好view
        view = inflater.inflate(R.layout.fragment_community_chatlist, container, false);
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvFriends);
//        recyclerView.setLayoutManager(
//                new StaggeredGridLayoutManager(
//                        1, StaggeredGridLayoutManager.VERTICAL));
        swipeRefreshLayout =
                view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showAllChatLists();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        rvChatList = view.findViewById(R.id.rvChatList);
        rvChatList.setLayoutManager(new LinearLayoutManager(getActivity()));

        ImageButton button = view.findViewById(R.id.btAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommunityAdd.class);
                startActivity(intent);
            }
        });


        return view;
    }


    private void showAllChatLists() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/ChatListServlet";
            List < CommunityChatList> chatLists = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getAll");
            // relation_member_no value:1 指id為1 ，到時候combine 登入帳號  value要修改為登錄帳號
            jsonObject.addProperty("chat_message_id", 1);
            String jsonOut = jsonObject.toString();
            ChatGetAllTask = new MyTask(url, jsonOut);
            try {
                String jsonIn =ChatGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<List < CommunityChatList>>() {
                }.getType();
                chatLists = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (chatLists == null || chatLists.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoChatFound);
            } else {
                rvChatList.setAdapter(new ChatRecycleAdapter(getActivity(), chatLists));
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoDataFound);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllChatLists();
    }

    private class ChatRecycleAdapter extends RecyclerView.Adapter <CommunityFragment_chatlist.ChatRecycleAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private List <CommunityChatList> chatLists;
        private int imageSize;

//        private class FriendsRecyclerViewAdapter extends RecyclerView.Adapter <CommunityFragment_family.FriendsRecyclerViewAdapter.MyViewHolder> {
//            private LayoutInflater layoutInflater;
//            private List <CommunityFItem> friends;
//            private int imageSize;

        ChatRecycleAdapter(Context context, List <CommunityChatList> chatLists) {
            layoutInflater = LayoutInflater.from(context);
            this.chatLists = chatLists;
            /* 螢幕寬度除以4當作將圖的尺寸 */
//            imageSize = getResources().getDisplayMetrics().widthPixels / 4;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            //            ImageView imageView;
            TextView tvChatMsg; /*tvAddress*/;

            MyViewHolder(View itemView) {
                super(itemView);
//                imageView = itemView.findViewById(R.id.ivSpot);
                tvChatMsg= itemView.findViewById(R.id.tvMsg);
                //  tvChatMsg = itemView.findViewById(R.id.tvChatMsg);
//                tvAddress = itemView.findViewById(R.id.tvAddress);
            }
        }

        @Override
        public int getItemCount() {
            return chatLists.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.fragment_community_item02, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            final CommunityChatList chatList = chatLists.get(position);
            String url = Common.URL + "/ChatListServlet";
            //String first_name = chatList.getFirst_name();
            String chat_message=chatList.getChat_message();
//            spotGetImageTask = new SpotGetImageTask(url, id, imageSize, myViewHolder.imageView);
//            spotGetImageTask.execute();
            // myViewHolder.tvName.setText(chatList.getFirst_name());
              myViewHolder.tvChatMsg.setText(chatList.getChat_message());
            //myViewHolder.tvPhoneNo.setText(friend.getPhoneNo());
            // myViewHolder.tvAddress.setText(friend.getAddress());
//            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Fragment fragment = new SpotDetailFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("spot", spot);
//                    fragment.setArguments(bundle);
//                    switchFragment(fragment);
//                }
//            });
//            myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    PopupMenu popupMenu = new PopupMenu(getActivity(), view, Gravity.END);
//                    popupMenu.inflate(R.menu.popup_menu);
//                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId()) {
//                                case R.id.update:
//                                    Fragment fragment = new SpotUpdateFragment();
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("spot", spot);
//                                    fragment.setArguments(bundle);
//                                    switchFragment(fragment);
//                                    break;
//                                case R.id.delete:
//                                    if (Common.networkConnected(getActivity())) {
//                                        String url = Common.URL + "/SpotServlet";
//                                        JsonObject jsonObject = new JsonObject();
//                                        jsonObject.addProperty("action", "spotDelete");
//                                        jsonObject.addProperty("spot", new Gson().toJson(spot));
//                                        int count = 0;
//                                        try {
//                                            spotDeleteTask = new MyTask(url, jsonObject.toString());
//                                            String result = spotDeleteTask.execute().get();
//                                            count = Integer.valueOf(result);
//                                        } catch (Exception e) {
//                                            Log.e(TAG, e.toString());
//                                        }
//                                        if (count == 0) {
//                                            Common.showToast(getActivity(), R.string.msg_DeleteFail);
//                                        } else {
//                                            spots.remove(spot);
//                                            FriendFragment.FriendsRecyclerViewAdapter.this.notifyDataSetChanged();
//                                            Common.showToast(getActivity(), R.string.msg_DeleteSuccess);
//                                        }
//                                    } else {
//                                        Common.showToast(getActivity(), R.string.msg_NoNetwork);
//                                    }
//                            }
//                            return true;
//                        }
//                    });
//                    popupMenu.show();
//                    return true;
//                }
//            });
        }

    }

//    private void switchFragment(Fragment fragment) {
//        FragmentTransaction fragmentTransaction =
//                getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.body, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }

    @Override
    public void onStop() {
        super.onStop();
        if (ChatGetAllTask != null) {
            ChatGetAllTask.cancel(true);
        }
//
//        if (spotGetImageTask != null) {
//            spotGetImageTask.cancel(true);
//        }

//        if (spotDeleteTask != null) {
//            spotDeleteTask.cancel(true);
//        }
    }
}
