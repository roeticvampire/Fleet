package com.roeticvampire.fleet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if(user!=null)
                i=new Intent(SplashScreen.this,
                        ChatlistActivity.class);
                else
                    i=new Intent(SplashScreen.this,
                            welcome2Activity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.
                String username= ((CustomApplication) SplashScreen.this.getApplication()).getUsername();
                finish();
                //the current activity will get finished.
            }
        }, 3000);
    }
}