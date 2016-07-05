package com.adrian.android.clinicappointments;

import android.app.Application;

import com.adrian.android.clinicappointments.domain.di.DomainModule;
import com.adrian.android.clinicappointments.libs.di.LibsModule;
import com.adrian.android.clinicappointments.login.di.LoginComponent;
import com.adrian.android.clinicappointments.login.ui.LoginView;
import com.firebase.client.Firebase;

/**
 * Created by adrian on 5/07/16.
 */
public class ClinicAppointmentsApp extends Application {
    public final static String EMAIL_KEY = "email";
    private LibsModule libsModule;
    private DomainModule domainModule;
    private ClinicAppointmentsAppModule clinicAppointmentsAppModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
    }

    private void initModules() {
        libsModule = new LibsModule();
        domainModule = new DomainModule();
        clinicAppointmentsAppModule = new ClinicAppointmentsAppModule(this);
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
    }

    public LoginComponent getLoginComponent(LoginView view) {
        return null;
//        return DaggerLoginComponent
//                .builder()
//                .ClinicAppointmentsAppModule(ClinicAppointmentsAppModule)
//                .domainModule(domainModule)
//                .libsModule(libsModule)
//                .loginModule(new LoginModule(view))
//                .build();

    }
}
