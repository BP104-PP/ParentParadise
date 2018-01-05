package com.project.pp.parentparadise.lin;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.project.pp.parentparadise.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import com.project.pp.parentparadise.utl.Common;
import com.project.pp.parentparadise.utl.MyTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActDetail extends AppCompatActivity {
    private final static String TAG = "ActDetail";
    private MyTask actGetItemTask;
    ImageView ivAct;
    CircleImageView ivVip;
    TextView tvTitle, tvPrice, tvVipName, tvVipHistory, tvVipRanking, tvAvtDescribe, tvFollow, tvMsg
            , tvCttTitle01, tvCtt01, tvCttTitle02, tvCtt02, tvCttTitle03, tvCtt03, tvCttTitle04, tvCtt04
            , tvCttTitle05, tvCtt05;
    ImageButton ibtFollow, ibtQandA, ibtRegister;
    RelativeLayout rlVipDetail;
    private int id, memberNo;
    private String mamberName;
    private Act act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail);

        Bundle bundle = this.getIntent().getExtras();
        //act = (Act) bundle.getSerializable("act");
        id = bundle.getInt("id");


        findViews();
        showItem();
        //ivAct.setImageResource(R.drawable.lin_slide_image01);
        ibtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActDetail.this, ActRegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rlVipDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActDetail.this, PublisherActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putSerializable("act", act);
                bundle.putInt("memberNo", memberNo);
                bundle.putString("memberName", mamberName);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
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
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                mamberName = actItem.getMemberName();
                memberNo = actItem.getMemberNo();

                byte[] image = null;
                byte[] imageMember = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    image = Base64.getMimeDecoder().decode(actItem.getActPic());
                    imageMember = Base64.getMimeDecoder().decode(actItem.getActVipPic());
                }

                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                Bitmap bmpMember = BitmapFactory.decodeByteArray(imageMember, 0, imageMember.length);
                //ImageView imageView = new ImageView(ConversationsActivity.this);
                // Set the Bitmap data to the ImageView
                ivAct.setImageBitmap(bmp);
                ivVip.setImageBitmap(bmpMember);
                tvTitle.setText(actItem.getActivityTitle());
                tvPrice.setText("$" + String.valueOf(actItem.getActivityFee()));
                tvFollow.setText(String.valueOf(actItem.getCollectionCount()));
                tvVipName.setText(actItem.getMemberName());
                tvVipHistory.setText("活動：" + String.valueOf(actItem.getMemberActCount()));
                tvVipRanking.setText("評價：" + "0");
                tvAvtDescribe.setText(actItem.getActivityDescribe());
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
        ivVip = findViewById(R.id.ivVip);
        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvVipName = findViewById(R.id.tvVipName);
        tvVipHistory = findViewById(R.id.tvVipHistory);
        tvVipRanking = findViewById(R.id.tvVipRanking);
        tvAvtDescribe = findViewById(R.id.tvAvtDescribe);
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
        ibtFollow = findViewById(R.id.ibtFollow);
        ibtQandA = findViewById(R.id.ibtQandA);
        ibtRegister = findViewById(R.id.ibtRegister);
        tvFollow = findViewById(R.id.tvFollow);
        tvMsg = findViewById(R.id.tvMsg);
        rlVipDetail = findViewById(R.id.rlVipDetail);
    }
}
