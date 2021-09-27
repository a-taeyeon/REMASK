package com.kbsc.remask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;

public class MyPointListActivity extends AppCompatActivity {
    private static final String TAG = "MyPointListActivity";
    FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    String userEmail = "";
    String dbUserId = "";

    TextView tvName;
    TextView tvPointSum;
    TextView tvStartDate;
    TextView tvEndDate;

    RadioGroup radio;
    String before = "";

    RecyclerView rvMyPoint;
    MyPointAdapter myPointAdapter;
    ArrayList<MyPoint> list;
    int pointSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypoint_list);

        firebaseAuth = firebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        tvName = findViewById(R.id.tvMypointList_userName);
        tvPointSum = findViewById(R.id.tvMypointList_sum);
        tvStartDate= findViewById(R.id.tvMypointList_start);
        tvEndDate = findViewById(R.id.tvMypointList_end);

        radio = (RadioGroup) findViewById(R.id.rgMypointList);
        radio.setOnCheckedChangeListener((group, checkedId) -> {
            View radioButton = radio.findViewById(checkedId);
            int index = radio.indexOfChild(radioButton);

            // Add logic here
            switch (index) {
                case 0: // 1주일
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    before = "1week";
                    break;
                case 1: // 1개월
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    before = "1month";
                    break;
                case 2: //3개월
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    before = "3month";
                    break;
                case 3: //6개월
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    before = "6month";
                    break;
                case 4: //1년
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    before = "1year";
                    break;
            }
            setDate(before);
        });

        dbUserId = setUserInfo();

        rvMyPoint = (RecyclerView)findViewById(R.id.rvMyPoint);


        /* initiate adapter */
        myPointAdapter = new MyPointAdapter();

        /* initiate recyclerview */
        rvMyPoint.setAdapter(myPointAdapter);
        rvMyPoint.setLayoutManager(new LinearLayoutManager(this));

        /* adapt data */
        list = new ArrayList<MyPoint>();
        getPointList(dbUserId, before);
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + list.toString()); //[]
        Log.d(TAG, "pointSum in onCreate(): " + pointSum); //0
        myPointAdapter.setPointList(list);
    }

    private String setUserInfo(){
        userEmail = firebaseAuth.getCurrentUser().getEmail();
        Log.d(TAG, userEmail);
        StringTokenizer stk = new StringTokenizer(userEmail, "@");
        dbUserId = stk.nextToken();
        Log.d(TAG, "dbKey: " + dbUserId);

        dbRef.child("users").child(dbUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "user: " + user.toString());
                String name = user.getUserName();

                //텍스트뷰에 받아온 문자열 대입하기
                tvName.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        return dbUserId;
    }

    private void setDate(String before){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd");
        Date today = new Date();
        String todayStr = dateFormatter.format(today);
        tvEndDate.setText(todayStr);

        Calendar cal = Calendar.getInstance();
        switch (before){
            case "1week":
                cal.add(Calendar.DATE, -7);
                break;
            case "1month":
                cal.add(Calendar.MONTH, -1);
                break;
            case "3month":
                cal.add(Calendar.MONTH, -3);
                break;
            case "6month":
                cal.add(Calendar.MONTH, -6);
                break;
            case "1year":
                cal.add(Calendar.YEAR, -1);
                break;
        }

        String start = dateFormatter.format(cal.getTime());
        tvStartDate.setText(start);
    }

    public void getPointList(String userId, String before){
        Query query = dbRef.child("points").orderByChild("user_id").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    MyPoint myPoint = childSnapshot.getValue(MyPoint.class);
                    if(myPoint.getUser_id().equalsIgnoreCase(userId)){
                        Log.d(TAG, "myPoint: " + myPoint);
                        pointSum += myPoint.getValue();

                        Calendar cal = Calendar.getInstance();
                        switch (before){
                            case "1week":
                                cal.add(Calendar.DATE, -7);
                                break;
                            case "1month":
                                cal.add(Calendar.MONTH, -1);
                                break;
                            case "3month":
                                cal.add(Calendar.MONTH, -3);
                                break;
                            case "6month":
                                cal.add(Calendar.MONTH, -6);
                                break;
                            case "1year":
                                cal.add(Calendar.YEAR, -1);
                                break;
                        }

                        list.add(new MyPoint(i++, myPoint.getUser_id(), myPoint.getContent(), myPoint.getDate(), myPoint.getValue()));
                    }
                }
                Log.d(TAG, "pointSum in onDataChange: " + pointSum);
                tvPointSum.setText(String.valueOf(pointSum));
                Collections.sort(list, new MyPointComparator());
                myPointAdapter.setPointList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}