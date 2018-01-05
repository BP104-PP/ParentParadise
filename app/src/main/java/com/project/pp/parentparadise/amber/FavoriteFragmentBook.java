package com.project.pp.parentparadise.amber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.pp.parentparadise.MainActivity;
import com.project.pp.parentparadise.R;

public class FavoriteFragmentBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_recyclerview);
        setTitle("我的收藏-閱讀");

    }

}
