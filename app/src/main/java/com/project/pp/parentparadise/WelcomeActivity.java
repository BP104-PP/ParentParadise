package com.project.pp.parentparadise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.project.pp.parentparadise.utl.SessionManager;

/**
 * Created by chungnan on 2017/12/22.
 */

public class WelcomeActivity extends AppCompatActivity {
    private static SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //設定隱藏標題
        getSupportActionBar().hide();
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        ImageView ivWelcome = findViewById(R.id.ivWelcome);
        session = new SessionManager(getApplicationContext());
        //session.setLogin(false);

        ivWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session != null && session.isLoggedIn()) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                WelcomeActivity.this.finish();
            }
        });
    }

    public void entryActivity(View view) {

    }
}
