package com.wordpress.smartedudotin.www.newsfakeornot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        SwitchFeedFragment ();


        BottomNavigationView bottomNavigationView = findViewById (R.id.bottom_nav_bar);

       bottomNavigationView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener () {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

               switch(menuItem.getItemId ()){
                   case R.id.news_feed_item: SwitchFeedFragment ();
                   return true;
                   case R.id.new_news_upload_item: SwitchAddNewsFragment ();
                   return true;
                   case R.id.user_item: SwitchUserFragment ();
                   return true;


               }

               return false;
           }
       });
    }


    public void SwitchFeedFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager ();
        fragmentManager.beginTransaction ().replace (R.id.mainFragment,new NewsFragment ()).commit ();

    }
    public void SwitchAddNewsFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager ();
        fragmentManager.beginTransaction ().replace (R.id.mainFragment,new NewNewsFragment() ).commit ();

    }
    public void SwitchUserFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager ();
        fragmentManager.beginTransaction ().replace (R.id.mainFragment,new UserFragment ()).commit ();

    }
}