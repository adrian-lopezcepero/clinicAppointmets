package com.adrian.android.clinicappointments.addappointment;

import com.adrian.android.clinicappointments.entities.Appointment;

/**
 * Created by adrian on 7/07/16.
 */
public class AddAppointmentInteractorImpl implements AddAppointmentInteractor {

    AddAppointmentRepository addAppointmentRepository;

    public AddAppointmentInteractorImpl(AddAppointmentRepository addAppointmentRepository) {
        this.addAppointmentRepository = addAppointmentRepository;
    }

    @Override
    public void addAppointment(Appointment appointment) {
        addAppointmentRepository.addAppointment(appointment);
    }

    @Override
    public void modifyAppointment(Appointment appointment) {
        addAppointmentRepository.modifyAppointment(appointment);
    }
}
