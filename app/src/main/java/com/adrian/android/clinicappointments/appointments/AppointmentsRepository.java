package com.adrian.android.clinicappointments.appointments;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentsRepository {
    void signOff();

    String getCurrentUserEmail();

    void removeAppointment(int AppointmentId);

    void subscribeToAppointmentsEvents();

    void unsubscribeToAppointmentsEvents();

    void destroyListener();

}
