package com.wordpress.smartedudotin.www.newsfakeornot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;
import com.wordpress.smartedudotin.www.newsfakeornot.authentication.AuthActivity;


public class SplashscreenActivity extends Activity {

    private static final int SPLASH_TIME_OUT= 1000;//1000 milli seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splashscreen);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.


 getCurrentUser ();
    }

    public void getCurrentUser() {
        // After login, Parse will cache it on disk, so
        // we don't need to login every time we open this
        // application
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            IfUserNotNull ();
        } else {
            IfUserIsNull ();
            // show the signup or login screen
        }
    }


    public void IfUserNotNull(){
        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashscreenActivity.this,
                        MainActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_TIME_OUT);
    }

    public void IfUserIsNull(){

        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashscreenActivity.this,
                        AuthActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_TIME_OUT);
    }

}



