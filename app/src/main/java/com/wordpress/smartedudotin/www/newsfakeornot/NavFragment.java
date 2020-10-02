package com.wordpress.smartedudotin.www.newsfakeornot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class NavFragment extends Fragment {


    public NavFragment() {
        // Required empty public constructor
    }


    public static NavFragment newInstance(String param1, String param2) {
        NavFragment fragment = new NavFragment ();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate (R.layout.fragment_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        final NavController navController = Navigation.findNavController (view);

        Button toUser = view.findViewById (R.id.toUser);
        toUser.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                navController.navigate (R.id.action_navFragment_to_userFragment5);
            }
        });

        Button toNews = view.findViewById (R.id.toNews);
        toNews.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                navController.navigate (R.id.action_navFragment_to_newsFragment4);
            }
        });

        Button toNewNews = view.findViewById (R.id.toAddNews);
        toNewNews.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                navController.navigate (R.id.action_navFragment_to_newNewsFragment);
            }
        });

    }
}