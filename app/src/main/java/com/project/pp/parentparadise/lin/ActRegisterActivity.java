package com.project.pp.parentparadise.lin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.project.pp.parentparadise.R;
import com.project.pp.parentparadise.ppmain.Member;
import com.project.pp.parentparadise.utl.SessionManager;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import com.project.pp.parentparadise.utl.Common;
import com.project.pp.parentparadise.utl.MyTask;

public class ActRegisterActivity extends AppCompatActivity {
    private final static String TAG = "ActRegister";
    private MyTask actGetItemTask;
    private ImageView ivAct;
    private TextView tvTitle, tvFee, tvCttTitle01, tvCtt01, tvCttTitle02, tvCtt02, tvCttTitle03,
                    tvCtt03, tvCttTitle04, tvCtt04, tvCttTitle05, tvCtt05;
    private Button btRegister;
    private int id, itemNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_register);
        Bundle bundle = this.getIntent().getExtras();
        //act = (Act) bundle.getSerializable("act");
        id = bundle.getInt("id");
        findViews();
        showItem();
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertReg();
                finish();
            }
        });
    }

    private void insertReg() {
        SessionManager session = new SessionManager(getApplicationContext());
        Member loginMember = session.getLoginMemberInfo();
        int member_no = loginMember.getMember_no();

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String postDate = sDateFormat.format(new java.util.Date());


        if (Common.networkConnected(this)) {
            String url = Common.URL + "/ActItemServelet";
            ActItem actItem = new ActItem(id, itemNo, member_no, 2,postDate, 0);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "addActReg");
            jsonObject.addProperty("actItem", new Gson().toJson(actItem));

            int count = 0;
            actGetItemTask = new MyTask(url, jsonObject.toString());

            try {
                String result = actGetItemTask.execute().get();
                count = Integer.valueOf(result);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (count == 0) {
                Common.showToast(this, "報名失敗");
                //Common.showToast(this, "NoItemFound");
            } else {
                Common.showToast(this, "報名成功");
            }
        } else {
            Common.showToast(this, "NoNetwork");
        }
    }

    private void showItem() {
        if (Common.networkConnected(this)) {
            String url = Common.URL + "/ActItemServelet";
            ActItem actItem = null;
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getItem");
                jsonObject.addProperty("id", id);
                String jsonOut = jsonObject.toString();
                actGetItemTask = new MyTask(url, jsonOut);
                String jsonIn = actGetItemTask.execute().get();
                Log.d(TAG, jsonIn);
                Gson gson = new Gson();
                Type type = new TypeToken<ActItem>(){ }.getType();
                actItem = gson.fromJson(jsonIn, type);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (actItem == null) {
                Common.showToast(this, "NoItemFound");
            } else {
                itemNo = actItem.getItemNo();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                byte[] image = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    image = Base64.getMimeDecoder().decode(actItem.getActPic());
                }

                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                //ImageView imageView = new ImageView(ConversationsActivity.this);
                // Set the Bitmap data to the ImageView
                ivAct.setImageBitmap(bmp);

                tvTitle.setText(actItem.getActivityTitle());
                tvFee.setText("$" + String.valueOf(actItem.getActivityFee()));
                tvCttTitle01.setText("截止日期");
                tvCtt01.setText(dateFormat.format(new Date(actItem.getRegDuedate())));
                tvCttTitle02.setText("活動日期");
                tvCtt02.setText(dateFormat.format(new Date(actItem.getActivityStartdate())));
                tvCttTitle03.setText("活動人數");
                tvCtt03.setText(String.valueOf(actItem.getActivityCount()));
                tvCttTitle04.setText("地點");
                tvCtt04.setText(actItem.getPlaceDesc());
                tvCttTitle05.setText("地址");
                tvCtt05.setText(actItem.getPlaceCityName() + actItem.getPlaceDistName() + actItem.getAddRoad());
            }
        } else {
            Common.showToast(this, "NoNetwork");
        }
    }

    private void findViews() {
        ivAct = findViewById(R.id.ivAct);
        tvTitle = findViewById(R.id.tvTitle);
        tvFee = findViewById(R.id.tvFee);
        tvCttTitle01 = findViewById(R.id.tvCttTitle01);
        tvCtt01 = findViewById(R.id.tvCtt01);
        tvCttTitle02 = findViewById(R.id.tvCttTitle02);
        tvCtt02 = findViewById(R.id.tvCtt02);
        tvCttTitle03 = findViewById(R.id.tvCttTitle03);
        tvCtt03 = findViewById(R.id.tvCtt03);
        tvCttTitle04 = findViewById(R.id.tvCttTitle04);
        tvCtt04 = findViewById(R.id.tvCtt04);
        tvCttTitle05 = findViewById(R.id.tvCttTitle05);
        tvCtt05 = findViewById(R.id.tvCtt05);
        btRegister = findViewById(R.id.btRegister);
    }
}
