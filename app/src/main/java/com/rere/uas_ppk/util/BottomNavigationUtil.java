package com.rere.uas_ppk.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.rere.uas_ppk.HomeActivity;
import com.rere.uas_ppk.MainActivity;
import com.rere.uas_ppk.ProfileActivity;

public class BottomNavigationUtil {
    private BottomNavigationView navbar;

    private Context context;

    public BottomNavigationUtil(Context context, BottomNavigationView navbar) {
        this.navbar = navbar;
        this.context = context;
    }

    protected void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs", context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("token");

        editor.apply();

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void setup() {
        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("App", item.getTitle().toString());
                if (item.getTitle().toString().equals("Home")) {
                    context.startActivity(new Intent(context, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    ((Activity) context).overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getTitle().toString().equals("Profile")) {
                    context.startActivity(new Intent(context, ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    ((Activity) context).overridePendingTransition(0, 0);
                    return true;
                }

                if (item.getTitle().toString().equals("Logout")) {
                    logout();
                    return true;
                }
                return false;
            }
        });
    }
}
