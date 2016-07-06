package com.adrian.android.clinicappointments.appointments;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentInteractor {
    void subscribe();

    void unsubscribe();

    void destroyListener();

    void removeAppointment(int AppointmentId);
}
