package com.adrian.android.clinicappointments;

import android.app.Application;

import com.adrian.android.clinicappointments.appointments.di.AppointmentsComponet;
import com.adrian.android.clinicappointments.appointments.di.AppointmentsModule;
import com.adrian.android.clinicappointments.appointments.di.DaggerAppointmentsComponet;
import com.adrian.android.clinicappointments.appointments.ui.AppointmentsView;
import com.adrian.android.clinicappointments.appointments.ui.adapters.OnItemClickListener;
import com.adrian.android.clinicappointments.domain.di.DomainModule;
import com.adrian.android.clinicappointments.libs.di.LibsModule;
import com.adrian.android.clinicappointments.login.di.DaggerLoginComponent;
import com.adrian.android.clinicappointments.login.di.LoginComponent;
import com.adrian.android.clinicappointments.login.di.LoginModule;
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
        return DaggerLoginComponent
                .builder()
                .clinicAppointmentsAppModule(clinicAppointmentsAppModule)
                .domainModule(domainModule)
                .libsModule(libsModule)
                .loginModule(new LoginModule(view))
                .build();

    }

    public AppointmentsComponet getAppointmentComponent(AppointmentsView view,
                                                        OnItemClickListener onItemClickListener) {
//        return null;
        return DaggerAppointmentsComponet
                .builder()
                .clinicAppointmentsAppModule(clinicAppointmentsAppModule)
                .domainModule(domainModule)
                .libsModule(libsModule)
                .appointmentsModule(new AppointmentsModule(view, onItemClickListener))
                .build();

    }
}
