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
import android.widget.HorizontalScrollView;
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
import java.util.ArrayList;
import java.util.List;

import com.project.pp.parentparadise.utl.Common;
import com.project.pp.parentparadise.utl.MyTask;


public class ActListFragment extends Fragment {
    private final static String TAG = "ActListFragment";
    private MyTask actListGetAllTask, getGoodTask;
    private RecyclerView rvRecommend, rvList;
    private TextView tvSecondTitle, tvRecommendTitle;
    private Button btOutdoor, btExperience, btTheme, btGame, btSearch;
    private View view;
    private HorizontalScrollView act_scrollbar;
    private int odClicked, expClicked, themeClicked, gameClicked;
    private ActGetImageTask actGetImageTask;
    private int actType, memberNO;
    private String goodType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_activities_list, container, false);
        findViews();
        tvRecommendTitle.setVisibility(View.GONE);
        rvRecommend.setVisibility(View.GONE);
        btTabDefault();
        tvSecondTitle.setText("所有活動");
        rvRecommend.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.HORIZONTAL));
        List<ActRecommend> activitiesRecommendList = getActivitiesRecommend();
        rvRecommend.setAdapter(new ActivitiesRecommendAdapter(getActivity(), activitiesRecommendList));

        rvList.setLayoutManager(
                new StaggeredGridLayoutManager(
                        2, StaggeredGridLayoutManager.VERTICAL));



//        btOutdoor.setOnClickListener(new MyOnClickListener(odClicked, btOutdoor, 1));
//        btExperience.setOnClickListener(new MyOnClickListener(expClicked, btExperience, 2));
//        btTheme.setOnClickListener(new MyOnClickListener(themeClicked, btTheme, 3));
//        btGame.setOnClickListener(new MyOnClickListener(gameClicked, btGame, 9));
        btOutdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(odClicked == 0){
                    btTabImageDefault();
                    btOutdoor.setBackgroundResource(R.drawable.lin_tab_back_check);
                    btOutdoor.setTextColor(getResources().getColor(R.color.rbTxtChecked1));
                    actType = 1;
                    showSortActs();
                    btTabDefault();
                    odClicked = 1;
                    tvSecondTitle.setText("戶外活動");
                }else{
                    btOutdoor.setBackgroundResource(R.drawable.lin_tab_back);
                    btOutdoor.setTextColor(getResources().getColor(R.color.rbTxtFalse));
                    odClicked = 0;
                    showAllActs();
                    tvSecondTitle.setText("所有活動");
                }
            }
        });
        btExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expClicked == 0){
                    btTabImageDefault();
                    btExperience.setBackgroundResource(R.drawable.lin_tab_back_check);
                    btExperience.setTextColor(getResources().getColor(R.color.rbTxtChecked1));
                    actType = 2;
                    showSortActs();
                    btTabDefault();
                    expClicked = 1;
                    tvSecondTitle.setText("親子體驗");
                }else{
                    btExperience.setBackgroundResource(R.drawable.lin_tab_back);
                    btExperience.setTextColor(getResources().getColor(R.color.rbTxtFalse));
                    expClicked = 0;
                    showAllActs();
                    tvSecondTitle.setText("所有活動");
                }
            }
        });
        btTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(themeClicked == 0){
                    btTabImageDefault();
                    btTheme.setBackgroundResource(R.drawable.lin_tab_back_check);
                    btTheme.setTextColor(getResources().getColor(R.color.rbTxtChecked1));
                    actType = 3;
                    showSortActs();
                    btTabDefault();
                    themeClicked = 1;
                    tvSecondTitle.setText("主題活動");
                }else{
                    btTheme.setBackgroundResource(R.drawable.lin_tab_back);
                    btTheme.setTextColor(getResources().getColor(R.color.rbTxtFalse));
                    themeClicked = 0;
                    showAllActs();
                    tvSecondTitle.setText("所有活動");
                }
            }
        });
        btGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameClicked == 0){
                    btTabImageDefault();
                    btGame.setBackgroundResource(R.drawable.lin_tab_back_check);
                    btGame.setTextColor(getResources().getColor(R.color.rbTxtChecked1));
                    actType = 9;
                    showSortActs();
                    btTabDefault();
                    gameClicked = 1;
                    tvSecondTitle.setText("其他");
                }else{
                    btGame.setBackgroundResource(R.drawable.lin_tab_back);
                    btGame.setTextColor(getResources().getColor(R.color.rbTxtFalse));
                    gameClicked = 0;
                    showAllActs();
                    tvSecondTitle.setText("所有活動");
                }
            }
        });
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActSearchActivity.class);
                startActivity(intent);
            }
        });
        act_scrollbar.setHorizontalScrollBarEnabled(false);

        showAllActs();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //showAllActs();
    }

    private void showAllActs() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/ActServlet";
            List<Act> actsList = null;
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getAll");
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
                Common.showToast(getActivity(), "NoActsFound");
            } else {
                rvList.setAdapter(new ActListAdapter(getActivity(), actsList));
            }
        } else {
            Common.showToast(getActivity(), "NoNetwork");
        }
    }

    private void showSortActs() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/ActServlet";
            List<Act> actsList = null;
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getSort");
                jsonObject.addProperty("actType", actType);
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
                Common.showToast(getActivity(), "NoActsFound");
            } else {
                rvList.setAdapter(new ActListAdapter(getActivity(), actsList));
            }
        } else {
            Common.showToast(getActivity(), "NoNetwork");
        }
    }

    private void btTabDefault() {
        odClicked = 0;
        expClicked = 0;
        themeClicked = 0;
        gameClicked = 0;
    }
    private void btTabImageDefault() {
        btOutdoor.setBackgroundResource(R.drawable.lin_tab_back);
        btOutdoor.setTextColor(getResources().getColor(R.color.rbTxtFalse));
        btExperience.setBackgroundResource(R.drawable.lin_tab_back);
        btExperience.setTextColor(getResources().getColor(R.color.rbTxtFalse));
        btTheme.setBackgroundResource(R.drawable.lin_tab_back);
        btTheme.setTextColor(getResources().getColor(R.color.rbTxtFalse));
        btGame.setBackgroundResource(R.drawable.lin_tab_back2);
        btGame.setTextColor(getResources().getColor(R.color.rbTxtFalse));
    }

//    class MyOnClickListener implements View.OnClickListener {
//        private int checked;
//        private Button button;
//        private int typeInt;
//        public MyOnClickListener(int checked, Button button, int typeInt){
//            this.checked = checked;
//            this.button = button;
//            this.typeInt = typeInt;
//        }
//
//
//        public void onClick(View v){
//            if(checked == 0){
//                btTabImageDefault();
//                button.setBackgroundResource(R.drawable.lin_tab_back_check);
//                button.setTextColor(getResources().getColor(R.color.rbTxtChecked1));
//                actType = typeInt;
//                showSortActs();
//                btTabDefault();
//                checked = 1;
//            }else{
//                button.setBackgroundResource(R.drawable.lin_tab_back);
//                button.setTextColor(getResources().getColor(R.color.rbTxtFalse));
//                checked = 0;
//                showAllActs();
//            }
//        }
//    }

    private void findViews() {
        rvRecommend = (RecyclerView) view.findViewById(R.id.rvRecommend);
        rvList = (RecyclerView) view.findViewById(R.id.rvList);
        btSearch = (Button) view.findViewById(R.id.btSearch);
        act_scrollbar = (HorizontalScrollView) view.findViewById(R.id.act_scrollbar);
        btOutdoor = (Button) view.findViewById(R.id.btOutdoor);
        btExperience = (Button) view.findViewById(R.id.btExperience);
        btTheme = (Button) view.findViewById(R.id.btTheme);
        btGame = (Button) view.findViewById(R.id.btGame);
        tvSecondTitle = (TextView) view.findViewById(R.id.tvSecondTitle);
        tvRecommendTitle = (TextView) view.findViewById(R.id.tvRecommendTitle);
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

//            News news = newsList.get(position);
//            String title = news.getFormatedDate() + " " + news.getTitle();
//            viewHolder.tvNewsTitle.setText(title);
//            viewHolder.tvNewsDetail.setText(act.getDetail());
//            viewHolder.tvNewsDetail.setVisibility(
//                    newsExpanded[position] ? View.VISIBLE : View.GONE);
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    expand(viewHolder.getAdapterPosition());
//                }
//            });


            //viewHolder.ivActImage.setImageResource(act.getImage());
//            double goodCount = 0;
//            String s = "";
//            if(act.getGoodCount() > 999){
//                goodCount = (double)act.getGoodCount() / 1000;
//                //DecimalFormat df = new DecimalFormat("00.0");
//                s = String.format("%.1f",goodCount) + "k";
//            }else{
//                s = Integer.toString(act.getGoodCount());
//            }
            viewHolder.tvTitle.setText(act.getActivityTitle());
            viewHolder.tvLike.setText(Integer.toString(act.getGoodCount()));
            viewHolder.tvWatch.setText(Integer.toString(act.getCollectionCount()));
            //viewHolder.tvMsg.setText(Integer.toString(act.getMsg()));

            viewHolder.btRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ActRegisterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);

                    intent.putExtras(bundle);
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

            viewHolder.rlBt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SessionManager session = new SessionManager(getActivity().getApplicationContext());
                    Member loginMember = session.getLoginMemberInfo();
                    memberNO = loginMember.getMember_no();
                    goodType = "A";
                    if (Common.networkConnected(getActivity())) {
                        String url = Common.URL + "/GoodServlet";
                        int goodChecked = 0;
                        try {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("action", "checkGood");
                            jsonObject.addProperty("memberNo", memberNO);
                            jsonObject.addProperty("actID", act.getActivityId());
                            jsonObject.addProperty("goodType", goodType);
                            String jsonOut = jsonObject.toString();
                            getGoodTask = new MyTask(url, jsonOut);
                            String jsonIn = getGoodTask.execute().get();
                            Log.d(TAG, jsonIn);
//                            Gson gson = new Gson();
//                            Type intType = new TypeToken<INT>(){ }.getType();
                            goodChecked = Integer.parseInt(jsonIn);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                        System.out.println("結果" + goodChecked);
                        if (goodChecked == 10 ) {
                            viewHolder.ivWatch.setImageResource(R.drawable.lin_heart);
                            viewHolder.tvWatch.setText(String.valueOf(act.getCollectionCount() + 1));
                        } else {
                            viewHolder.ivWatch.setImageResource(R.drawable.lin_heart_boder);
                            viewHolder.tvWatch.setText(String.valueOf(act.getCollectionCount() - 1));
                        }
                    } else {
                        Common.showToast(getActivity(), "NoNetwork");
                    }
                }
            });
        }
    }

    private class ActivitiesRecommendAdapter extends
            RecyclerView.Adapter<ActivitiesRecommendAdapter.MyViewHolder> {
        private Context context;
        private List<ActRecommend> activitiesRecommendList;

        ActivitiesRecommendAdapter(Context context, List<ActRecommend> activitiesRecommendList) {
            this.context = context;
            this.activitiesRecommendList = activitiesRecommendList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivActivitiesRecommendImage;
            TextView tvRecommendTitle;

            MyViewHolder(View itemView) {
                super(itemView);
                ivActivitiesRecommendImage = (ImageView) itemView.findViewById(R.id.ivActivitiesRecommendImage);
                tvRecommendTitle = (TextView) itemView.findViewById(R.id.tvRecommendTitle);
            }
        }

        @Override
        public int getItemCount() {
            return activitiesRecommendList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.activity_recommend_item_view, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            final ActRecommend activitiesRecommend = activitiesRecommendList.get(position);
            viewHolder.ivActivitiesRecommendImage.setImageResource(activitiesRecommend.getImage());
            viewHolder.tvRecommendTitle.setText(activitiesRecommend.getTitle());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private List<ActRecommend> getActivitiesRecommend() {
        List<ActRecommend> activitiesRecommendList = new ArrayList<>();
        activitiesRecommendList.add(new ActRecommend(R.drawable.lin_test01, "2018 小騎士馬術冬令營 - 小牛仔也很忙！"));
        activitiesRecommendList.add(new ActRecommend(R.drawable.lin_test01, "2018 小騎士馬術冬令營 - 小牛仔也很忙！"));
        activitiesRecommendList.add(new ActRecommend(R.drawable.lin_test01, "2018 小騎士馬術冬令營 - 小牛仔也很忙！"));
        activitiesRecommendList.add(new ActRecommend(R.drawable.lin_test01, "2018 小騎士馬術冬令營 - 小牛仔也很忙！"));
        activitiesRecommendList.add(new ActRecommend(R.drawable.lin_test01, "2018 小騎士馬術冬令營 - 小牛仔也很忙！"));
        activitiesRecommendList.add(new ActRecommend(R.drawable.lin_test01, "2018 小騎士馬術冬令營 - 小牛仔也很忙！"));
        activitiesRecommendList.add(new ActRecommend(R.drawable.lin_test01, "2018 小騎士馬術冬令營 - 小牛仔也很忙！"));

        return activitiesRecommendList;
    }



//    private List<Act> getActList() {
//        List<Act> actList = new ArrayList<>();
//        actList.add(new Act(R.drawable.lin_slide_image01, "2018 小騎士馬術冬令營 - 小牛仔也很忙！", 10, 20,30));
//        actList.add(new Act(R.drawable.lin_slide_image02, "2030 小騎士馬術冬令營 - 小牛仔也很忙！", 1, 7,122));
//        actList.add(new Act(R.drawable.lin_slide_image03, "2040 小騎士馬術冬令營 - 小牛仔也很忙！", 5, 0,14));
//        return actList;
//    }

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
