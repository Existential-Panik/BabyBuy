package com.example.babybuy;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    ImageView splImg;
    TextView splTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        findId();

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        splImg.setAnimation(animation);
        splTxt.setAnimation(animation);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), SignUp.class);
                    startActivity(intent);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void findId(){
        splImg = findViewById(R.id.splashImg);
        splTxt = findViewById(R.id.splashText);
    }
}