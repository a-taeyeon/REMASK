package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;
import java.util.UUID;

public class MyMaskOrderFormActivity extends AppCompatActivity implements NavigationInterface{

    private static final String TAG = "MyMaskOrderFormActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    String userEmail = "";
    String dbUserId = "";

    BottomNavigationView bottomNavigationView;
    Menu menu;

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
    int totalPoint = 0;

    String method = "";
//    int radioIndex;

    int dbCnt = 0;
    int updateCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymask_orderform);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        toolbar_name.setText("발주 넣기");
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
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();

        etCnt1 = findViewById(R.id.etMaskOrderForm_cnt1);
        etCnt2 = findViewById(R.id.etMaskOrderForm_cnt2);
        etCnt3 = findViewById(R.id.etMaskOrderForm_cnt3);
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
            int orderFabricCnt = Integer.parseInt(etCnt1.getText().toString());
            int orderRingCnt = Integer.parseInt(etCnt2.getText().toString());
            int orderWireCnt = Integer.parseInt(etCnt3.getText().toString());
            String reason = etReason.getText().toString();
            long date = System.currentTimeMillis();
            String state = "심사중";

            if(!method.equals("") && !reason.equals("") && (orderFabricCnt != 0 || orderRingCnt != 0 || orderWireCnt != 0)) {
                UUID key = UUID.randomUUID();
                MaskOrder maskOrder = new MaskOrder(dbUserId, orderFabricCnt, orderRingCnt, orderWireCnt,
                        method, reason, date, state);
                dbRef.child("maskorder").child(key.toString()).setValue(maskOrder);

                //내 point도 totalPoint만큼 감소 -> 작동안함?
                updateMyPoint(dbUserId);

                //maskComponent 테이블에서 fabricCnt, ringCnt, wireCnt 감소
                updateMaskComponent("fabric", orderFabricCnt);
                updateMaskComponent("ring", orderRingCnt);
                updateMaskComponent("wire", orderWireCnt);

                Intent intent = new Intent(MyMaskOrderFormActivity.this, MypageActivity.class);
                startActivity(intent);
                finish();
            } else{
                Toast.makeText(MyMaskOrderFormActivity.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateMaskComponent(String component, int orderCnt) {
        dbRef.child("maskcomponent").child(component).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MaskComponent maskComponent = dataSnapshot.getValue(MaskComponent.class);
                if(maskComponent != null) {
                    dbCnt = maskComponent.getCnt();

                    DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference();
                    updateCnt = dbCnt - orderCnt;
                    Log.d(TAG, "updateMaskComponent() - updateCnt: " + updateCnt);
                    dbRef2.child("maskcomponent").child(component).setValue(new MaskComponent(updateCnt));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
    }

    public void updateMyPoint(String userId){
        String uuid = String.valueOf(UUID.randomUUID());
        StringTokenizer usedPoint = new StringTokenizer(tvTotalPoint.getText().toString(), " ");
        dbRef.child("points").child(uuid).setValue(new MyPoint(dbUserId, "폐마스크 발주", System.currentTimeMillis(), -Integer.parseInt(usedPoint.nextToken())));
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivMaskOrderForm_minus1:
                if(cnt1 > 0) {
                    etCnt1.setText(String.valueOf(--cnt1));
                    --totalPoint;
                }
                break;
            case R.id.ivMaskOrderForm_plus1:
                etCnt1.setText(String.valueOf(++cnt1));
                ++totalPoint;
                break;
            case R.id.ivMaskOrderForm_minus2:
                if(cnt2 > 0) {
                    etCnt2.setText(String.valueOf(--cnt2));
                    --totalPoint;
                }
                break;
            case R.id.ivMaskOrderForm_plus2:
                etCnt2.setText(String.valueOf(++cnt2));
                ++totalPoint;
                break;
            case R.id.ivMaskOrderForm_minus3:
                if(cnt3 > 0){
                    etCnt3.setText(String.valueOf(--cnt3));
                    --totalPoint;
                }
                break;
            case R.id.ivMaskOrderForm_plus3:
                etCnt3.setText(String.valueOf(++cnt3));
                ++totalPoint;
                break;
        }
        String totalPointStr = String.valueOf(totalPoint) + " P";
        tvTotalPoint.setText(totalPointStr);
    }

}