package com.kbsc.remask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyMaskOrderListActivity extends AppCompatActivity implements NavigationInterface{
    private static final String TAG = "MaskOrderListActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;

    String userEmail = "";
    String dbUserId = "";

    BottomNavigationView bottomNavigationView;
    Menu menu;

    RecyclerView rvMyMask;
    MyMaskOrderListAdapter myMaskOrderListAdapter;
    ArrayList<MyMask> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymask_order_list);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        toolbar_name.setText("발주 목록");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        menu = bottomNavigationView.getMenu();
        bottomNavigationView.setSelectedItemId(R.id.action_mypage);  //선택된 아이템 지정
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Intent intent = nextIntent(menuItem, menu, getApplicationContext());
            startActivity(intent);
            finish();
            return true;
        });

        userEmail = firebaseAuth.getCurrentUser().getEmail();
        Log.d(TAG, userEmail);
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();

        rvMyMask = (RecyclerView) findViewById(R.id.rvMymaskOrder);

        /* initiate adapter */
        myMaskOrderListAdapter = new MyMaskOrderListAdapter();
        /* initiate recyclerview */
        rvMyMask.setAdapter(myMaskOrderListAdapter);
        rvMyMask.setLayoutManager(new LinearLayoutManager(this));
        /* adapt data */
        list = new ArrayList<MyMask>();
        getMymaskList(dbUserId);
        myMaskOrderListAdapter.setMyMaskOrderList(list);
    }

    public void getMymaskList(String userId){
        Query query = dbRef.child("maskorder").orderByChild("user_id").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    MaskOrder maskOrder = childSnapshot.getValue(MaskOrder.class);
                    if (maskOrder.getUser_id().equalsIgnoreCase(userId)) {
                        list.add(new MyMask(maskOrder.getDate(), maskOrder.getFabricCnt(), maskOrder.getRingCnt(), maskOrder.getWireCnt(), maskOrder.getState()));
                    }
                }
                myMaskOrderListAdapter.setMyMaskOrderList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}