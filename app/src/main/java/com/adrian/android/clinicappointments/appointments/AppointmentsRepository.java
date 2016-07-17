package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.entities.Appointment;

import java.util.Calendar;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentsRepository {
    void signOff();

    String getCurrentUserEmail();

    void removeAppointment(Appointment appointment);

    void subscribeToAppointmentsEvents(Calendar date);

    void unsubscribeToAppointmentsEvents();

    void subscribeToCheckForData(Long initDate);

    void unsubscribeToCheckForData();

    void destroyListener();

}
