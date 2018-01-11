package com.project.pp.parentparadise.amber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.pp.parentparadise.MainActivity;
import com.project.pp.parentparadise.R;

public class FavoriteFragmentAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_recyclerview);
        setTitle("我的收藏-親子活動");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id",2);
        startActivity(intent);
    }
}
