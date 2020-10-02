package com.wordpress.smartedudotin.www.newsfakeornot;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class NewNewsFragment extends Fragment {

    private EditText news;
    private ParseUser user;
    private EditText newsTitle;
    private ImageView uploadImageView;

    private  byte[] bytes;
    private int x;

    public NewNewsFragment() {
        // Required empty public constructor
    }


    public static NewNewsFragment newInstance(String param1, String param2) {
        NewNewsFragment fragment = new NewNewsFragment ();

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
        return inflater.inflate (R.layout.fragment_new_news, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        x = -1;
        user = ParseUser.getCurrentUser ();
        newsTitle = view.findViewById (R.id.new_news_Title);
        uploadImageView = view.findViewById (R.id.newsImageView);
          news = view.findViewById (R.id.newNews);
        Button submit = view.findViewById (R.id.submitBtn);


        submit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                try {
                    uploadNewNews();
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
                // Retrieve the object by id

            }
        });


        final Button imageUploadBtn = view.findViewById (R.id.newsImageUpload);
        imageUploadBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                ImageUpload ();
            }
        });

        //buttons of uploader response

//
//        final Button[] uploaderResponse = new Button[]{view.findViewById (R.id.uploader_false_response), view.findViewById (R.id.uploader_true_response),view.findViewById (R.id.uploader_null_response) };
//
//        uploaderResponse[0].setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View view) {
//
//                if (x==1){
//                    uploaderResponse[1].setActivated (false);
//
//                }
//                if (x==2){
//                    uploaderResponse[2].setActivated (false);
//                }
//
//                if(x==0){
//                    view.setActivated (false);
//                    x=-1;
//                }else {view.setActivated (true);
//               x=0;}
//            }
//        });
//
//        uploaderResponse[1].setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View view) {
//
//                if (x==0){
//                    uploaderResponse[0].setActivated (false);
//
//                }
//                if (x==2){
//                    uploaderResponse[2].setActivated (false);
//                }
//
//                if(x==1){
//                    view.setActivated (false);
//                    x=-1;
//                }else {view.setActivated (true);
//                    x=1;}
//            }
//        });
//
//        uploaderResponse[2].setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View view) {
//
//
//                if (x==1){
//                    uploaderResponse[1].setActivated (false);
//
//                }
//                if (x==0){
//                    uploaderResponse[0].setActivated (false);
//                }
//
//                if(x==2){
//                    view.setActivated (false);
//                    x=-1;
//                }else {view.setActivated (true);
//                    x=2;}
//            }
//        });



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

                uploadImageView.setImageURI (capturedImage);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap (getActivity ().getContentResolver(), capturedImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
                bitmap.compress (Bitmap.CompressFormat.PNG ,80,byteArrayOutputStream);
                bytes = byteArrayOutputStream.toByteArray ();


                FancyToast.makeText (getContext (),"image selected", FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show ();
//                ParseFile parseFile = new ParseFile ("img.png",bytes );
//                ParseObject parseObject = new ParseObject ("NewsFeed");
//                parseObject.put ("newsImage", parseFile);
//
//                final ProgressDialog progressDialog = new ProgressDialog (getContext ());
//                progressDialog.setTitle ("Loading...");
//                progressDialog.show ();
//
//                parseObject.saveInBackground (new SaveCallback () {
//                    @Override
//                    public void done(ParseException e) {
//
//                        if (e==null){
//                            FancyToast.makeText (getContext (),"Image Uploaded", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show ();
//                        }else {
//                            FancyToast.makeText (getContext (),"Image is not getting Uploaded", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show ();
//
//                        }
//                        progressDialog.dismiss ();}
//                });
            }catch (Exception e){
                e.printStackTrace ();
            }

        }


    }

    public void uploadNewNews() throws JSONException {

        ParseFile parseFile = new ParseFile ("img.png",bytes );
        ParseObject parseObject = new ParseObject ("NewsFeed");
        parseObject.put ("newsImage", parseFile);
        parseObject.put ("newsTitle" , newsTitle.getText ().toString ());
        parseObject.put ("newsBody", news.getText ().toString ());
        parseObject.put ("uploaderId", user.getEmail ());
        parseObject.put ("uploaderPoint", user);
//        switch (x){
//            case 2:
//                parseObject.put ("isTrueResponseBy",  (new UserResponse (" null").toString ()));
//                parseObject.put ("isFalseResponseBy",  (new UserResponse (" null")));
//
//            case 1:
//        parseObject.put ("isTrueResponseBy",  (new UserResponse (user.getEmail ())));
//                parseObject.put ("isFalseResponseBy",  (new UserResponse (" null")));
//            case 0:
//        parseObject.put ("isFalseResponseBy",  (new UserResponse (user.getEmail ()).toString ()));
//                parseObject.put ("isTrueResponseBy",  (new UserResponse (" null").toString ()));
//            case -1:
//                parseObject.put ("isTrueResponseBy",  (new UserResponse (" null")));
//                parseObject.put ("isFalseResponseBy",  (new UserResponse (" null")));}


        final ProgressDialog progressDialog = new ProgressDialog (getContext ());
        progressDialog.setMessage ("Uploading...");
        progressDialog.setCancelable (false);
        progressDialog.show ();

        parseObject.saveInBackground (new SaveCallback () {
            @Override
            public void done(ParseException e) {

                if (e==null){
                    FancyToast.makeText (getContext (),"News Uploaded", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show ();

                    newsTitle.setText ("");
                    news.setText ("");
                    uploadImageView.setImageURI (null);
                }else {
                    FancyToast.makeText (getContext (),"News is not getting Uploaded", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show ();
                }

                progressDialog.dismiss ();
            }
        });

    }



}