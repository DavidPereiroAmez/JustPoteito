package com.example.justpoteito;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    TextView nameApp;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        nameApp = findViewById(R.id.appName);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_fade_in);
        logo.setAnimation(logoAnimation);

        animatedText("JustPoteito");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,
                        MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }, 4000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            nameApp.setText(charSequence.subSequence(0, index++));

            if(index <= charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };

    public void animatedText(CharSequence cs){
      charSequence = cs;
      index = 0;

      nameApp.setText(R.string.app_name);

      handler.removeCallbacks(runnable);
      handler.postDelayed(runnable, delay);
    }
}