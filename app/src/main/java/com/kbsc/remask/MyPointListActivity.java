package com.kbsc.remask;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyPointListActivity extends AppCompatActivity {

    RadioGroup radio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypoint_list);
        radio = (RadioGroup) findViewById(R.id.rgMypointList);

        radio.setOnCheckedChangeListener((group, checkedId) -> {
            View radioButton = radio.findViewById(checkedId);
            int index = radio.indexOfChild(radioButton);

            // Add logic here
            switch (index) {
                case 0: // 1주일
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    break;
                case 1: // 1개월
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    break;
                case 2: //3개월
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    break;
                case 3: //6개월
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    break;
                case 4: //1년
                    Toast.makeText(MyPointListActivity.this, "Selected button number " + index,  Toast.LENGTH_SHORT).show();
                    break;
            }
        });

    }
}