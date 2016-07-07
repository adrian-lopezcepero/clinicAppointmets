package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.appointments.events.AppointmentEvent;
import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentsPresenter {
    void onPause();

    void onResume();

    void onCreate();

    void onDestroy();

    void subscribe();

    void unsubscribe();

    void onEventMainThread(AppointmentEvent event);

    void removeAppointment(Appointment appointment);

    void signOff();
}
