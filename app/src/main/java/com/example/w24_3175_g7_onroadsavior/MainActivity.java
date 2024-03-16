package com.example.w24_3175_g7_onroadsavior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        replaceFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home){
                replaceFragment(new HomeFragment());
                return  true;
            }
            if(item.getItemId() == R.id.history){
                replaceFragment(new HistroyFragment());
                return  true;
            }
            if(item.getItemId() == R.id.notification){
                replaceFragment(new NotificationFragment());
                return  true;
            }
            if(item.getItemId() == R.id.profile){
                replaceFragment(new ProfileFragment());
                return  true;
            }
            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}