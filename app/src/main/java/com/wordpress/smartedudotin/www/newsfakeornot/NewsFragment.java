package com.wordpress.smartedudotin.www.newsfakeornot;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.JsonElement;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;



public class NewsFragment extends Fragment {

    private   ArrayList<NewsFeed> newsFeeds;
   // private Uri imageUri = Uri.parse ("https://i.pinimg.com/originals/24/39/a6/2439a657128437d7b308e112f05c2b70.png");

    private NewsFeedAdapter newsFeedAdapter;
    private   ListView listView;
    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment ();

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
        return inflater.inflate (R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

      newsFeeds= new ArrayList<NewsFeed> ();
        loadNewsFeed ();
        newsFeedAdapter = new NewsFeedAdapter (getActivity (), R.layout.news_feed, newsFeeds);

         listView = view.findViewById (R.id.list_news);


    }


    public void loadNewsFeed(){

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery ("NewsFeed");
        FancyToast.makeText (getContext (),"loading news",FancyToast.LENGTH_SHORT,FancyToast.CONFUSING,false).show ();

        final ProgressDialog progressDialog = new ProgressDialog (getContext ());
        progressDialog.setMessage ("loading...");
        progressDialog.setCancelable (false);
        progressDialog.show ();

        parseQuery.findInBackground (new FindCallback<ParseObject> () {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects != null && e==null){


                for(ParseObject po : objects){

//                    UserResponse[] userFalseArray;
//                    UserResponse[] userTrueArray;
//                    if ( po.get ("isFalseResponseBy")!=null){
//                     userFalseArray = new Gson().fromJson((JsonElement) po.get ("isFalseResponseBy"), UserResponse[].class);}
//                    else {  userFalseArray= new UserResponse[]{new UserResponse (" null")};
//                    }
//                    if (po.get ("isTrueResponseBy")!=null){
//                     userTrueArray = new Gson().fromJson((JsonElement) po.get ("isTrueResponseBy"), UserResponse[].class);}
//                    else { userTrueArray = new UserResponse[]{new UserResponse ("null")};}

                    ArrayList<String> arrayListTrue = new ArrayList<> ();
                    //arrayListTrue.addAll (Collections.singleton (po.get ("isTrueResponseBy").toString ()));

                    arrayListTrue = (ArrayList<String>) po.get ("isTrueResponseBy");

                    ArrayList<String> arrayListFalse = new ArrayList<> ();
                    //arrayListFalse.addAll (Collections.singleton (po.get ("isFalseResponseBy").toString ()));

                    arrayListFalse= (ArrayList<String>) po.get ("isFalseResponseBy");

                    newsFeeds.add (new NewsFeed (po.getObjectId (),
                            (ParseFile) po.get ("newsImage"),
                            po.get ("newsTitle")+"",
                            po.get ("newsBody")+"",
                          arrayListTrue,
                           arrayListFalse));




                }
                    listView.setAdapter (newsFeedAdapter);
                progressDialog.dismiss ();

              }
                if (e!= null) FancyToast.makeText (getContext (),"error in loading news",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show ();

            }
        });

    }
}