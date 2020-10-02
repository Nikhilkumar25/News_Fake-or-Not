package com.wordpress.smartedudotin.www.newsfakeornot;

import android.content.Context;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ParseQueries {

   private Object s = " ";

    public void updateUserObject(final Context context, String objId, final String input, final String itemKey) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");

        // Retrieve the object by id
        query.getInBackground(objId, new GetCallback<ParseObject>() {
            public void done(ParseObject entity, ParseException e) {
                if (e == null) {
                    // Update the fields we want to
                    entity.put(itemKey, input);

                    // All other fields will remain the same
                    entity.saveInBackground();

                    FancyToast.makeText (context, "User Updated", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,false).show ();
                }

                if (e!= null) FancyToast.makeText (context, "Error occurred in updating", FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show ();

            }
        });
    }

        public Object readUserObject(final Context  context,String objId, final String itemKey) {


            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");

            // The query will search for a ParseObject, given its objectId.
            // When the query finishes running, it will invoke the GetCallback
            // with either the object, or the exception thrown
            query.getInBackground(objId, new GetCallback<ParseObject>() {
                public void done(ParseObject result, ParseException e) {
                    if (result!=null && e == null) {
                      s =  result.get(itemKey);

                    } else {
                        FancyToast.makeText (context, "Error occurred in loading name", FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show ();
                        // something went wrong
                    }
                }
            });

            return s;
        }


}
