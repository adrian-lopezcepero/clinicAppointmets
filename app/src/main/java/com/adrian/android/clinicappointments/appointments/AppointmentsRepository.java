package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentsRepository {
    void signOff();

    String getCurrentUserEmail();

    void removeAppointment(Appointment appointment);

    void subscribeToAppointmentsEvents();

    void unsubscribeToAppointmentsEvents();

    void destroyListener();

}
