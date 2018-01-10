package com.project.pp.parentparadise.amber;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.pp.parentparadise.R;
import com.project.pp.parentparadise.ppmain.Member;
import com.project.pp.parentparadise.utl.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by amberyang on 2017/12/7.
 */

public class ShareAddArticle extends AppCompatActivity {
    private static final String TAG = "ShareAddArticle";
    private Spinner spClassify1;
    private Button btScan, btTakePhoto, btPickPhoto;
    private EditText etAddIsbn, etDescribe;
    private String shareType;
    private LinearLayout llActMain, llBookData, llAddArticle;
    private RecyclerView rvPhotoPicked;
    private ArrayList<String> pathList = new ArrayList<>();
    private static final int REQUEST_TAKE_PICTURE = 0;
    private static final int REQUEST_PICK_PICTURE = 1;
    private ShareTask addArticleTask, upLoadPhotoTask;
    private File file;
    private int newSize = 512;

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
        btTakePhoto = findViewById(R.id.btTakePhoto);
        btPickPhoto = findViewById(R.id.btPickPhoto);
        etAddIsbn = findViewById(R.id.etAddIsbn);
        etDescribe = findViewById(R.id.etDescribe);
        llActMain = findViewById(R.id.llActMain);
        llBookData = findViewById(R.id.llBookData);
        llAddArticle = findViewById(R.id.llAddArticle);
        rvPhotoPicked = findViewById(R.id.rvPhotoPicked);
        spClassify1.setSelection(0, true);
        spClassify1.setOnItemSelectedListener(spListener1);
    }

    private void initContent() {
        llBookData.setVisibility(View.GONE);
        llAddArticle.setVisibility(View.GONE);
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
                    llAddArticle.setVisibility(View.VISIBLE);
                    shareType = "S";
                    break;
                case 2:
                    initContent();
                    llAddArticle.setVisibility(View.VISIBLE);
                    shareType = "A";
                    break;
                case 3:
                    initContent();
                    llBookData.setVisibility(View.VISIBLE);
                    btScan.setVisibility(View.VISIBLE);
                    llAddArticle.setVisibility(View.VISIBLE);
                    shareType = "B";
                    break;
                case 4:
                    initContent();
                    llAddArticle.setVisibility(View.VISIBLE);
                    shareType = "M";
                    break;
                case 5:
                    initContent();
                    llAddArticle.setVisibility(View.VISIBLE);
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

    public void OnScanClick(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setBarcodeImageEnabled(true);
        integrator.setBeepEnabled(false);
        integrator.setCameraId(0);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("請掃描條碼或QRcode");
        integrator.initiateScan();
    }

//    public void OnTackPhotoClick(View view) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        file = new File(file, "picture.jpg");
//
//        Uri contentUri = FileProvider.getUriForFile(
//                this, getPackageName() + ".provider", file);
//        String path = contentUri.toString();
//        pathList.add(path);
//
//        intent.putStringArrayListExtra(MediaStore.EXTRA_OUTPUT , pathList);
//        Log.d(TAG, intent.toString());
//        if (isIntentAvailable(this, intent)) {
//            startActivityForResult(intent, REQUEST_TAKE_PICTURE);
//        } else {
//            Toast.makeText(this, "No Camera Apps Found",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }

//    public void OnPickPhotoClick(View view) {
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        Uri contentUri = FileProvider.getUriForFile(
//                this, getPackageName() + ".provider", file);
//        String path = contentUri.toString();
//        pathList.add(path);
//        intent.putStringArrayListExtra(MediaStore.EXTRA_OUTPUT, pathList);
//
//        startActivityForResult(intent, REQUEST_PICK_PICTURE);
//    }

    //    檢查可拍照的軟體，可能有多個(回傳List)
    public boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    //    檢查如果user不允許使用存取外部儲存體，讓按鈕失效
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case ShareCommon.PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    btTakePhoto.setEnabled(true);
                    btPickPhoto.setEnabled(true);
                } else {
                    btTakePhoto.setEnabled(false);
                    btPickPhoto.setEnabled(false);
                }
                break;
        }
    }


    // 傳資料到畫面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("Received", "onActivityResult");
        rvPhotoPicked.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PICTURE:
                    pathList = intent.getStringArrayListExtra(MediaStore.ACTION_IMAGE_CAPTURE);
                    rvPhotoPicked.setAdapter(new PhotoPathAdapter(this, pathList));
                    rvPhotoPicked.setOnFlingListener(null);
                    snapHelper.attachToRecyclerView(rvPhotoPicked);
                    break;

                case REQUEST_PICK_PICTURE:
                    pathList = intent.getStringArrayListExtra(MediaStore.ACTION_IMAGE_CAPTURE);
                    rvPhotoPicked.setAdapter(new PhotoPathAdapter(this, pathList));
                    rvPhotoPicked.setOnFlingListener(null);
                    snapHelper.attachToRecyclerView(rvPhotoPicked);
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


    class PhotoPathAdapter extends RecyclerView.Adapter<PhotoPathAdapter.MyViewHolder> {
        private Context context;
        private ArrayList<String> pathList;

        PhotoPathAdapter(Context context, ArrayList<String> pathList) {
            this.context = context;
            this.pathList = pathList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivPhoto;
            LinearLayout llViewGroup;

            MyViewHolder(View itemView) {
                super(itemView);
                ivPhoto = itemView.findViewById(R.id.ivPhoto);
                llViewGroup = itemView.findViewById(R.id.llViewGroup);
            }
        }

        @Override
        public int getItemCount() {
            return pathList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.share_add_article_itemview, viewGroup, false);
            return new PhotoPathAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
//            holder.ivPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Bitmap srcImage = BitmapFactory.decodeFile(pathList.get(position));
            Bitmap downsizedImage = ShareCommon.downSize(srcImage, newSize);
            holder.ivPhoto.setImageBitmap(downsizedImage);

            ImageView[] dots = new ImageView[pathList.size()];
            for (int i = 0; i < pathList.size(); i++) {
                ImageView mImageView = new ImageView(ShareAddArticle.this);
                dots[i] = mImageView;
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams
                        (new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                ll.rightMargin = 6;
                ll.leftMargin = 6;
                ll.bottomMargin = 8;

                if (i == position) {
                    mImageView.setBackgroundResource(R.drawable.amber_icon_dot_yellow);
                } else {
                    mImageView.setBackgroundResource(R.drawable.amber_icon_dot_white);
                }
                holder.llViewGroup.addView(mImageView, ll);
            }
        }
    }
    //UpLoadData to SQL
    public void onSubmitClick(View view) {

        if (etDescribe.getText() == null && pathList.size() == 0) {
            ShareCommon.showToast(this, R.string.msg_NoUploadMessage);
            return;
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
                ShareData shareData = new ShareData(member_no, shareType, shareContent, isbn);
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
                            String result = upLoadPhotoTask.execute().get();
                            photoCount = Integer.valueOf(result);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }

                if (etDescribe.getText() == null && pathList.size() == 0) {
                    ShareCommon.showToast(this, R.string.msg_InsertFail);
//                    finish();
                } else {
                    ShareCommon.showToast(this, R.string.msg_InsertSuccess);
                    finish();
                    Intent intent = new Intent(this, ShareFragmentSweet.class);
                    intent.putExtra("id",1);
                    startActivity(intent);
                }

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

