package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;

public class MyInfoUpdateActivity extends AppCompatActivity implements NavigationInterface{
    private static final String TAG = "MyInfoUpdateActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;

    String userEmail = "";
    String dbUserId = "";
    BottomNavigationView bottomNavigationView;
    Menu menu;

    TextView tvName;
    TextView tvEmail;
    TextView tvPhone;
    EditText etPw;
    TextView tvBtnChangePw;
    TextView tvRegisterSeller;
    TextView tvSeller;
    TextView tvSellerNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_update);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        toolbar_name.setText("내정보수정");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        menu = bottomNavigationView.getMenu();
        bottomNavigationView.setSelectedItemId(R.id.action_mypage);  //선택된 아이템 지정
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Intent intent = nextIntent(menuItem, menu, getApplicationContext());
            startActivity(intent);
            finish();
            return true;
        });

        tvName = (TextView) findViewById(R.id.tvUpdateInfo_userName);
        tvEmail = (TextView) findViewById(R.id.tvUpdateInfo_email);
        tvPhone = (TextView) findViewById(R.id.tvUpdateInfo_phone);
        etPw = (EditText) findViewById(R.id.etUpdateInfo_newPw);
        tvBtnChangePw = (TextView) findViewById(R.id.tvUpdateInfo_chpw);
        tvRegisterSeller = (TextView) findViewById(R.id.tvUpdateInfo_registerSeller);
        tvSeller = (TextView) findViewById(R.id.tvUpdateInfo_isSeller);
        tvSellerNum = (TextView) findViewById(R.id.tvUpdateInfo_sellerNum);

        setUserInfo();
    }

    private void setUserInfo(){
        userEmail = firebaseAuth.getCurrentUser().getEmail();
        Log.d(TAG, userEmail);
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();
        Log.d(TAG, "dbKey: " + dbUserId);

        dbRef.child("users").child(dbUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "user: " + user.toString());
                String name = user.getUserName();
                String phone = user.getPhone();
                boolean seller = user.isSeller();

                //텍스트뷰에 받아온 문자열 대입하기
                tvName.setText(name);
                tvEmail.setText(userEmail);
                tvPhone.setText(phone);

                if(seller){
                    tvSeller.setVisibility(View.VISIBLE);
                    tvSellerNum.setVisibility(View.VISIBLE);
                    tvSellerNum.setText(user.getSellerNum());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
    }


}