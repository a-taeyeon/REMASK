package com.kbsc.remask;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.StringTokenizer;

public class MyInfoUpdateActivity extends AppCompatActivity {
    private static final String TAG = "MyInfoUpdateActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;

    String userEmail = "";
    String dbKey = "";

    TextView tvEmail;
    TextView tvPhone;
    EditText etPw;
    TextView tvBtnChangePw;
    TextView tvRegisterSeller;
    TextView tvSellerNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_update);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        tvEmail = (TextView) findViewById(R.id.tvUpdateInfo_email);
        tvPhone = (TextView) findViewById(R.id.tvUpdateInfo_phone);
        etPw = (EditText) findViewById(R.id.etUpdateInfo_newPw);
        tvBtnChangePw = (TextView) findViewById(R.id.tvUpdateInfo_chpw);
        tvRegisterSeller = (TextView) findViewById(R.id.tvUpdateInfo_registerSeller);
        tvSellerNum = (TextView) findViewById(R.id.tvUpdateInfo_sellerNum);

        userEmail = firebaseAuth.getCurrentUser().getEmail();
        Log.d(TAG, userEmail);
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbKey = stk.nextToken();
        Log.d(TAG, "dbKey: " + dbKey);


        dbRef.child("users").child(dbKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "user: " + user.toString());
                String phone = user.getPhone();

                //텍스트뷰에 받아온 문자열 대입하기
                tvEmail.setText(userEmail);
                tvPhone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
    }


}