package com.example.wicketyandroid;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class AuthenticationEntry extends AppCompatActivity {
    private static final String TAG = "AuthenticationEntry";
    int RC_SIGN_IN = 101;
    Button guestButton, googleSignInButton;
    TextView replyTextView;
    ImageView imageView;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount account;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a04_authentication_entry);
        guestButton = (Button) findViewById(R.id.guestButton_a04);
        googleSignInButton = (Button) findViewById(R.id.googleSignIn_button_a04);
        replyTextView = (TextView) findViewById(R.id.textView_a04_reply);
        imageView = (ImageView) findViewById(R.id.imageView_login);


        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account!=null) {

                    googleSignInClient.signOut();
                    account=null;
                    updateUI(account,"Signed Out");
                }
                Intent i = new Intent(AuthenticationEntry.this, ScoreBoard.class);
                startActivity(i);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyTextView.setText("Please wait ....");
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    String resultText = "";

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {


        try {
             account = completedTask.getResult(ApiException.class);
            updateUI(account,"OK");
            Intent i = new Intent(AuthenticationEntry.this, ScoreBoard.class);
            startActivity(i);

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {

            Log.w(TAG, resultText);

            updateUI(null,e.getMessage());
        }
    }

    private void updateUI(GoogleSignInAccount account,String code) {

        if(account!=null) {

            replyTextView.setText("Welcome : " + account.getDisplayName());
            imageView.setImageURI(account.getPhotoUrl());
            googleSignInButton.setText("Continue As " + account.getEmail());
        }else{
            replyTextView.setText("Authenticated or Guest ?");
            imageView.setImageURI(null);
            googleSignInButton.setText("Login with Google  ");

        }
    }
}








