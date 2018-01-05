package com.project.pp.parentparadise;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.pp.parentparadise.utl.*;

import com.project.pp.parentparadise.utl.SessionManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private final static String TAG = "RegisterActivity";
    private EditText etAccCode, etLastName, etFirstName, etEmail, etPassword;
    private Button btnRegister, btnLinkToLogin;
    private ImageView ivTakePicture;
    private ProgressDialog pDialog;
    private SessionManager session;
    private static final int PHOTO_REQUEST_GALLERY = 1;
    private static final int PHOTO_REQUEST_CUT = 3;
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //設定隱藏標題
        getSupportActionBar().hide();
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        findViews();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Register button Click Event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> msgList = new ArrayList<>();
                String accCode = etAccCode.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String firstName = etFirstName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (accCode.isEmpty())
                    msgList.add("使用者代號");

                if (lastName.isEmpty())
                    msgList.add("會員-姓");

                if (firstName.isEmpty())
                    msgList.add("會員-名字");

                if (email.isEmpty())
                    msgList.add("註冊 Mail");

                if (password.isEmpty())
                    msgList.add("密碼");

                if (msgList.size() > 0) {
                    String warmMsg = "請輸入完整" + Common.joinArrayListString(msgList, "、") + '！';
                    Toast.makeText(RegisterActivity.this,
                            warmMsg, Toast.LENGTH_LONG)
                            .show();
                } else {
                    Boolean rc = doRegister();
                    if (rc) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

        // Link to Register Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean doRegister() {
        boolean rc = false;
        if (Common.networkConnected(this)) {
            String url = Common.URL + "/RegisterServlet";
            String accCode = etAccCode.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String firstName = etFirstName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            ivTakePicture.setDrawingCacheEnabled(true);
            Bitmap bitmap = ivTakePicture.getDrawingCache();
            byte[] image = Common.bitmapToPNG(bitmap);
            ivTakePicture.setDrawingCacheEnabled(false); // 這行必須在bitmap處理完後再執行，否則bitmap會被回收掉

            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("accCode", accCode);
                jsonObject.addProperty("lastName", lastName);
                jsonObject.addProperty("firstName", firstName);
                jsonObject.addProperty("mail", email);
                jsonObject.addProperty("password", password);
                jsonObject.addProperty("headImage", Base64.encodeToString(image, Base64.DEFAULT));

                String jsonOut = jsonObject.toString();
                MyTask myTask = new MyTask(url, jsonOut);
                String jsonIn = myTask.execute().get();
                Log.d(TAG, jsonIn);

                Gson gson = new Gson();
                jsonObject = gson.fromJson(jsonIn, JsonObject.class);
                rc = jsonObject.get("return_value").getAsBoolean();

                String msg = jsonObject.get("return_msg").getAsString();
                Toast.makeText(getApplicationContext(),
                        msg, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
        return rc;
    }

    private void findViews() {
        ivTakePicture = findViewById(R.id.ivTakePicture);
        etAccCode = findViewById(R.id.acc_code);
        etLastName = findViewById(R.id.last_name);
        etFirstName = findViewById(R.id.first_name);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btnRegister);
        btnLinkToLogin = findViewById(R.id.btnLinkToLogin);
    }

    public void onTakePictureClick(View view) {
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(intent, PICK_FROM_GALLERY);

        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "存取相簿失敗!",
                    Toast.LENGTH_LONG).show();
        }
    }

    // 封裝請求Gallery的intent
    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("return-data", true);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //Bitmap photo = data.getParcelableExtra("data");

                Uri uri = data.getData();
                try {
                    photo = BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "PHOTO_REQUEST_GALLERY: " + photo.toString());
                ivTakePicture.setImageBitmap(photo);
            }
        }
    }
}
