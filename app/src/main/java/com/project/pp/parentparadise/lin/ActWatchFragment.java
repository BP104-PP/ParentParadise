package com.project.pp.parentparadise.lin;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.project.pp.parentparadise.R;
import com.project.pp.parentparadise.ppmain.Member;
import com.project.pp.parentparadise.utl.SessionManager;

import java.lang.reflect.Type;
import java.util.List;
import com.project.pp.parentparadise.utl.Common;
import com.project.pp.parentparadise.utl.MyTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActWatchFragment extends Fragment {
    private final static String TAG = "ActListFragment";
    private MyTask actListGetAllTask;
    private ActGetImageTask actGetImageTask;
    private RecyclerView rvWatchList;
    private int member_no;
    private String goodType;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activities_watch, container, false);
        rvWatchList = view.findViewById(R.id.rvWatchList);
        rvWatchList.setLayoutManager(
                new StaggeredGridLayoutManager(
                        2, StaggeredGridLayoutManager.VERTICAL));
        showWatchActs();

        return view;
    }

    private void showWatchActs() {
        SessionManager session = new SessionManager(getActivity().getApplicationContext());
        Member loginMember = session.getLoginMemberInfo();
        member_no = loginMember.getMember_no();
        goodType = "A";
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/ActServlet";
            List<Act> actsList = null;
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getWatch");
                jsonObject.addProperty("memberNumber", member_no);
                jsonObject.addProperty("goodType", goodType);
                String jsonOut = jsonObject.toString();
                actListGetAllTask = new MyTask(url, jsonOut);
                String jsonIn = actListGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Act>>(){ }.getType();
                actsList = gson.fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (actsList == null || actsList.isEmpty()) {
                Common.showToast(getActivity(), "尚無關注清單");
            }else{
                rvWatchList.setAdapter(new ActListAdapter(getActivity(), actsList));
            }
        } else {
            Common.showToast(getActivity(), "NoNetwork");
        }
    }

    private class ActListAdapter extends
            RecyclerView.Adapter<ActListAdapter.MyViewHolder> {
        private Context context;
        private List<Act> ActList;
        private int imageSize;

        ActListAdapter(Context context, List<Act> ActList){
            this.context = context;
            this.ActList = ActList;
            imageSize = getResources().getDisplayMetrics().widthPixels/4;
            //imageSize = 10000000;

        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivActImage, ivLike, ivWatch, ivMsg;
            TextView tvTitle, tvLike, tvWatch, tvMsg;
            Button btRegister;
            RelativeLayout rlBt1, rlBt2, rlBt3;

            MyViewHolder(View itemView) {
                super(itemView);
                ivActImage = (ImageView) itemView.findViewById(R.id.ivActImage);
                ivLike = (ImageView) itemView.findViewById(R.id.ivLike);
                ivWatch = (ImageView) itemView.findViewById(R.id.ivWatch);
                ivMsg = (ImageView) itemView.findViewById(R.id.ivMsg);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvLike = (TextView) itemView.findViewById(R.id.tvLike);
                tvWatch = (TextView) itemView.findViewById(R.id.tvWatch);
                tvMsg = (TextView) itemView.findViewById(R.id.tvMsg);
                btRegister = (Button) itemView.findViewById(R.id.btRegister);
                rlBt1 = (RelativeLayout) itemView.findViewById(R.id.rlBt1);
                rlBt2 = (RelativeLayout) itemView.findViewById(R.id.rlBt2);
                rlBt3 = (RelativeLayout) itemView.findViewById(R.id.rlBt3);
            }
        }

        @Override
        public int getItemCount() {
            return ActList.size();
        }
        @Override
        public ActListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.activity_item_view, viewGroup, false);
            return new ActListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ActListAdapter.MyViewHolder viewHolder, int position) {
            final Act act = ActList.get(position);
            String url = Common.URL + "/ActServlet";
            final int id = act.getActivityId();
            actGetImageTask = new ActGetImageTask(url, id, imageSize, viewHolder.ivActImage);
            actGetImageTask.execute();

            viewHolder.tvTitle.setText(act.getActivityTitle());
            viewHolder.tvLike.setText(Integer.toString(act.getGoodCount()));
            viewHolder.tvWatch.setText(Integer.toString(act.getCollectionCount()));
            //viewHolder.tvMsg.setText(Integer.toString(act.getMsg()));

            viewHolder.btRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ActRegisterActivity.class);
                    startActivity(intent);
                }
            });

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ActDetail.class);

                    Bundle bundle = new Bundle();
                    //bundle.putSerializable("act", act);
                    bundle.putInt("id", id);

                    intent.putExtras(bundle);

                    startActivity(intent);
                }
            });

            viewHolder.rlBt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.ivLike.setImageResource(R.drawable.lin_good);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (actListGetAllTask != null) {
            actListGetAllTask.cancel(true);
        }

        if (actGetImageTask != null) {
            actGetImageTask.cancel(true);
        }
    }
}
