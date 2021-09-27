package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MyMaskOrderActivity extends AppCompatActivity {
    private static final String TAG = "MyMaskOrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymask_order);
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
