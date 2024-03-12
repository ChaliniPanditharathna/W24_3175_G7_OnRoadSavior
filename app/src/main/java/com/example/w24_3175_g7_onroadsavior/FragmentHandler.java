package com.example.w24_3175_g7_onroadsavior;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentHandler extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    HistroyFragment histroyFragment = new HistroyFragment();

    NotificationFragment notificationFragment = new NotificationFragment();

    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_bar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId() == R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return  true;
                }
                if(item.getItemId() == R.id.history){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, histroyFragment).commit();
                    return  true;
                }
                if(item.getItemId() == R.id.notification){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).commit();
                    return  true;
                }
                if(item.getItemId() == R.id.profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                    return  true;
                }
                return false;
            }
        });
    }
}