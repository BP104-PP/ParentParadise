package com.project.pp.parentparadise.amber;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.pp.parentparadise.R;
import com.project.pp.parentparadise.ppmain.Member;
import com.project.pp.parentparadise.utl.SessionManager;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by amberyang on 2017/12/7.
 */

public class ShareAddArticle extends AppCompatActivity {
    private static final String TAG = "ShareAddArticle";
    private Spinner spClassify1;
    private Button btScan, btPhoto;
    private EditText etAddIsbn, etDescribe;
    private String shareType;
    private LinearLayout llActMain, llBookData;
    private ScrollView svAddArticle;
    private GridView gvPhotoPicked;
    private ArrayList<String> pathList = new ArrayList<>();
    private static final int REQUEST_PICTURE = 0;
    private ShareTask addArticleTask, upLoadPhotoTask;
    int newSize = 512;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_add_article_main);
        setTitle("新增分享文章");
        findviews();
        initContent();
    }

    private void findviews() {
        spClassify1 = findViewById(R.id.spClassify1);
        btScan = findViewById(R.id.btScan);
        btPhoto = findViewById(R.id.btPhoto);
        etAddIsbn = findViewById(R.id.etAddIsbn);
        etDescribe = findViewById(R.id.etDescribe);
        llActMain = findViewById(R.id.llActMain);
        llBookData = findViewById(R.id.llBookData);
        svAddArticle = findViewById(R.id.svAddArticle);
        gvPhotoPicked = findViewById(R.id.gvPhotoPicked);
        spClassify1.setSelection(0, true);
        spClassify1.setOnItemSelectedListener(spListener1);
    }

    private void initContent() {
        llBookData.setVisibility(View.GONE);
        svAddArticle.setVisibility(View.GONE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        ShareCommon.askPermissions(this, permissions, ShareCommon.PERMISSION_READ_EXTERNAL_STORAGE);
    }

    Spinner.OnItemSelectedListener spListener1 = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            switch (pos) {
                case 0:
                    initContent();
                    break;
                case 1:
                    initContent();
                    svAddArticle.setVisibility(View.VISIBLE);
                    shareType = "S";
                    break;
                case 2:
                    initContent();
                    svAddArticle.setVisibility(View.VISIBLE);
                    shareType = "A";
                    break;
                case 3:
                    initContent();
                    llBookData.setVisibility(View.VISIBLE);
                    btScan.setVisibility(View.VISIBLE);
                    svAddArticle.setVisibility(View.VISIBLE);
                    shareType = "B";
                    break;
                case 4:
                    initContent();
                    svAddArticle.setVisibility(View.VISIBLE);
                    shareType = "M";
                    break;
                case 5:
                    initContent();
                    svAddArticle.setVisibility(View.VISIBLE);
                    shareType = "L";
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    //檢查如果user不允許使用存取外部儲存體，讓按鈕失效
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case ShareCommon.PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    btPhoto.setEnabled(true);
                } else {
                    btPhoto.setEnabled(false);
                }
                break;
        }
    }

    public void OnScanClick(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setBarcodeImageEnabled(true);
        integrator.setBeepEnabled(false);
        integrator.setCameraId(0);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("請掃描條碼或QRcode");
        integrator.initiateScan();
    }

    public void OnPhotoClick(View view) {
        // start multiple photos selector
        Intent intent = new Intent(ShareAddArticle.this, ImagesSelectorActivity.class);
        // max number of images to be selected
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 12);
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
        // pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, pathList);
        // start the selector
        startActivityForResult(intent, REQUEST_PICTURE);
    }

    public class PhotoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pathList.size();
        }

        @Override
        public Object getItem(int position) {
            return pathList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View itemView, ViewGroup parent) {
            if (itemView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(ShareAddArticle.this);
                itemView = layoutInflater.inflate(R.layout.share_add_article_itemview, parent, false);
            }

            ImageView ivPhoto = itemView.findViewById(R.id.ivPhoto);
//            ivPhoto.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

            ivPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Bitmap srcImage = BitmapFactory.decodeFile(pathList.get(position));
            Bitmap downsizedImage = ShareCommon.downSize(srcImage, newSize);
            ivPhoto.setImageBitmap(downsizedImage);

            return itemView;
        }
    }

    // 傳資料到畫面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("Received", "onActivityResult");
        if (resultCode == RESULT_OK) {
            int newSize = 512;
            switch (requestCode) {
                case REQUEST_PICTURE:
                    pathList = intent.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                    assert pathList != null;

                    StringBuilder sb = new StringBuilder();
                    sb.append(String.format("Totally %d images selected:", pathList.size())).append("\n");
                    for (String result : pathList) {
                        sb.append(result).append("\n");
                    }
                    gvPhotoPicked.setAdapter(new PhotoAdapter());
                    break;

                default:
                    IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                    if (intentResult != null && intentResult.getContents() != null) {
                        Log.d("qrcode", "intentResult.getContents() = " + intentResult.getContents());
                        etAddIsbn.setText(intentResult.getContents());
                    } else {
                        etAddIsbn.setText("Result Not Found");
                    }
                    break;
            }
        }
    }

//UpLoadData to SQL
    public void onSubmitClick(View view) {
        ShareData shareData = null;

        if (etDescribe.getText() == null && gvPhotoPicked.getCount() == 0) {
            ShareCommon.showToast(this, R.string.msg_NotUploadWithoutPicture);
        } else {
            SessionManager session = new SessionManager(getApplicationContext());
            Member loginMember = session.getLoginMemberInfo();
            int member_no = loginMember.getMember_no();

            String shareContent = etDescribe.getText().toString().trim();
            String isbn = etAddIsbn.getText().toString().trim();
            String url = ShareCommon.URL + "/ShareServlet";

            int shareId = 0;
            int photoCount = 0;

            if (ShareCommon.networkConnected(this)) {
                shareData = new ShareData(member_no, shareType, shareContent, isbn);
                JsonObject jsonDataObject = new JsonObject();
                jsonDataObject.addProperty("action", "addArticle");
                jsonDataObject.addProperty("shareData", new Gson().toJson(shareData));

                addArticleTask = new ShareTask(url, jsonDataObject.toString());
                try {
                    String data = addArticleTask.execute().get();
                    shareId = Integer.valueOf(data);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }

            } else {
                ShareCommon.showToast(this, R.string.msg_NoNetwork);
            }

            if (ShareCommon.networkConnected(this)) {
                if (pathList.size() > 0) {
                    for (int i = 0; i < pathList.size(); i++) {

                        Bitmap bitmapImage = BitmapFactory.decodeFile(pathList.get(i));
                        Bitmap downsizedImage = ShareCommon.downSize(bitmapImage, newSize);
                        byte[] byteImage = ShareCommon.bitmapToPNG(downsizedImage);
                        String ImageBase64 = Base64.encodeToString(byteImage, Base64.DEFAULT);

                        JsonObject jsonImageObject = new JsonObject();
                        jsonImageObject.addProperty("action", "upLoadArticlePhoto");
                        jsonImageObject.addProperty("shareId", shareId);
                        jsonImageObject.addProperty("ImageBase64", ImageBase64);

                        upLoadPhotoTask = new ShareTask(url, jsonImageObject.toString());
                        try {
                            upLoadPhotoTask.execute();
//                            photoCount = Integer.valueOf(data);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }
//                if (etDescribe.getText() != null && shareId !=0 || gvPhotoPicked.getCount() != 0 && photoCount!=0) {
//                    ShareCommon.showToast(this, R.string.msg_InsertFail);
//                } else {
//                    ShareCommon.showToast(this, R.string.msg_InsertSuccess);
//                }

            } else {
                ShareCommon.showToast(this, R.string.msg_NoNetwork);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (addArticleTask != null) {
            addArticleTask.cancel(true);
        }
    }
}

