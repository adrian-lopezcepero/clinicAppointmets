package com.adrian.android.clinicappointments.addappointment.ui;

import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 7/07/16.
 */
public interface AddAppointmentView {
    void hideProgress();

    void showProgress();

    void enableInputs();

    void disableImputs();

    void onAddAppointment();

    void onAddAddressToMap();

    void showError(String error);

    void addAppointment(Appointment appointment);

    void modifyAppointment(Appointment appointment);
}
