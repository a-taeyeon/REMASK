package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyCartActivity extends AppCompatActivity implements NavigationInterface{
    private static final String TAG = "MyCartActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    String userEmail = "";
    String dbUserId = "";

    BottomNavigationView bottomNavigationView;
    Menu menu;


    private CheckBox cbSelectAll;
    private TextView tvDelete;
    public static boolean isCheckFlag = false;

    ImageView ivPrdtImg;
    TextView tvPrdtName;
    TextView tvDescription;
    TextView tvPrice;
    TextView tvQuantity;

    RecyclerView rvMyCart;
    MyCartAdapter myCartAdapter;
    ArrayList<MyCart> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart_list);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        toolbar_name.setText("장바구니");
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
        Log.d(TAG, userEmail);
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();

        cbSelectAll = findViewById(R.id.cbMyCart_all);
        tvDelete = findViewById(R.id.tvMyCart_delete);

        ivPrdtImg = findViewById(R.id.ivMyCart_prdtImg);
        tvPrdtName = findViewById(R.id.tvMyCart_prdtName);
        tvDescription = findViewById(R.id.tvMyCart_prdtAbbr);
        tvPrice = findViewById(R.id.tvMyCart_price);
        tvQuantity = findViewById(R.id.tvMyCart_quantity);

        rvMyCart = (RecyclerView) findViewById(R.id.rvMyCart);

        /* initiate adapter */
        myCartAdapter = new MyCartAdapter();
        /* initiate recyclerview */
        rvMyCart.setAdapter(myCartAdapter);
        rvMyCart.setLayoutManager(new LinearLayoutManager(this));
        /* adapt data */
        list = new ArrayList<MyCart>();
        getCartList(dbUserId);
        myCartAdapter.setMyCartList(list);

        cbSelectAll.setOnClickListener(v -> {
            if (cbSelectAll.isChecked()) {
                for (MyCart mycart : list) {
                    mycart.setSelected(true);
                }
            } else {
                for (MyCart mycart : list) {
                    mycart.setSelected(false);
                }
            }
            myCartAdapter.notifyDataSetChanged();
        });

        tvDelete.setOnClickListener(v ->{ //Delete작동안됨
            myCartAdapter.setOnItemClickListener((v1, position) -> {
                Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
            });
        });

    }

    public void getCartList(String userId){
        Query query = dbRef.child("cart").orderByChild("user_id").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Cart cart = childSnapshot.getValue(Cart.class);
                    if (cart.getUser_id().equalsIgnoreCase(userId)) {
                        Log.d(TAG, "!!!!!!!!!!!" + cart.toString());
                        //myCart하나씩 뽑아와서 product_id 구하기
                        String prdtId = cart.getProduct_id();

                        //Product 테이블에서 product_id로 상품정보
                        DatabaseReference prdtRef = FirebaseDatabase.getInstance().getReference();
                        Query query2 = prdtRef.child("product").orderByKey().equalTo(prdtId);
                        query2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    Product product = child.getValue(Product.class);
                                    Log.d(TAG, "!!!product: " + product.toString());

                                    //(추가) PrdtImages 테이블에서 product_id로 상품이미지 구하기


                                    list.add(new MyCart(product.getPrdtName(), product.getPrdtAbbr(), product.getPoint()));
                                }

                                myCartAdapter.setMyCartList(list);
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