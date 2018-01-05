package com.project.pp.parentparadise;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.pp.parentparadise.utl.Common;
import com.project.pp.parentparadise.utl.SessionManager;
import com.project.pp.parentparadise.utl.MyTask;


public class LoginActivity extends AppCompatActivity {
    private final static String TAG = "LoginActivity";
    private Button btnLogin, btnLogout, btnLinkToRegister;
    private EditText inputEmail, inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private MyTask loginTask;
    private int loginMemberNo = -1;
//    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lgoin);
        //設定隱藏標題
        getSupportActionBar().hide();
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        findViews();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    Boolean isLogin = checkLogin(email, password);
                    if (isLogin) {
                        // Login Ok ! Keep login member info in session, and forward to mainactivity
                        session.setLogin(isLogin, email, password, loginMemberNo);

                        Common.showToast(LoginActivity.this, "login Ok !");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //Common.showToast(LoginActivity.this, "login fail !");
                        inputPassword.setText("");
                        inputPassword.setFocusable(true);
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Please enter the email and pasword!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Logout Screen
        btnLogout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            session = null;
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            }
        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
            }
        });
    }

    private boolean checkLogin(String userId, String password) {
        // TO DO Check WebLogin
        if (Common.networkConnected(this)) {
            String url = Common.URL + "/LoginServlet";
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("mail", userId);
                jsonObject.addProperty("password", password);
                String jsonOut = jsonObject.toString();

                loginTask = new MyTask(url, jsonOut);
                boolean isUserValid = false;
                String jsonIn = loginTask.execute().get();
                Log.d(TAG, jsonIn);
                Gson gson = new Gson();
                jsonObject = gson.fromJson(jsonIn, JsonObject.class);
                isUserValid = jsonObject.get("isUserValid").getAsBoolean();
                loginMemberNo = jsonObject.get("memberNo").getAsInt();

                if (!isUserValid) {
                    // Error in login. Get the error message
                    String errorMsg = jsonObject.get("error_msg").getAsString();
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }

                return isUserValid;
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
        return false;
    }

    private void findViews() {
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);
        btnLinkToRegister = findViewById(R.id.btnLinkToRegisterScreen);
    }
}
