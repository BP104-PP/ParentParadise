package com.project.pp.parentparadise.amber;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.project.pp.parentparadise.R;
import com.project.pp.parentparadise.utl.Common;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.graphics.Color.rgb;

/**
 * Created by Amber on 2017/12/5.
 */

public class ShareFragmentSweet extends Fragment {
    private static final String TAG = "ShareFragmentSweet";
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView[] dots;           //點點數組合
    private ImageView mImageView;
    private ShareTask shareGetAllTask;
    private DataAdapter dataAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.share_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        swipeRefreshLayout = view.findViewById(R.id.swipeSweet);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showAllShareData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllShareData();
    }

    private void showAllShareData() {
        if (ShareCommon.networkConnected(getActivity())) {
            String url = ShareCommon.URL + "/ShareServlet";
            List<ShareData> shareList = null;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getAll");
            String jsonOut = jsonObject.toString();
            shareGetAllTask = new ShareTask(url, jsonOut);
            try {
                String jsonIn = shareGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<List<ShareData>>() {
                }.getType();
                shareList = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (shareList == null || shareList.isEmpty()) {
                ShareCommon.showToast(getActivity(), "無分享文章");
            } else {
                if (dataAdapter != null) {
                    dataAdapter.notifyDataSetChanged();
                } else {
                    dataAdapter = new DataAdapter(getActivity(), shareList);
                    recyclerView.setAdapter(dataAdapter);
                }
            }
        } else {
            ShareCommon.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }

    class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
        private Context context;
        private List<ShareData> dataList;
        private int imageSize;

        DataAdapter(Context context, List<ShareData> dataList) {
            this.context = context;
            this.dataList = dataList;
            imageSize = getResources().getDisplayMetrics().widthPixels / 4;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivHead;
            TextView tvName, tvTime, tvDescribe, tvMCount, tvGCount, tvMore, tvMIcon;
            RecyclerView rvPhotoList;

            MyViewHolder(View itemView) {
                super(itemView);
                ivHead = itemView.findViewById(R.id.ivHead);
                tvName = itemView.findViewById(R.id.tvName);
                tvTime = itemView.findViewById(R.id.tvTime);
                rvPhotoList = itemView.findViewById(R.id.rvPhotoList);
                tvDescribe = itemView.findViewById(R.id.tvDescribe);
                tvMCount = itemView.findViewById(R.id.tvMCount);
                tvGCount = itemView.findViewById(R.id.tvGCount);
                tvMore = itemView.findViewById(R.id.tvMore);
                tvMIcon = itemView.findViewById(R.id.tvMIcon);
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.share_itemview_sweet, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final ShareData data = dataList.get(position);
            String url = ShareCommon.URL + "/ShareServlet";
            int memberNo = data.getMemberNo();
            ShareGetHeadPhotoTask shareGetHeadTask = new ShareGetHeadPhotoTask(url, memberNo, imageSize, holder.ivHead);
            shareGetHeadTask.execute();

            holder.tvName.setText(data.getLastName() + data.getFirstName());

            holder.tvTime.setText(data.getPostDate());

            if (data.getContent().length() > 13) {
                holder.tvMore.setVisibility(View.VISIBLE);
                holder.tvDescribe.setText(data.getContent().substring(0, 12));
            } else {
                holder.tvDescribe.setText(data.getContent());
                holder.tvMore.setVisibility(View.GONE);
            }

            holder.tvGCount.setText(String.valueOf(data.getGoodCount()));
            holder.tvMCount.setText(String.valueOf(data.getMessageCount()));

            holder.tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tvMore.setVisibility(View.GONE);
                    holder.tvDescribe.setText(data.getContent());
                }
            });

            holder.tvMIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ShareAddMessage.class);
                    startActivity(intent);
                }
            });

            holder.rvPhotoList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

            List<ShareData> photoList = null;

            if (ShareCommon.networkConnected(getActivity())) {
                int shareId = data.getShareId();
                url = ShareCommon.URL + "/ShareServlet";
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getPhotoList");
                jsonObject.addProperty("shareId", shareId);
                String jsonOut = jsonObject.toString();
                ShareTask getPhotoListTask = new ShareTask(url, jsonOut);
                try {
                    String jsonIn = getPhotoListTask.execute().get();
                    Log.d(TAG, jsonIn);
                    Type listType = new TypeToken<List<ShareData>>() {
                    }.getType();
                    photoList = new Gson().fromJson(jsonIn, listType);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            } else {
                ShareCommon.showToast(getActivity(), R.string.msg_NoNetwork);
            }

            if (photoList.size() != 0) {
                holder.rvPhotoList.setAdapter(new PhotoAdapter(getActivity(), photoList));
                holder.rvPhotoList.setOnFlingListener(null);
                PagerSnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(holder.rvPhotoList);

            } else {
                holder.rvPhotoList.setVisibility(View.GONE);
                holder.rvPhotoList.setOnFlingListener(null);
            }
        }

        class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
            private Context context;
            private List<ShareData> photoList;
            private int imageSize;

            PhotoAdapter(Context context, List<ShareData> photoList) {
                this.context = context;
                this.photoList = photoList;
                imageSize = getResources().getDisplayMetrics().widthPixels;
            }

            class MyViewHolder extends RecyclerView.ViewHolder {
                ImageView ivPhoto;
                LinearLayout llviewGroup;

                MyViewHolder(View itemView) {
                    super(itemView);
                    ivPhoto = itemView.findViewById(R.id.ivPhoto);
                    llviewGroup = itemView.findViewById(R.id.llviewGroup);
                }
            }

            @Override
            public int getItemCount() {
                return photoList.size();
            }

            @Override
            public PhotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View itemView = layoutInflater.inflate(R.layout.share_itemview_photo, viewGroup, false);
                return new PhotoAdapter.MyViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(MyViewHolder photoHolder, int position) {
                final ShareData photoData = photoList.get(position);
                String url = ShareCommon.URL + "/ShareServlet";
                int photoNo = photoData.getPhotoNo();
                ShareGetPhotoTask shareGetPhotoTask = new ShareGetPhotoTask(url, photoNo, imageSize, photoHolder.ivPhoto);
                shareGetPhotoTask.execute();
                dots = new ImageView[photoList.size()];
                for (int i = 0; i < photoList.size(); i++) {
                    mImageView = new ImageView(getActivity());
                    dots[i] = mImageView;
                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams
                            (new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                                    LayoutParams.WRAP_CONTENT));
                    ll.rightMargin = 6;
                    ll.leftMargin = 6;
                    ll.bottomMargin = 8;

                    if (i == position) {
                        mImageView.setBackgroundResource(R.drawable.amber_icon_dot_yellow);
                    } else {
                        mImageView.setBackgroundResource(R.drawable.amber_icon_dot_white);
                    }
                    photoHolder.llviewGroup.addView(mImageView, ll);
                }
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        if (shareGetAllTask != null) {
            shareGetAllTask.cancel(true);
        }
        if (shareGetAllTask != null) {
            shareGetAllTask.cancel(true);
        }
        if (shareGetAllTask != null) {
            shareGetAllTask.cancel(true);
        }
    }
}



