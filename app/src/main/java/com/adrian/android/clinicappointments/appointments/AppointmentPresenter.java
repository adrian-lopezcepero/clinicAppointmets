package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.appointments.events.AppointmentEvent;
import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentPresenter {
    void onPause();

    void onResume();

    void onCreate();

    void onDestroy();

    void onEventMainThread(AppointmentEvent event);

    void removeAppointment(Appointment appointment);

    void signOff();
}
