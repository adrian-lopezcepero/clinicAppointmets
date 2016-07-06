package com.adrian.android.clinicappointments.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adrian.android.clinicappointments.ClinicAppointmentsApp;
import com.adrian.android.clinicappointments.R;
import com.adrian.android.clinicappointments.appointments.ui.AppointmentsActivity;
import com.adrian.android.clinicappointments.login.LoginPresenter;
import com.adrian.android.clinicappointments.signup.ui.SignupActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.editTxtEmail)
    EditText editTxtEmail;
    @Bind(R.id.editTxtPassword)
    EditText editTxtPassword;
    @Bind(R.id.btnSignin)
    Button btnSignin;
    @Bind(R.id.btnSignup)
    Button btnSignup;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;

    @Inject
    LoginPresenter presenter;

    @Inject
    SharedPreferences sharedPreferences;

    private ClinicAppointmentsApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        app = (ClinicAppointmentsApp) getApplication();
        setupInjection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
//        Try to perform login because we can have a previous login
        presenter.login(null, null);
    }

    private void setupInjection() {
        app.getLoginComponent(this).inject(this);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }


    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    @OnClick(R.id.btnSignup)
    public void handleSignUp() {
        startActivity(new Intent(this, SignupActivity.class));
    }

    @Override
    @OnClick(R.id.btnSignin)
    public void handleSignIn() {
        presenter.login(editTxtEmail.getText().toString(), editTxtPassword.getText().toString());
    }

    @Override
    public void newUserSuccess() {
        throw new UnsupportedOperationException("Not supported operation on LoginActivity");
    }

    @Override
    public void navigateToAppointmentsScreen() {
        Intent intent = new Intent(this, AppointmentsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void setUserEmail(String email) {
        if (email != null) {
            String key = ClinicAppointmentsApp.EMAIL_KEY;
            sharedPreferences.edit().putString(key, email).commit();
        }
    }

    @Override
    public void loginError(String error) {
        editTxtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        editTxtPassword.setError(msgError);
    }

    @Override
    public void newUserError(String error) {
        throw new UnsupportedOperationException("Not supported operation on LoginActivity");
    }

    private void setInputs(boolean enabled) {
        btnSignin.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
        editTxtEmail.setEnabled(enabled);
        editTxtPassword.setEnabled(enabled);
    }
}
