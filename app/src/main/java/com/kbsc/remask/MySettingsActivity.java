package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MySettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysettings);
    }

    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.tvSettings_update:
                intent = new Intent(MySettingsActivity.this, MyInfoUpdateActivity.class);
                break;
            case R.id.tvSettings_license:
                intent = new Intent(MySettingsActivity.this, LicenseActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }

}