package com.platzi.platzigram.login.view;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.Toolbar;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.platzi.platzigram.R;

import java.util.Arrays;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG = "CreateAccountActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    //private CallbackManager callbackManager;

    private Button btnJoinUs;
    private TextInputEditText edtEmail, edtPassword;
    //private LoginButton loginButtonFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_tittle_createaccont), true);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        //callbackManager = CallbackManager.Factory.create();

        btnJoinUs = (Button) findViewById(R.id.joinUs);
        edtEmail = (TextInputEditText) findViewById(R.id.email);
        edtPassword = (TextInputEditText) findViewById(R.id.password_createaccount);
        //loginButtonFacebook = (LoginButton) findViewById(R.id.login_facebook);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "Usuario logeado" + firebaseUser.getEmail());
                } else {
                    Log.w(TAG, "Usuario No logeado" );
                }
            }
        };



        btnJoinUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        /*loginButtonFacebook.setReadPermissions(Arrays.asList("email"));
        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.w(TAG, "Facebook Login Success Token: " + loginResult.getAccessToken().getApplicationId());
                signInFacebookFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                    Log.w(TAG, "Facebook Login Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "Facebook Login Error" + error.toString());
                error.printStackTrace();
            }
        });*/

    }

    private void createAccount() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
