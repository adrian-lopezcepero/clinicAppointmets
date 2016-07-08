package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.appointments.events.AppointmentEvent;
import com.adrian.android.clinicappointments.entities.Appointment;

import java.util.Date;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentsPresenter {
    void onPause();

    void onResume();

    void onCreate(Date date);

    void onDestroy();

    void subscribe(Date date);

    void unsubscribe();

    void subsribeToCeckForData();

    void onEventMainThread(AppointmentEvent event);

    void removeAppointment(Appointment appointment);

    void signOff();
}
