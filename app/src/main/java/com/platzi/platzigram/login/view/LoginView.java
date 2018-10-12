package com.platzi.platzigram.login.view;

public interface LoginView {

    void enableInputs();
    void desableInputs();

    void showProgressBar();
    void hideProgressBar();

    void loginError(String error);

    void goToURL();

    void goCreateAccount();
    void goHome();

}
