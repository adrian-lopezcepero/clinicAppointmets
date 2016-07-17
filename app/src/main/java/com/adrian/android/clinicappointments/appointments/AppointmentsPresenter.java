package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.appointments.events.AppointmentEvent;
import com.adrian.android.clinicappointments.entities.Appointment;

import java.util.Calendar;

/**
 * Created by adrian on 6/07/16.
 */
public interface AppointmentsPresenter {
    void onPause();

    void onResume();

    void onCreate(Calendar date);

    void onDestroy();

    void subscribe(Calendar date);

    void unsubscribe();

    void subsribeToCeckForData(Long initDate);

    void onEventMainThread(AppointmentEvent event);

    void removeAppointment(Appointment appointment);

    void signOff();
}
