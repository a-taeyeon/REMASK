package com.kbsc.remask;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;
import java.util.UUID;

public class MaskRegisterActivity extends AppCompatActivity implements NavigationInterface{
    private static final String TAG = "MaskRegisterActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    String userEmail = "";
    String dbUserId = "";

    BottomNavigationView bottomNavigationView;
    Menu menu;

    ImageView ivCamera;
    final static int CAPTURE_IMAGE = 1;

    EditText etCnt1;
    EditText etCnt2;
    EditText etCnt3;
    TextView tvConfirm;

    int cnt1 = 0;
    int cnt2 = 0;
    int cnt3 = 0;

    int state = -1; //마스크 ML 확인 결과

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask_register);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        userEmail = firebaseAuth.getCurrentUser().getEmail();
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        menu = bottomNavigationView.getMenu();
        bottomNavigationView.setSelectedItemId(R.id.action_mask);  //선택된 아이템 지정
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Intent intent = nextIntent(menuItem, menu, getApplicationContext());
            startActivity(intent);
            finish();
            return true;
        });

        etCnt1 = findViewById(R.id.etMaskRegister_cnt1);
        etCnt2 = findViewById(R.id.etMaskRegister_cnt2);
        etCnt3 = findViewById(R.id.etMaskRegister_cnt3);
        tvConfirm = findViewById(R.id.tvMaskRegister_confirm);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(MaskRegisterActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        ivCamera = findViewById(R.id.ivMaskRegister_camera);
        ivCamera.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAPTURE_IMAGE);
        });

        tvConfirm.setOnClickListener(v -> {
            String uuid = UUID.randomUUID().toString();
            dbRef.child("maskregister").child(uuid).setValue(new MaskRegister(dbUserId, System.currentTimeMillis(), state));
        });
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK && data.hasExtra("data")) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (bitmap != null) {
                ivCamera.setImageBitmap(bitmap);
            }
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivMaskRegister_minus1:
                if(cnt1 > 0) {
                    etCnt1.setText(String.valueOf(--cnt1));
                }
                break;
            case R.id.ivMaskRegister_plus1:
                etCnt1.setText(String.valueOf(++cnt1));
                break;
            case R.id.ivMaskRegister_minus2:
                if(cnt2 > 0) {
                    etCnt2.setText(String.valueOf(--cnt2));
                }
                break;
            case R.id.ivMaskRegister_plus2:
                etCnt2.setText(String.valueOf(++cnt2));
                break;
            case R.id.ivMaskRegister_minus3:
                if(cnt3 > 0){
                    etCnt3.setText(String.valueOf(--cnt3));
                }
                break;
            case R.id.ivMaskRegister_plus3:
                etCnt3.setText(String.valueOf(++cnt3));
                break;
        }
    }
}
