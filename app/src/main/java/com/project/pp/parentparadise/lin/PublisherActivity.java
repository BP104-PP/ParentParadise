package com.project.pp.parentparadise.lin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.pp.parentparadise.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PublisherActivity extends AppCompatActivity {
    private CircleImageView ivVip;
    private TextView tvVipName;
    private ImageButton ibtAddFriend, ibtChat;
    private int memberNo;
    private String memberName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
        Bundle bundle = this.getIntent().getExtras();
        //act = (Act) bundle.getSerializable("act");
        memberNo = bundle.getInt("memberNo");
        memberName = bundle.getString("memberName");
        findViews();
        tvVipName.setText(memberName);
    }

    private void findViews() {
        ivVip = (CircleImageView) findViewById(R.id.ivVip);
        tvVipName = (TextView) findViewById(R.id.tvVipName);
        ibtAddFriend = (ImageButton) findViewById(R.id.ibtAddFriend);
        ibtChat = (ImageButton) findViewById(R.id.ibtChat);
    }
}
