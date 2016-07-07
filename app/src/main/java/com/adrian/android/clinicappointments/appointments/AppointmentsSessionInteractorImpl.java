package com.adrian.android.clinicappointments.appointments;

/**
 * Created by adrian on 6/07/16.
 */
public class AppointmentsSessionInteractorImpl implements AppointmentsSessionInteractor {

    AppointmentsRepository repository;

    public AppointmentsSessionInteractorImpl(AppointmentsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void signOff() {
        repository.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return repository.getCurrentUserEmail();
    }
}
