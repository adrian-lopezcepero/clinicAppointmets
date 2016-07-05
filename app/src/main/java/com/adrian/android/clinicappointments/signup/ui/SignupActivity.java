package com.adrian.android.clinicappointments.signup.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adrian.android.clinicappointments.ClinicAppointmentsApp;
import com.adrian.android.clinicappointments.R;
import com.adrian.android.clinicappointments.login.LoginPresenter;
import com.adrian.android.clinicappointments.login.ui.LoginView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.editTxtEmail)
    EditText editTxtEmail;
    @Bind(R.id.editTxtPassword)
    EditText editTxtPassword;
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
        setContentView(R.layout.activity_signup);

        setTitle(getString(R.string.signup_title));
        ButterKnife.bind(this);
        app = (ClinicAppointmentsApp) getApplication();
        setupInjection();
        presenter.onCreate();
    }

    private void setupInjection() {
        app.getLoginComponent(this).inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onCreate();
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
    public void handleSignUp() {

    }

    @Override
    public void handleSignIn() {
        throw new UnsupportedOperationException("Not supported operation on SignUpActivity");

    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.signup_notice_message_useradded, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void navigateToAppointmentsScreen() {
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
        throw new UnsupportedOperationException("Not supported operation on SignUpActivity");
    }

    @Override
    public void newUserError(String error) {
        editTxtPassword.setText("");
        String msgError = String.format(getString(R.string.signup_error_message_signup), error);
        editTxtPassword.setError(msgError);
    }

    private void setInputs(boolean enabled) {
        btnSignup.setEnabled(enabled);
        editTxtEmail.setEnabled(enabled);
        editTxtPassword.setEnabled(enabled);
    }
}
