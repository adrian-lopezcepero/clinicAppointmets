package com.adrian.android.clinicappointments.login.ui;

/**
 * Created by adrian on 5/07/16.
 */
public interface LoginView {

    void hideProgress();

    void showProgress();

    void enableInputs();

    void disableInputs();

    void handleSignUp();

    void handleSignIn();


    void newUserSuccess();

    void navigateToAppointmentsScreen();

    void setUserEmail(String email);

    void loginError(String error);

    void newUserError(String error);


}
