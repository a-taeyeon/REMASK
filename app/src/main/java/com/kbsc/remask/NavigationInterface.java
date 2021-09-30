package com.kbsc.remask;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.firebase.database.annotations.NotNull;

public interface NavigationInterface {

    public default Intent nextIntent(@NonNull @NotNull MenuItem menuItem, Menu menu, Context context){
        Intent intent = null;
        switch(menuItem.getItemId())
        {
            case R.id.action_home:
                menuItem.setIcon(R.drawable.navi_home_checked);    // 선택한 이미지 변경
                menu.findItem(R.id.action_mask).setIcon(R.drawable.navi_mask);
                menu.findItem(R.id.action_cart).setIcon(R.drawable.navi_cart);
                menu.findItem(R.id.action_mypage).setIcon(R.drawable.navi_mypage);
                intent = new Intent(context, MainActivity.class);
                break;

            case R.id.action_mask:
                menuItem.setIcon(R.drawable.navi_mask_checked);    // 선택한 이미지 변경
                menu.findItem(R.id.action_home).setIcon(R.drawable.navi_home);
                menu.findItem(R.id.action_cart).setIcon(R.drawable.navi_cart);
                menu.findItem(R.id.action_mypage).setIcon(R.drawable.navi_mypage);
                intent = new Intent(context, MaskRegisterActivity.class);
                break;

            case R.id.action_cart:
                menuItem.setIcon(R.drawable.navi_cart_checked);    // 선택한 이미지 변경
                menu.findItem(R.id.action_home).setIcon(R.drawable.navi_home);
                menu.findItem(R.id.action_mask).setIcon(R.drawable.navi_mask);
                menu.findItem(R.id.action_mypage).setIcon(R.drawable.navi_mypage);
//                        intent = new Intent(context, .class);
                break;

            case R.id.action_mypage:
                menuItem.setIcon(R.drawable.navi_mypage_checked);    // 선택한 이미지 변경
                menu.findItem(R.id.action_home).setIcon(R.drawable.navi_home);
                menu.findItem(R.id.action_mask).setIcon(R.drawable.navi_mask);
                menu.findItem(R.id.action_cart).setIcon(R.drawable.navi_cart);
                intent = new Intent(context, MypageActivity.class);
                break;
        }
        return intent;
    }
}
