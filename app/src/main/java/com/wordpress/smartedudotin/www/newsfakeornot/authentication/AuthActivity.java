package com.wordpress.smartedudotin.www.newsfakeornot.authentication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;
import com.wordpress.smartedudotin.www.newsfakeornot.MainActivity;
import com.wordpress.smartedudotin.www.newsfakeornot.R;
import com.wordpress.smartedudotin.www.newsfakeornot.SplashscreenActivity;

import java.util.zip.Inflater;

public class AuthActivity extends AppCompatActivity {

    private LinearLayout signUpLl;
    private LinearLayout signInLl;

    // SignIn
    private EditText signInEmail;
    private EditText signInPassword;
    private Button signInBtn;

    //SignUp
    private EditText signUpUsername;
    private EditText signUpEmail;
    private EditText signUpPassword;
    private Button signUpBtn;

    // Forgot Password
    private Button forgotPassBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_auth);

        Button toSignInFragBtn = findViewById (R.id.toSignInFrag);
        Button toSignUpFragBtn = findViewById (R.id.toSignUpFrag);
        signUpLl = findViewById (R.id.signUpFragment);
        signInLl = findViewById (R.id.signInFragment);

        // switching between sign up and sign in
        toSignUpFragBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                signInLl.setVisibility (View.GONE);
                signUpLl.setVisibility (View.VISIBLE);}
        });

        toSignInFragBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                signUpLl.setVisibility (View.GONE);
                signInLl.setVisibility (View.VISIBLE);
            }
        });

        /*///*Sign In part\\*/
        signInEmail = findViewById (R.id.registeredUsername);
        signInPassword = findViewById (R.id.registeredPassword);
        signInBtn = findViewById (R.id.signInBtn);
        signInBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(AuthInputCheck ( signInPassword.getText ().toString (),signInEmail.getText ().toString ().toLowerCase ()))
                SignInUser (signInEmail.getText ().toString ().toLowerCase (), signInPassword.getText ().toString ());
            }
        });


        /*//Sign Up/*/
       // signUpUsername = findViewById (R.id.registrationUsername);
        signUpEmail = findViewById (R.id.registrationEmail);
        signUpPassword = findViewById (R.id.registrationPassword);
        signUpBtn = findViewById (R.id.signUpBtn);

        /*  Forgot Password*/
        forgotPassBtn = findViewById (R.id.forgotPassBtn);
        forgotPassBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText (AuthActivity.this);
                input.setInputType (InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
               new AlertDialog.Builder(AuthActivity.this).setTitle ("Enter Email").setView (input).setPositiveButton ("Send Request", new DialogInterface.OnClickListener () {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                      ResetPassword (input.getText ().toString ());
                   }
               }).setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel ();
                   }
               }).setCancelable (false).show ();
            }
        });

        signUpBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                if(AuthInputCheck ( signUpPassword.getText ().toString (), signUpEmail.getText ().toString ()))
                SignUpMethod (signUpEmail.getText ().toString ().toLowerCase (), signUpPassword.getText ().toString (), signUpEmail.getText ().toString ().toLowerCase ());
            }
        });
    }


    public void SignInUser(String username,final String password){
        ParseUser.logInInBackground(username,password, new LogInCallback () {
            final ProgressDialog progressDialog = ProgressDialog.show (AuthActivity.this,"", " Signing In.. Please wait...",true );

            public void done(ParseUser user, ParseException e) {
                progressDialog.dismiss ();
                if (user != null) {
                    // Hooray! The user is logged in.
                    Boolean emailVerified = user.getBoolean("emailVerified");
                    if (!emailVerified){
                        AlertDialog.Builder builder = new AlertDialog.Builder (AuthActivity.this).setMessage ("Your email is not verified").setCancelable (false);
                        builder.create (); }

                    if (emailVerified){

                        startActivity (new Intent (AuthActivity.this, MainActivity.class));
                        finish ();

                    }

                } else {
                    // Signup failed. Look at the ParseException to see what happened.

                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    if (password.toLowerCase ().equals (password)){
                        Toast.makeText (AuthActivity.this, "Password doesn't follow required format", Toast.LENGTH_SHORT).show ();}

                    else if (password.length ()<8){
                        Toast.makeText (AuthActivity.this, "Password doesn't follow required format", Toast.LENGTH_SHORT).show ();}
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong

                    else { Toast.makeText (AuthActivity.this, "Something went wrong Please check the filled details", Toast.LENGTH_SHORT).show ();}}

            }

        });
    }

    public void SignUpMethod(String username , final String password, final String email){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Other fields can be set just like any other ParseObject,
        // using the "put" method, like this: user.put("attribute", "its value");
        // If this field does not exists, it will be automatically created

        final ProgressDialog progressDialog = ProgressDialog.show (AuthActivity.this,"", " Signing Up.. Please wait...",true );

        progressDialog.show ();

        user.signUpInBackground(new SignUpCallback () {
            public void done(ParseException e) {
                progressDialog.dismiss ();
                if (e == null) {

                    ParseUser.logOut ();

                    AlertDialog.Builder builder = new AlertDialog.Builder (AuthActivity.this).setMessage ("Check you mailbox for email verification").setCancelable (true);
                    builder.create ();
                    builder.show ();

                    // Hooray! Let them use the app now.
                } else {

                     { Toast.makeText (AuthActivity.this, "Something went wrong Please check the filled details", Toast.LENGTH_SHORT).show ();}}
            }
        });
    }

    public boolean AuthInputCheck( String password, String email) {


        if (!email.contains ("@") || !email.contains (".")) {
            Toast.makeText (AuthActivity.this, "invalid Email format", Toast.LENGTH_SHORT).show ();

            return false;
        } else if (password.equals (password.toLowerCase ())) {
            Toast.makeText (AuthActivity.this, "Password doesn't follow required format", Toast.LENGTH_SHORT).show ();
            return false;
        } else if (password.length () < 8) {
            Toast.makeText (AuthActivity.this, "Password doesn't follow required format", Toast.LENGTH_SHORT).show ();
            return false;
        }
        // Sign up didn't succeed. Look at the ParseException
        // to figure out what went wrong

        return true;
    }







    @Override
    protected void onResume() {
        super.onResume ();
        getCurrentUser ();
    }

    public void getCurrentUser() {
        // After login, Parse will cache it on disk, so
        // we don't need to login every time we open this
        // application
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            startActivity (new Intent (AuthActivity.this, MainActivity.class));

            finish ();
        }  // show the signup or login screen

    }


    public void ResetPassword(String email){

        // An e-mail will be sent with further instructions
        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback () {
            public void done(ParseException e) {
                if (e == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder (AuthActivity.this).setMessage ("An email is successfully sent to you").setCancelable (true);
                    builder.create ();
                    builder.show ();

                    // An email was successfully sent with reset instructions.
                } else {
                    // Something went wrong. Look at the ParseException to see what's up.
                }
            }
        });

    }
}