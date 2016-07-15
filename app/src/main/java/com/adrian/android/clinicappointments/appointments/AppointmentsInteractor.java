package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.entities.Appointment;

import java.util.Calendar;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentsInteractor {
    void subscribe(Calendar date);

    void unsubscribe();

    void subscribeToCheckForData(Long initDate);

    void destroyListener();

    void removeAppointment(Appointment appointment);
}
