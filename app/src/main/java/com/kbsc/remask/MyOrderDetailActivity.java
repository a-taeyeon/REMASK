package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;

public class MyOrderDetailActivity extends AppCompatActivity {
    private static final String TAG = "MyOrderDetailActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    String userEmail = "";
    String dbUserId = "";

    ImageView ivPrdtImg;
    TextView tvOrderId;
    TextView tvPrdtName;
    TextView tvState;
    TextView tvName;
    TextView tvPhone;
    TextView tvAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_detail);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        userEmail = firebaseAuth.getCurrentUser().getEmail();
        Log.d(TAG, userEmail);
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();

        Intent intent = getIntent();
        String orderId = intent.getExtras().getString("orderId");
        Log.d(TAG, "orderId: " + orderId);

        tvOrderId = findViewById(R.id.tvMyOrderDetail_orderId);
        tvState = findViewById(R.id.tvMyOrderDetail_state);
        tvName = findViewById(R.id.tvMyOrderDetail_name);
        tvPhone = findViewById(R.id.tvMyOrderDetail_phone);
        tvAddr = findViewById(R.id.tvMyOrderDetail_addr);

        ivPrdtImg = findViewById(R.id.ivMyOrderDetail_prdtImg);
        tvPrdtName = findViewById(R.id.tvMyOrderDetail_prdtName);

        getInfo(orderId);
    }

    public void getInfo(String orderId){
        Query query = dbRef.child("order").orderByKey().equalTo(orderId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Order order = childSnapshot.getValue(Order.class);
                    Log.d(TAG, "order: " + order.toString());
                    String prdtId = order.getPrdt_id();
                    tvOrderId.setText(orderId);
                    tvState.setText(order.getState());
                    tvName.setText(order.getName());
                    tvPhone.setText(order.getPhone());
                    tvAddr.setText("(" + order.getZipCode() + ")" + "\n" + order.getShipAddr1() + order.getShipAddr2());

                    DatabaseReference prdtRef = FirebaseDatabase.getInstance().getReference();
                    Query query2 = prdtRef.child("product").orderByKey().equalTo(prdtId);
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                Product product = child.getValue(Product.class);
                                Log.d(TAG, "product: " + product.toString());

                                tvPrdtName.setText(product.getPrdtName());
                                //(추가) PrdtImages 테이블에서 product_id로 상품이미지 구하기


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
