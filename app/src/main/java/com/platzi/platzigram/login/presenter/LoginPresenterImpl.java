package com.platzi.platzigram.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.platzi.platzigram.login.interactor.LoginInteractor;
import com.platzi.platzigram.login.interactor.LoginInteractorImpl;
import com.platzi.platzigram.login.view.LoginActivity;
import com.platzi.platzigram.login.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;//así porque se llama desde la vista.
        interactor = new LoginInteractorImpl(this);//llamas al interactor
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        loginView.desableInputs();
        loginView.showProgressBar();
        interactor.signIn(username, password, activity, firebaseAuth);
    }

    @Override
    public void loginSuccess() {
        loginView.goHome();
        loginView.hideProgressBar();
    }

    @Override
    public void loginError(String error) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(error);
    }
}
