package com.project.pp.parentparadise.amber;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.pp.parentparadise.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ShareAddMessage extends AppCompatActivity {
    ScrollView scrollViewMessage;
    LinearLayout llMessage;
    ImageView ivMHead;
    EditText etMessage;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_add_message_main);
        setTitle("分享留言");
        findViews();
    }

    private void findViews() {
        llMessage = findViewById(R.id.llMessage);
        scrollViewMessage = findViewById(R.id.scrollViewMessage);
        etMessage = findViewById(R.id.etMessage);
        ivMHead = findViewById(R.id.ivMHead);
        ivMHead.setImageResource(R.drawable.amber_icon_head01);
    }


    ImageView ivHead;
    TextView tvName, tvTime, tvDescribe, tvGoodCount;
    public void OnMessageSendClick(View view) {
        view = View.inflate(this, R.layout.share_add_message_itemview1, null);

        ivHead = view.findViewById(R.id.ivHead);
        tvName = view.findViewById(R.id.tvName);
        tvTime = view.findViewById(R.id.tvTime);
        tvDescribe = view.findViewById(R.id.tvDescribe);
        tvGoodCount = view.findViewById(R.id.tvGCount);
        ivHead.setImageResource(R.drawable.amber_icon_head01);
        tvName.setText("Ana");

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        tvTime.setText(date);

        String msg = etMessage.getText().toString().trim();
        tvDescribe.setText(msg);
        if (msg.isEmpty()) {
            Toast toast = Toast.makeText(this, "請輸入訊息", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        } else {
            llMessage.addView(view);
            scrollViewMessage.post(new Runnable() {
                @Override
                public void run() {
                    scrollViewMessage.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    }

    public void OnReplyClick(View view) {
        String tagName ="@" + tvName.getText().toString();
        etMessage.setText(tagName);
        etMessage.setSelection(tagName.length());
    }
}

