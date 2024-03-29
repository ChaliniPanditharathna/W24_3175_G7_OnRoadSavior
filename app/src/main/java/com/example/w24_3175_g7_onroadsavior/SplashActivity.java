package com.example.w24_3175_g7_onroadsavior;

import androidx.appcompat.app.AppCompatActivity;



import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    //variables
    Animation topAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        ImageView imgViewLogo = findViewById(R.id.imgViewLogo);
        TextView txtViewAppName = findViewById(R.id.txtViewAppName);
        TextView txtViewTagLine = findViewById(R.id.txtViewTagLine);

        imgViewLogo.setAnimation(topAnim);
        txtViewAppName.setAnimation(bottomAnim);
        txtViewTagLine.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LogInActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(imgViewLogo, "logo_image");
                pairs[1] = new Pair<View, String>(txtViewAppName, "app_name");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREEN);
    }
}