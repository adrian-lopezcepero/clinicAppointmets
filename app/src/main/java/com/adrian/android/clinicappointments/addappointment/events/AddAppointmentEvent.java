package com.adrian.android.clinicappointments.addappointment.events;

import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 7/07/16.
 */
public class AddAppointmentEvent {
    public final static int ON_ADDED_SUCCESS = 0;
    public final static int ON_ADDED_ERROR = 1;
    public final static int ON_MODIFIED_SUCCESS = 2;
    public final static int ON_MODIFIED_ERROR = 3;

    private int type;
    private Appointment appointment;
    private String error;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
