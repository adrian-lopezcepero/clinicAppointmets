package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.entities.Appointment;

import java.util.Date;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentsInteractor {
    void subscribe(Date date);

    void unsubscribe();

    void subscribeToCheckForData();

    void destroyListener();

    void removeAppointment(Appointment appointment);
}
