package com.adrian.android.clinicappointments.addappointment.di;

import com.adrian.android.clinicappointments.ClinicAppointmentsAppModule;
import com.adrian.android.clinicappointments.addappointment.ui.AddAppointmentActivity;
import com.adrian.android.clinicappointments.domain.di.DomainModule;
import com.adrian.android.clinicappointments.libs.di.LibsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by adrian on 7/07/16.
 */
@Singleton
@Component(modules = {AddAppointmentModule.class, DomainModule.class, LibsModule.class,
        ClinicAppointmentsAppModule.class})
public interface AddAppointmentComponent {

    void inject(AddAppointmentActivity addAppointmentActivity);
}
