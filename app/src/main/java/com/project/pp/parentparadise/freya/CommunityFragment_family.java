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



public class CommunityFragment_family extends Fragment {

    private static final String TAG = "FriendsListFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvFriends;
    private MyTask friendGetAllTask;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        onCreateView自動幫忙建好view
        view = inflater.inflate(R.layout.fragment_community_family, container, false);
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
                showAllFriends();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        rvFriends = view.findViewById(R.id.rvFriends);
        rvFriends.setLayoutManager(new LinearLayoutManager(getActivity()));

        ImageButton button = view.findViewById(R.id.btAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommunityAdd.class);
                startActivity(intent);
            }
        });


        return view;
    }


    private void showAllFriends() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/FriendServlet";
            List < CommunityFItem> friends = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getAll");
            // relation_member_no value:1 指id為1 ，到時候combine 登入帳號  value要修改為登錄帳號
            jsonObject.addProperty("relation_member_no", 1);
            String jsonOut = jsonObject.toString();
            friendGetAllTask = new MyTask(url, jsonOut);
            try {
                String jsonIn = friendGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<List < CommunityFItem>>() {
                }.getType();
                friends = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (friends == null || friends.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoFriendFound);
            } else {
                rvFriends.setAdapter(new FriendsRecyclerViewAdapter(getActivity(), friends));
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoDataFound);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllFriends();
    }

    private class FriendsRecyclerViewAdapter extends RecyclerView.Adapter <FriendsRecyclerViewAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private List <CommunityFItem> friends;
        private int imageSize;

        FriendsRecyclerViewAdapter(Context context, List <CommunityFItem> friends) {
            layoutInflater = LayoutInflater.from(context);
            this.friends = friends;
            /* 螢幕寬度除以4當作將圖的尺寸 */
//            imageSize = getResources().getDisplayMetrics().widthPixels / 4;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            //            ImageView imageView;
            TextView tvName /*, tvPhoneNo, tvAddress*/;

            MyViewHolder(View itemView) {
                super(itemView);
//                imageView = itemView.findViewById(R.id.ivSpot);
                tvName = itemView.findViewById(R.id.tvName);
//                tvPhoneNo = itemView.findViewById(R.id.tvPhoneNo);
//                tvAddress = itemView.findViewById(R.id.tvAddress);
            }
        }

        @Override
        public int getItemCount() {
            return friends.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.fragment_community_item01, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            final CommunityFItem friend = friends.get(position);
            String url = Common.URL + "/FriendServlet";
            String first_name = friend.getFirst_name();
//            spotGetImageTask = new SpotGetImageTask(url, id, imageSize, myViewHolder.imageView);
//            spotGetImageTask.execute();
            myViewHolder.tvName.setText(friend.getFirst_name());
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
        if (friendGetAllTask != null) {
            friendGetAllTask.cancel(true);
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
