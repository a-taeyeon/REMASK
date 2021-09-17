package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MypageActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivMypage_settings:
                intent = new Intent(MypageActivity.this, MySettingsActivity.class);
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
//            case R.id.ivMypage_masks:
//                intent = new Intent(MypageActivity.this, );
//                break;
        }
        startActivity(intent);
        finish();
    }

}