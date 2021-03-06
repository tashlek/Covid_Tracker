package com.developer.arsltech.covid_19tracker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    TextView covid, tracker;
    LottieAnimationView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setNavigationBarColor(this.getResources().getColor(android.R.color.black));
        }
        covid = findViewById(R.id.covid);
        tracker = findViewById(R.id.tracker);
        covid.setVisibility(View.GONE);
        tracker.setVisibility(View.GONE);
        splash = findViewById(R.id.splash);
        splash.setVisibility(View.GONE);
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.internet_check_dialog);
            dialog.setCanceledOnTouchOutside(true);
            Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button btnTryAgain = dialog.findViewById(R.id.btn_try_again);
            btnTryAgain.setOnClickListener(v -> recreate());
            YoYo.with(Techniques.Tada)
                    .repeat(-1)
                    .playOn(btnTryAgain);
            YoYo.with(Techniques.Swing)
                    .repeat(-1)
                    .playOn(dialog.findViewById(R.id.logo));
            dialog.show();
        }
        else
        {
            splash.setVisibility(View.VISIBLE);

            YoYo.with(Techniques.Flash)
                    .duration(300)
                    .repeat(2)
                    .playOn(splash);
            new Handler().postDelayed(() -> {
                covid.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Swing)
                        .duration(500)
                        .playOn(covid);
            }, 1000);
            new Handler().postDelayed(() -> {
                tracker.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Shake)
                        .duration(500)
                        .repeat(1)
                        .playOn(tracker);
            }, 1500);
            new Handler().postDelayed(() -> YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(1)
                    .playOn(covid), 1750);
            new Handler().postDelayed(() -> {
                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .repeat(3)
                        .playOn(covid);
                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .repeat(3)
                        .playOn(tracker);
            }, 2250);
            new Handler().postDelayed(() -> {
                YoYo.with(Techniques.FadeOut)
                        .duration(500)
                        .playOn(covid);
                YoYo.with(Techniques.FadeOut)
                        .duration(500)
                        .playOn(tracker);
            }, 3500);
            new Handler().postDelayed(() -> {
                covid.setVisibility(View.GONE);
                tracker.setVisibility(View.GONE);
                YoYo.with(Techniques.Flash)
                        .duration(300)
                        .repeat(2)
                        .playOn(splash);
            }, 4000);
            new Handler().postDelayed(() -> YoYo.with(Techniques.Swing)
                    .duration(200)
                    .playOn(splash), 4300);
            new Handler().postDelayed(() -> YoYo.with(Techniques.FadeOut)
                    .duration(200)
                    .playOn(splash), 4400);

            new Handler().postDelayed(() -> {
                splash.setVisibility(View.GONE);
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }, 4500);

        }
    }
    @Override
    public void onBackPressed() {
    }
}