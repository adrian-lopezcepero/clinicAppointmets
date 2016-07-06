package com.adrian.android.clinicappointments.login;

import com.adrian.android.clinicappointments.libs.base.EventBus;
import com.adrian.android.clinicappointments.login.events.LoginEvent;
import com.adrian.android.clinicappointments.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by adrian on 5/07/16.
 */
public class LoginPresenterImpl implements LoginPresenter {

    LoginInteractor loginInteractor;
    SignUpInteractor signUpInteractor;
    EventBus eventBus;
    LoginView loginView;

    public LoginPresenterImpl(LoginInteractor loginInteractor, SignUpInteractor signUpInteractor,
                              EventBus eventBus, LoginView loginView) {
        this.loginInteractor = loginInteractor;
        this.signUpInteractor = signUpInteractor;
        this.eventBus = eventBus;
        this.loginView = loginView;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        this.loginView = null;
    }


    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        if (event != null) {
            switch (event.getEventType()) {
                case LoginEvent.ONSIGNIN_SUCCESS:
                    onSignInSuccess(event.getLoggedUserEmail());
                    break;
                case LoginEvent.ONSIGNIN_ERROR:
                    onSignInError(event.getErrorMessage());
                    break;
                case LoginEvent.ONSIGNUP_ERROR:
                    onSignUpError(event.getErrorMessage());
                    break;
                case LoginEvent.ONSIGNUP_SUCCESS:
                    onSignUpSuccess();
                    break;
            }
        }
    }


    private void onSignUpSuccess() {
        if (loginView != null) {
            loginView.newUserSuccess();
        }
    }

    private void onSignUpError(String error) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);
        }
    }

    private void onSignInError(String error) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }

    private void onSignInSuccess(String email) {
        if (loginView != null) {
            loginView.setUserEmail(email);
            loginView.navigateToAppointmentsScreen();
        }
    }

    @Override
    public void login(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.execute(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        signUpInteractor.execute(email, password);
    }
}
