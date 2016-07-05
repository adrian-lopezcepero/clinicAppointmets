package com.adrian.android.clinicappointments.login.di;

import com.adrian.android.clinicappointments.ClinicAppointmentsAppModule;
import com.adrian.android.clinicappointments.domain.di.DomainModule;
import com.adrian.android.clinicappointments.libs.di.LibsModule;
import com.adrian.android.clinicappointments.login.ui.LoginActivity;
import com.adrian.android.clinicappointments.signup.ui.SignupActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by adrian on 5/07/16.
 */
@Singleton
@Component(modules = {LoginModule.class, DomainModule.class, LibsModule.class,
        ClinicAppointmentsAppModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);

    void inject(SignupActivity signupActivity);
}
