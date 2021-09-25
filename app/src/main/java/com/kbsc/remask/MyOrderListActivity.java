package com.kbsc.remask;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyOrderListActivity extends AppCompatActivity {
    private static final String TAG = "MyOrderListActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    String userEmail = "";
    String dbUserId = "";

    ImageView ivPrdtImg;
    TextView tvOrderId;
    TextView tvPrdtName;
    TextView tvState;

    RecyclerView rvMyOrder;
    MyOrderAdapter myOrderAdapter;
    ArrayList<MyOrder> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_list);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        userEmail = firebaseAuth.getCurrentUser().getEmail();
        Log.d(TAG, userEmail);
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();

        ivPrdtImg = findViewById(R.id.ivMyOrder_prdtImg);
        tvOrderId = findViewById(R.id.tvMyOrderDetail_orderId);
        tvPrdtName = findViewById(R.id.tvMyOrderDetail_prdtName);
        tvState = findViewById(R.id.tvMyOrderDetail_state);

        rvMyOrder = (RecyclerView) findViewById(R.id.rvMyOrder);

        /* initiate adapter */
        myOrderAdapter = new MyOrderAdapter();
        /* initiate recyclerview */
        rvMyOrder.setAdapter(myOrderAdapter);
        rvMyOrder.setLayoutManager(new LinearLayoutManager(this));
        /* adapt data */
        list = new ArrayList<MyOrder>();
        getOrderList(dbUserId);
        myOrderAdapter.setMyOrderList(list);
    }

    public void getOrderList(String userId){
        Query query = dbRef.child("order").orderByChild("user_id").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Order order = childSnapshot.getValue(Order.class);
                    if (order.getUser_id().equalsIgnoreCase(userId)) {
                        Log.d(TAG, "!!!!!!!!!!!" + order.toString());
                        String prdtId = order.getPrdt_id();

                        DatabaseReference prdtRef = FirebaseDatabase.getInstance().getReference();
                        Query query2 = prdtRef.child("product").orderByKey().equalTo(prdtId);
                        query2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    Product product = child.getValue(Product.class);
                                    Log.d(TAG, "!!!product: " + product.toString());

                                    //(추가) PrdtImages 테이블에서 product_id로 상품이미지 구하기


                                    list.add(new MyOrder(String.valueOf(order.getOrder_id()), product.getPrdtName(), order.getState()));
                                }

                                myOrderAdapter.setMyOrderList(list);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
