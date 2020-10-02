package com.wordpress.smartedudotin.www.newsfakeornot;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wordpress.smartedudotin.www.newsfakeornot.authentication.AuthActivity;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class UserFragment extends Fragment {

    private ParseUser currentUser;

    private TextView userName;
    private Button logOutBtn;
    private TextView userEmail;
    private Button resetPasswordBtn;
    private ImageView userImage;
    private  ParseFile parseFile;

    private static final String USEOobjID = "4BwpMWdCnm";

    
    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment ();

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
        return inflater.inflate (R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        currentUser = ParseUser.getCurrentUser ();

        userName =view.findViewById (R.id.user_name);
        userImage = view.findViewById (R.id.user_image);
        userEmail = view.findViewById (R.id.user_email);

        logOutBtn = view.findViewById (R.id.logoutBtn);
        resetPasswordBtn = view.findViewById (R.id.reset_password);

        userEmail.setText (currentUser.getEmail ());

        userImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                UserImageUpdate();
            }
        });


  readUserObject (getContext (),currentUser.getObjectId (),  "userDisplayName");

        userName.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                UserDisplayNameUpdate ();
            }
        });

        logOutBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                LogoutUser ();
            }
        });

        resetPasswordBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                ResetPassword ();
            }
        });
    }

    public void LogoutUser(){
        ParseUser.logOut ();

        Toast.makeText (getContext (), "Logging out user", Toast.LENGTH_SHORT).show ();

        getActivity ().startActivity (new Intent (getContext (), AuthActivity.class));

        getActivity ().finish ();
    }

    public void ResetPassword(){

        // An e-mail will be sent with further instructions
        ParseUser.requestPasswordResetInBackground(currentUser.getEmail (), new RequestPasswordResetCallback () {
            public void done(ParseException e) {
                if (e == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder (getContext ()).setMessage ("An email is successfully sent to your registered Email").setCancelable (true);
                    builder.create ();
                    builder.show ();

                    // An email was successfully sent with reset instructions.
                } else {
                    // Something went wrong. Look at the ParseException to see what's up.
                }
            }
        });

    }

    public void UserDisplayNameUpdate(){
        final EditText input = new EditText (getContext ());
        input.setInputType (InputType.TYPE_CLASS_TEXT);
        new AlertDialog.Builder(getContext ()).setTitle ("Enter New UserName").setView (input).setPositiveButton ("Update", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            new ParseQueries ().updateUserObject (getContext (), currentUser.getObjectId (),input.getText ().toString (), "userDisplayName" );

            userName.setText (input.getText ().toString ());
            }
        }).setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel ();
            }
        }).setCancelable (false).show ();


    }

    public void UserImageUpdate(){
        new AlertDialog.Builder (getContext ()).setTitle ("Select Action").setCancelable (true).setPositiveButton ("Update Image", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ImageUpload();
            }
        }).setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss ();
            }
        }).show ();
    }

    public void readUserObject(final Context  context,String objId, final String itemKey) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");

        // The query will search for a ParseObject, given its objectId.
        // When the query finishes running, it will invoke the GetCallback
        // with either the object, or the exception thrown
        query.getInBackground(currentUser.getObjectId (), new GetCallback<ParseObject>() {
            public void done(ParseObject result, ParseException e) {
                if (result!=null && e == null) {
                   userName.setText ( result.get(itemKey)+"");
                    ((ParseFile)result.get ("profileImage")).getDataInBackground (new GetDataCallback () {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (data != null && e==null)
                            userImage.setImageBitmap (BitmapFactory.decodeByteArray (data,0,data.length));
                        }
                    });

                } else {
                    FancyToast.makeText (context, "Error occurred in loading name", FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show ();
                    // something went wrong
                }
            }
        });

    }

    private void ImageUpload(){
        if (Build.VERSION.SDK_INT>=23 && getActivity ().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions (new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 3000);
        }else{

            captureImage();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);

        if (requestCode==3000 ){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                captureImage ();
            }
        }
    }

    private void captureImage() {
        Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult (intent, 4000);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult (requestCode, resultCode, data);


        if (requestCode==4000 && data!= null && resultCode == RESULT_OK) {

            try {

                Uri capturedImage = data.getData ();

                userImage.setImageURI (capturedImage);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap (getActivity ().getContentResolver(), capturedImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
                bitmap.compress (Bitmap.CompressFormat.PNG ,40,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray ();


                parseFile = new ParseFile ("user.png",bytes );
                 ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("_User");

                final ProgressDialog progressDialog = new ProgressDialog (getContext ());
                progressDialog.setMessage ("Uploading...");
                progressDialog.setCancelable (false);
                progressDialog.show ();

                parseQuery.getInBackground (currentUser.getObjectId (), new GetCallback<ParseObject> () {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null)
                       { object.put ("profileImage", parseFile);
                       object.saveInBackground ();
                        FancyToast.makeText (getContext (),"image uploaded", FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show ();
                       }
                        else {
                            e.printStackTrace ();
                        }
                        progressDialog.dismiss ();
                    }
                });


            }catch (Exception e){
                e.printStackTrace ();
            }

        }


    }


}