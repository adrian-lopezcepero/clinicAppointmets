package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.entities.Appointment;

import java.util.Date;

/**
 * Created by adrian on 6/07/16.
 */
public class AppointmentsInteractorImpl implements AppointmentsInteractor {
    AppointmentsRepository repository;

    public AppointmentsInteractorImpl(AppointmentsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribeToCheckForData(Long initDate) {
        repository.subscribeToCheckForData(initDate);
    }

    @Override
    public void subscribe(Date date) {
        repository.subscribeToAppointmentsEvents(date);
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
