package com.platzi.platzigram.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.platzi.platzigram.R;
import com.platzi.platzigram.login.view.CreateAccountActivity;
import com.platzi.platzigram.login.view.LoginActivity;
import com.platzi.platzigram.post.view.HomeFragment;
import com.platzi.platzigram.view.fragment.ProfileFragment;
import com.platzi.platzigram.view.fragment.SearchFragment;

public class ContainerActivity extends AppCompatActivity {


    private static final String TAG = "ContainerActivity";
    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    SearchFragment searchFragment = new SearchFragment();

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        firebaseInitialize();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottombar);

        changeFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.home){
                    changeFragment(homeFragment);
                }else if (item.getItemId() == R.id.profile) {
                    changeFragment(profileFragment);
                }else if (item.getItemId() == R.id.search){
                    changeFragment(searchFragment);
                }

                return false;
            }

        });

    }

    private void changeFragment(Fragment fragment){

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

    }

    private void firebaseInitialize(){
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.mSignOut:
                firebaseAuth.signOut();
                if (LoginManager.getInstance() != null){
                    LoginManager.getInstance().logOut();//salir de la sesion de facebook
                }
                Toast.makeText(this, "Se cerró la sesión", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ContainerActivity.this, LoginActivity.class);
                startActivity(i);

                break;
            case  R.id.mAbout:
                Toast.makeText(this, "Platzigram by Platzi", Toast.LENGTH_SHORT).show();
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
