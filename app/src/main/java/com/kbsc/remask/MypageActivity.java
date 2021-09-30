package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;
import java.util.UUID;

public class MypageActivity extends AppCompatActivity implements NavigationInterface{

    private static final String TAG = "MypageActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    String userEmail = "";
    String dbUserId = "";

    BottomNavigationView bottomNavigationView;
    Menu menu;

    Intent intent;
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        menu = bottomNavigationView.getMenu();
        bottomNavigationView.setSelectedItemId(R.id.action_mypage);  //선택된 아이템 지정
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Intent intent = nextIntent(menuItem, menu, getApplicationContext());
            startActivity(intent);
            finish();
            return true;
        });

        tvName = (TextView) findViewById(R.id.tvMypage_userName);
        setUserName();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivMypage_settings:
                intent = new Intent(MypageActivity.this, MyInfoUpdateActivity.class);
                break;
            case R.id.ivMypage_mycart:
                intent = new Intent(MypageActivity.this, MyCartActivity.class);
                break;
            case R.id.ivMypage_point:
                intent = new Intent(MypageActivity.this, MyPointListActivity.class);
                break;
            case R.id.ivMypage_orders:
                intent = new Intent(MypageActivity.this, MyOrderListActivity.class);
                break;
            case R.id.ivMypage_masks:
                intent = new Intent(MypageActivity.this, MyMaskOrderActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void setUserName(){
        userEmail = firebaseAuth.getCurrentUser().getEmail();
        Log.d(TAG, "dbKey: " + dbUserId);
        Log.d(TAG, userEmail);
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();

        dbRef.child("users").child(dbUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "user: " + user.toString());
                String name = user.getUserName();

                tvName.setText(name);

                if(user.getSellerNum() != null){
                    ImageView ivSeller = findViewById(R.id.ivMypage_seller);
                    ivSeller.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
    }
}