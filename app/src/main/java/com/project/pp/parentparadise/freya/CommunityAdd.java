package com.project.pp.parentparadise.freya;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.project.pp.parentparadise.R;
import com.project.pp.parentparadise.ppmain.Member;
import com.project.pp.parentparadise.utl.Common;
import com.project.pp.parentparadise.utl.MyTask;

import java.lang.reflect.Type;
import java.util.List;

public class CommunityAdd extends Activity {

    private static final String TAG = "MainActivity";
    private EditText searchId;
    private Button btSubmit;
    private TextView tvShow;
    private ImageView ivUp_logo;
    private ImageView ivButtom_logo;
    //private SessionManager session;
    public View view;
    String acc_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_community_add);
////        searchId = view.findViewById(R.id.searchId);
////        btSubmit = view.findViewById(R.id.btSubmit);
        findViews();
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String acc_code = searchId.getText().toString().trim();
                if (acc_code.isEmpty()) {
                    Toast.makeText(CommunityAdd.this,
                            R.string.text_InvalidName,
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                // jsonObject.addProperty("memberList", new Gson().toJson(memberList));
                if (networkConnected()) {
                    String url = Common.URL + "/SearchMemberServlet";
                    List<MemberSearch> members = null;
                    JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("action", "getAll");
                    jsonObject.addProperty("acc_code", acc_code);
                    MyTask myTask = new MyTask(url, jsonObject.toString());

                    try {
                        String inStr = myTask.execute().get();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<MemberSearch>>(){}.getType();
                        members = gson.fromJson(inStr, listType);
                        //int count = Integer.parseInt(inStr);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (members == null || members.isEmpty()) {

                    } else {
                        tvShow.setText(members.get(0).getFirst_name());
                    }
                }
            }
        });
    }


    private boolean networkConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo.isConnected();
        } else {
            return false;
        }
    }

//    private void showAccCode(String showAccCode) {
//        if (Common.networkConnected(this)) {
//            String url = Common.URL + "/MemberServlet";
//            List <MemberSearch> friends = null;
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("action", "getAll");
//            // relation_member_no value:1 指id為1 ，到時候combine 登入帳號  value要修改為登錄帳號
//           // jsonObject.addProperty("acc_code", "getall");
//            String jsonOut = jsonObject.toString();
//            MyTask myTask  = new MyTask(url, jsonOut);
//            try {
//                String jsonIn = myTask.execute().get();
//                Log.d(TAG, jsonIn);
//                Type listType = new TypeToken<List <Member>>() {
//                }.getType();
//                friends = new Gson().fromJson(jsonIn, listType);
//            } catch (Exception e) {
//                Log.e(TAG, e.toString());
//            }
//            if (showAccCode == null || showAccCode.isEmpty()) {
//                Common.showToast(this, R.string.msg_NoIDFound);
//            } else {
//                Common.showToast(this, R.id.tvshow);
//
//                // rvFriends.setAdapter(new FriendsRecyclerViewAdapter(getActivity(), friends));
//            }
//        } else {
//            Common.showToast(this, R.string.msg_NoDataFound);
//        }
//    }
//

//    private boolean networkConnected() {
//        ConnectivityManager connectivityManager =
//                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            return networkInfo.isConnected();
//        } else {
//            return false;
//        }
//    }

    private void findViews() {
        searchId = findViewById(R.id.tvSearchid);
        btSubmit = findViewById(R.id.btSubmit);
        tvShow= findViewById(R.id.tvShow);
        ivUp_logo=findViewById(R.id.up_logo);
        ivButtom_logo=findViewById(R.id.bottom_logo);

    }
}