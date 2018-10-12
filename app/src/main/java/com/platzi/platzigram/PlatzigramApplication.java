package com.platzi.platzigram;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PlatzigramApplication extends Application {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = "PlatzigramApplication" ;
    private FirebaseStorage firebaseStorage;

    @Override
    public void onCreate() {
        super.onCreate();

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

        firebaseStorage = FirebaseStorage.getInstance();
    }

    public StorageReference getStorageReference(){
        return firebaseStorage.getReference();
    }
}
