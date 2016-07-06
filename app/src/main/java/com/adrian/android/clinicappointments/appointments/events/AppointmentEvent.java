package com.adrian.android.clinicappointments.appointments.events;

import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 6/07/16.
 */
public class AppointmentEvent {
    public final static int ONAPPOINTMENT_ADDED = 0;
    public final static int ONAPPOINTMENT_REMOVED = 1;
    public final static int ONAPPOINTMENT_CHANGED = 2;

    private int eventType;
    private Appointment appointment;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
