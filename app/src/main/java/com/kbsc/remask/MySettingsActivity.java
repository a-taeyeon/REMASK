package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MySettingsActivity extends AppCompatActivity implements NavigationInterface{

    BottomNavigationView bottomNavigationView;
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysettings);

        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        toolbar_name.setText("환경설정");
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

    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.tvMymaskOrder_form:
                intent = new Intent(MySettingsActivity.this, MyInfoUpdateActivity.class);
                break;
            case R.id.tvMymaskOrder_list:
                intent = new Intent(MySettingsActivity.this, LicenseActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }

}