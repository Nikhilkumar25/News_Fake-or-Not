package com.wordpress.smartedudotin.www.newsfakeornot;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

public class NewsFeedAdapter extends ArrayAdapter<NewsFeed> {

    private static final int PROGRESS_MAX = 100;

    private Drawable dr;
    private Context context;

    private ImageView newsImage;
    private Button falseBtn;
    private  Button trueBtn;

    private TextView falseCount;
    private TextView trueCount;



    public NewsFeedAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NewsFeed> objects) {
        super (context, resource, objects);
        this.context = context;

        dr = context.getDrawable (R.drawable.custom_progressbar);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View newsFeedListItem = convertView;

        if (newsFeedListItem==null){
            newsFeedListItem = LayoutInflater.from (getContext ()).inflate (R.layout.news_feed,parent,false);
        }

        final NewsFeed currentNews = getItem (position);

//        assert currentNews != null;

//        final int[] btnClickCheck = {0};

//       ProgressBar progressBar = newsFeedListItem.findViewById (R.id.progressBar);
//        progressBar.setMax (currentNews.getIsTrueCount ()+currentNews.getIsFalseCount ());
//        progressBar.setProgressDrawable (dr);


//       progressBar.setProgress (currentNews.getIsTrueCount ());

        //progressBar.setProgress (50);

          newsImage = newsFeedListItem.findViewById (R.id.news_image);

          if (currentNews.getImage () != null){

        currentNews.getImage ().getDataInBackground (new GetDataCallback () {
            @Override
            public void done(byte[] data, ParseException e) {
                newsImage.setImageBitmap (BitmapFactory.decodeByteArray (data,0,data.length));
            }
        });} else {
              newsImage.setImageResource (R.drawable.empty_profile_img);
          }

          /** News Body and Title*/
        TextView newsBody = newsFeedListItem.findViewById (R.id.news_text);
        newsBody.setText (currentNews.getNewsBody ());

        TextView newsTitle = newsFeedListItem.findViewById (R.id.news_title);
        newsTitle.setText (currentNews.getNewsTitle ());
        /**News Body and title*/

          trueCount = newsFeedListItem.findViewById (R.id.trueCount);
          falseCount = newsFeedListItem.findViewById (R.id.falseCount);

       trueBtn = newsFeedListItem.findViewById (R.id.trueBtn);
      falseBtn = newsFeedListItem.findViewById (R.id.falseBtn);

      trueCount.setText (currentNews.getIsTrueCount ()+"");
    falseCount.setText (currentNews.getIsFalseCount ()+"");

      if (currentNews.isFalseResponded () || currentNews.isTrueResponded ()){
          if (currentNews.isFalseResponded ()){
              falseBtn.setActivated (true);
              falseBtn.setClickable (false);

              trueBtn.setClickable (false);
          }
          if (currentNews.isTrueResponded ()){
              falseBtn.setActivated (true);
              trueBtn.setClickable (false);

              falseBtn.setClickable (false);
          }
      }

        trueBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                isTrueSelected(currentNews.getObjId (), currentNews.getUserTrueResponse ());
//
//                trueCount.setText (currentNews.getIsTrueCount ()+1+"");
//                falseCount.setText (currentNews.getIsFalseCount ()+"");

                view.setActivated (true);

                view.setClickable (false);
                falseBtn.setClickable (false);
            }
        });


        falseBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                isFalseSelected(currentNews.getObjId (), currentNews.getUserFalseResponse ());

//                trueCount.setText (currentNews.getIsTrueCount ()+"");
//                falseCount.setText (currentNews.getIsFalseCount ()+1+"");

                view.setActivated (true);

                view.setClickable (false);
                trueBtn.setClickable (false);
            }

        });





        return newsFeedListItem;

    }

    public void isTrueSelected(String ObjId, final ArrayList<String> userResponses) {

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery ("NewsFeed");



        parseQuery.getInBackground (ObjId, new GetCallback<ParseObject> () {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object!= null && e==null){

                    Toast.makeText (context, "Reacting", Toast.LENGTH_SHORT).show ();
                    userResponses.add (ParseUser.getCurrentUser ().getEmail ());


                    object.put ("isTrueResponseBy", userResponses );
                    object.saveInBackground ();
                }
            }
        });

        countUpdate (ObjId);
      }


    public void isFalseSelected(String ObjId, final ArrayList<String> userResponses) {

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery ("NewsFeed");

        parseQuery.getInBackground (ObjId, new GetCallback<ParseObject> () {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object!= null && e==null){

                    Toast.makeText (context, "Reacting", Toast.LENGTH_SHORT).show ();
                    userResponses.add (ParseUser.getCurrentUser ().getEmail ());


                    object.put ("isFalseResponseBy", userResponses );
                    object.saveInBackground ();
                }
            }
        });

        countUpdate(ObjId);
    }

    public void countUpdate(String ObjId){

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery ("NewsFeed");

        parseQuery.getInBackground (ObjId, new GetCallback<ParseObject> () {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object!= null && e==null){
                    ArrayList<String> arrayListT = new ArrayList<> ();
                    arrayListT = (ArrayList<String>)object.get ("isTrueResponseBy");

                    trueCount.setText (arrayListT.size ()+" ");

                    ArrayList<String> arrayListF = new ArrayList<> ();
                    arrayListF = (ArrayList<String>)object.get ("isFalseResponseBy");
                    falseCount.setText (arrayListF.size () +" ");

                    object.saveInBackground ();
                }
            }
        });


    }




}
