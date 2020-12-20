package com.codingburg.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.codingburg.covid19.activity.MainActivity;

public class SplashscreenActivity extends AppCompatActivity {
    private static int SPLASH_Time = 3000;
    private String country;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    Animation top_anumation, buttom_animation, middel_animation;
    private TextView name, news1,news2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        top_anumation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        buttom_animation = AnimationUtils.loadAnimation(this,R.anim.buttom_animation);
        middel_animation = AnimationUtils.loadAnimation(this,R.anim.middel_animation);
        name = findViewById(R.id.name);
        news1 = findViewById(R.id.news1);
        news2 = findViewById(R.id.news2);
        name.setAnimation(top_anumation);
        news1.setAnimation(middel_animation);
        news2.setAnimation(buttom_animation);


        loadData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(country != null){
                    Intent homeIntent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                if(country == ""){
                    Intent homeIntent=new Intent(getApplicationContext(), YourCountryActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        },SPLASH_Time);
    }

   public void loadData() {
       SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
       String text = sharedPreferences.getString(TEXT, "");
       country = text;

   }

}