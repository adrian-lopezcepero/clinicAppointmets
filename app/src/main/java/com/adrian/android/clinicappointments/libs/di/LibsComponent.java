package com.adrian.android.clinicappointments.libs.di;

import com.adrian.android.clinicappointments.ClinicAppointmentsAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by adrian on 5/07/16.
 */
@Singleton
@Component(modules = {LibsModule.class, ClinicAppointmentsAppModule.class})
public interface LibsComponent {
}
