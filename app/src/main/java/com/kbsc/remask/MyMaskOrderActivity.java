package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MyMaskOrderActivity extends AppCompatActivity  implements NavigationInterface{
    private static final String TAG = "MyMaskOrderActivity";

    BottomNavigationView bottomNavigationView;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymask_order);

        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        toolbar_name.setText("폐마스크 발주");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        menu = bottomNavigationView.getMenu();
        bottomNavigationView.setSelectedItemId(R.id.action_mypage);  //선택된 아이템 지정
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Intent intent = nextIntent(menuItem, menu, getApplicationContext());
            startActivity(intent);
            finish();
            return true;
        });
    }

    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.tvMymaskOrder_form:
                intent = new Intent(MyMaskOrderActivity.this, MyMaskOrderFormActivity.class);
                break;
            case R.id.tvMymaskOrder_list:
                intent = new Intent(MyMaskOrderActivity.this, MyMaskOrderListActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
