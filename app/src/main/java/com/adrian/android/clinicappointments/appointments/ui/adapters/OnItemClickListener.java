package com.adrian.android.clinicappointments.appointments.ui.adapters;

import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 6/07/16.
 */
public interface OnItemClickListener {
    void onDeleteClick(Appointment appointment);

    void onEditClick(Appointment appointment);
}
