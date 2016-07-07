package com.adrian.android.clinicappointments.addappointment;

import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 7/07/16.
 */
public interface AddAppointmentInteractor {

    void addAppointment(Appointment appointment);

    void modifyAppointment(Appointment appointment);
}
