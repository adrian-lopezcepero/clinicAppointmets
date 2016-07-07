package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 6/07/16.
 */
public class AppointmentsInteractorImpl implements AppointmentsInteractor {
    AppointmentsRepository repository;

    public AppointmentsInteractorImpl(AppointmentsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        repository.subscribeToAppointmentsEvents();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribeToAppointmentsEvents();
    }

    @Override
    public void destroyListener() {
        repository.destroyListener();
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        repository.removeAppointment(appointment);
    }
}
