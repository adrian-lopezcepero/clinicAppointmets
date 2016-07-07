package com.adrian.android.clinicappointments.addappointment;

import com.adrian.android.clinicappointments.addappointment.events.AddAppointmentEvent;
import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 7/07/16.
 */
public interface AddAppointmentPresenter {
    void onCreate();

    void onDestroy();

    void onEventMainThread(AddAppointmentEvent event);

    void onAddAppointmentSuccess(Appointment appointment);

    void onAddAppointmentError(String error);

    void onModifiedAppointmentSuccess(Appointment appointment);

    void onModifiedAppointmentError(String error);

    void addAppointment(Appointment appointment);

    void modifyAppointment(Appointment appointment);
}
