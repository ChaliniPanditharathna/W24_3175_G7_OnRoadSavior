package com.example.w24_3175_g7_onroadsavior;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.w24_3175_g7_onroadsavior.Database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentHandler extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FloatingActionButton fab;

    DrawerLayout drawerLayout;

    private FirebaseAuth mAuth;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_bar);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            redirectToLogin();
            return;
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        DB = new DBHelper(this);
        UserHelperClass user = DB.getUserData(currentUser.getUid());
        if (savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ServiceProviderRequestFragment()).commit();
           navigationView.setCheckedItem(R.id.nav_home);

        }

        replaceFragment(new ServiceProviderRequestFragment(), currentUser);

        FloatingActionButton fab = findViewById(R.id.fab_emergency);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FragmentHandler.this, EmergencyActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home){
                if(user.getUserType().equalsIgnoreCase("Service Provider")) {
                    replaceFragment(new ServiceProviderRequestFragment(), currentUser);
                }
                if(user.getUserType().equalsIgnoreCase("Service Requester")){
                    replaceFragment(new UserHomeFragment(), currentUser);
                }
                fab.setVisibility(View.VISIBLE);
                return  true;
            }
            if(item.getItemId() == R.id.history){
                if(user.getUserType().equalsIgnoreCase("Service Provider")){
                    replaceFragment(new ProviderRequestsHistoryFragment(), currentUser);
                }
                if(user.getUserType().equalsIgnoreCase("Service Requester")){
                    replaceFragment(new HistroyFragment(), currentUser);
                }
                fab.setVisibility(View.GONE);
                return  true;
            }
            if(item.getItemId() == R.id.notification){
                replaceFragment(new NotificationFragment(), currentUser);
                fab.setVisibility(View.GONE);
                return  true;
            }
            if(item.getItemId() == R.id.profile){
                replaceFragment(new ProfileFragment(), currentUser);
                fab.setVisibility(View.GONE);
                return  true;
            }
            return false;
        });

    }

    private void replaceFragment(Fragment fragment, FirebaseUser currentUser) {

        Bundle args = new Bundle();
        args.putParcelable("CURRENT_USER", currentUser);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

    private void redirectToLogin() {
        Intent intent = new Intent(FragmentHandler.this, LogInActivity.class);
        startActivity(intent);
    }
}