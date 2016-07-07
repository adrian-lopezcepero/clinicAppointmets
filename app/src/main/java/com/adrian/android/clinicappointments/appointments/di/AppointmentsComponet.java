package com.adrian.android.clinicappointments.appointments.di;

import com.adrian.android.clinicappointments.ClinicAppointmentsAppModule;
import com.adrian.android.clinicappointments.appointments.ui.AppointmentsActivity;
import com.adrian.android.clinicappointments.domain.di.DomainModule;
import com.adrian.android.clinicappointments.libs.di.LibsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by adrian on 6/07/16.
 */
@Singleton
@Component(modules = {AppointmentsModule.class, DomainModule.class, LibsModule.class,
        ClinicAppointmentsAppModule.class})
public interface AppointmentsComponet {

    void inject(AppointmentsActivity appointmentsActivity);
}
