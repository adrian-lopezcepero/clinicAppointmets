package com.adrian.android.clinicappointments.login.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adrian.android.clinicappointments.R;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void enableInputs() {

    }

    @Override
    public void hideInputs() {

    }

    @Override
    public void handleSignUp() {

    }

    @Override
    public void handleSignIn() {

    }

    @Override
    public void newUserSuccess() {

    }

    @Override
    public void navigateToAppointmentsScreen() {

    }

    @Override
    public void setUserEmail(String email) {

    }

    @Override
    public void loginError(String error) {

    }

    @Override
    public void newUserError(String error) {

    }
}
