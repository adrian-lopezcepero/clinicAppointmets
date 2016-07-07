package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentsInteractor {
    void subscribe();

    void unsubscribe();

    void destroyListener();

    void removeAppointment(Appointment appointment);
}
