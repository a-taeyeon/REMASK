package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;
import java.util.UUID;

public class MaskOrderActivity extends AppCompatActivity {

    EditText etCnt1;
    EditText etCnt2;
    EditText etCnt3;
    EditText etReason;
    TextView tvTotalPoint;
    TextView tvConfirm;

    RadioGroup radioGroup;

    int cnt1 = 0;
    int cnt2 = 0;
    int cnt3 = 0;

    String method = "";
//    int radioIndex;

    private static final String TAG = "MaskOrderActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    String userEmail = "";
    String dbUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask_order);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        userEmail = firebaseAuth.getCurrentUser().getEmail();
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();

        etCnt1 = findViewById(R.id.etMaskOrder_cnt1);
        etCnt2 = findViewById(R.id.etMaskOrder_cnt2);
        etCnt3 = findViewById(R.id.etMaskOrder_cnt3);
        etReason = findViewById(R.id.etMaskOrder_reason);
        tvTotalPoint = findViewById(R.id.tvMaskOrder_totalPoint);
        tvConfirm = findViewById(R.id.tvMaskOrder_confirm);
        radioGroup = findViewById(R.id.rgMaskOrder_method);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
//            radioIndex = radioGroup.indexOfChild(radioButton);
            method = radioButton.getText().toString();
        });

        tvConfirm.setOnClickListener(v -> {
            int fabricCnt = Integer.parseInt(etCnt1.getText().toString());
            int ringCnt = Integer.parseInt(etCnt2.getText().toString());
            int wireCnt = Integer.parseInt(etCnt3.getText().toString());
            String reason = etReason.getText().toString();
            long date = System.currentTimeMillis();
            String state = "심사중";

            if(!method.equals("") && !reason.equals("")) {
                UUID key = UUID.randomUUID();
                MaskOrder maskOrder = new MaskOrder(dbUserId, fabricCnt, ringCnt, wireCnt,
                        method, reason, date, state);
                dbRef.child("maskorder").child(key.toString()).setValue(maskOrder);

                //(추가) maskComponent 테이블에서 fabricCnt, ringCnt, wireCnt 감소

            }else{
                Toast.makeText(MaskOrderActivity.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(MaskOrderActivity.this, MypageActivity.class);
            startActivity(intent);
            finish();
        });

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivMaskOrder_minus1:
                if(cnt1 > 0)    etCnt1.setText(String.valueOf(--cnt1));
                break;
            case R.id.ivMaskOrder_plus1:
                etCnt1.setText(String.valueOf(++cnt1));
                break;
            case R.id.ivMaskOrder_minus2:
                if(cnt2 > 0)    etCnt2.setText(String.valueOf(--cnt2));
                break;
            case R.id.ivMaskOrder_plus2:
                etCnt2.setText(String.valueOf(++cnt2));
                break;
            case R.id.ivMaskOrder_minus3:
                if(cnt3 > 0)    etCnt3.setText(String.valueOf(--cnt3));
                break;
            case R.id.ivMaskOrder_plus3:
                etCnt3.setText(String.valueOf(++cnt3));
                break;
        }
    }

}