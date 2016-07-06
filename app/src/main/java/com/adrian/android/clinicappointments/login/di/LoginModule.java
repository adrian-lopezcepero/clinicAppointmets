package com.adrian.android.clinicappointments.login.di;

import com.adrian.android.clinicappointments.domain.FirebaseAPI;
import com.adrian.android.clinicappointments.libs.base.EventBus;
import com.adrian.android.clinicappointments.login.LoginInteractor;
import com.adrian.android.clinicappointments.login.LoginInteractorImpl;
import com.adrian.android.clinicappointments.login.LoginPresenter;
import com.adrian.android.clinicappointments.login.LoginPresenterImpl;
import com.adrian.android.clinicappointments.login.LoginRepository;
import com.adrian.android.clinicappointments.login.LoginRepositoryImpl;
import com.adrian.android.clinicappointments.login.SignUpInteractor;
import com.adrian.android.clinicappointments.login.SignUpInteractorImpl;
import com.adrian.android.clinicappointments.login.ui.LoginView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adrian on 5/07/16.
 */
@Module
public class LoginModule {
    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesLoginView() {
        return this.view;
    }

    @Provides
    @Singleton
    LoginPresenter providesLoginPresenter(LoginInteractor loginInteractor, SignUpInteractor
            signUpInteractor,
                                          EventBus eventBus, LoginView loginView) {
        return new LoginPresenterImpl(loginInteractor, signUpInteractor, eventBus, loginView);
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository loginRepository) {
        return new LoginInteractorImpl(loginRepository);
    }

    @Provides
    @Singleton
    SignUpInteractor providesSignUpInteractor(LoginRepository loginRepository) {
        return new SignUpInteractorImpl(loginRepository);
    }

    @Provides
    @Singleton
    LoginRepository providesLoginRepository(EventBus eventBus, FirebaseAPI firebaseAPI) {
        return new LoginRepositoryImpl(eventBus, firebaseAPI);
    }
}
