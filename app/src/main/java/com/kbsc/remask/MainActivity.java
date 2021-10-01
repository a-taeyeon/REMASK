package com.kbsc.remask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Collections;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements NavigationInterface{
    private static final String TAG = "MainActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;

    String userEmail = "";
    String dbUserId = "";
    BottomNavigationView bottomNavigationView;
    Menu menu;

    TextView tvName;
    TextView tvTotalPoint;
    int pointSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else{
            dbUserId = getUserName();
            getTotalPoint(dbUserId);
        }

        bottomNavigationView = findViewById(R.id.bottom_nav);
        menu = bottomNavigationView.getMenu();
        bottomNavigationView.setSelectedItemId(R.id.action_home);  //선택된 아이템 지정
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Intent intent = nextIntent(menuItem, menu, getApplicationContext());
            startActivity(intent);
            finish();
            return true;
        });

        tvName = findViewById(R.id.tvMain_name);
        tvTotalPoint = findViewById(R.id.tvMain_totalPoint);


    }

    private String getUserName(){
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
                tvName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        return dbUserId;
    }

    private void getTotalPoint(String userId){
        Query query = dbRef.child("points").orderByChild("user_id").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    MyPoint myPoint = childSnapshot.getValue(MyPoint.class);
                    if(myPoint.getUser_id().equalsIgnoreCase(userId)){
                        Log.d(TAG, "myPoint: " + myPoint);
                        pointSum += myPoint.getValue();
                    }
                }
                tvTotalPoint.setText(String.valueOf(pointSum) + " point");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}