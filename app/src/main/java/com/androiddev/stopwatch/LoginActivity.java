package com.androiddev.stopwatch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


/**
 * Login page
 * @author Thilina Weerasinghe - CT/2017/068
 * @version 1.0
 * used google btn there
 * Used firebase to login with google account
 * need to add plugins and dependencies to build.gradle file
 * need to add json file given by firebase to the correct path
 *
 */
public  class LoginActivity extends AppCompatActivity {
    //Declare global variables
    int SIGN_IN = 0;
    SignInButton signInBtn;
    GoogleSignInClient GSignInClient;

    /**
     * connect button to xml view
     * configure google sign in
     *  @param savedInstanceState savedInstanceState is a reference to a Bundle object that is passed into the onCreate method of Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //connect button to xml view
        signInBtn = findViewById(R.id.sign_in_button);  //sign in button
        //configure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //get email

        GSignInClient = GoogleSignIn.getClient(this, gso);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }  // call sign in method
        });
    }

    private void signIn() {
        Intent signInIntent = GSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * handle the google sign in
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
//handle sign in results
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account;
            account = completedTask.getResult(ApiException.class);
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));  //if valide move to HomeActivity.class
        } catch (ApiException e) {
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode()); //print error on logcat
            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();      //if failed display "faild message"
        }
    }

    /**
     * if success to login with google open the next activity
     */
    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            //If sign in success move to MainActivity.class
        }
        super.onStart();
    }
}




