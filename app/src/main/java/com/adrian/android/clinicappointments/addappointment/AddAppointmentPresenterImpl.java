package com.adrian.android.clinicappointments.addappointment;

import com.adrian.android.clinicappointments.addappointment.events.AddAppointmentEvent;
import com.adrian.android.clinicappointments.addappointment.ui.AddAppointmentView;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.adrian.android.clinicappointments.libs.base.EventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by adrian on 7/07/16.
 */
public class AddAppointmentPresenterImpl implements AddAppointmentPresenter {

    EventBus eventBus;
    AddAppointmentInteractor addAppointmentInteractor;
    AddAppointmentView addAppointmentView;

    public AddAppointmentPresenterImpl(EventBus eventBus, AddAppointmentInteractor
            addAppointmentInteractor, AddAppointmentView appointmentView) {
        this.eventBus = eventBus;
        this.addAppointmentInteractor = addAppointmentInteractor;
        this.addAppointmentView = appointmentView;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.addAppointmentView = null;
        eventBus.unregister(this);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddAppointmentEvent event) {
        if (this.addAppointmentView != null) {

            Appointment appointment = event.getAppointment();
            switch (event.getType()) {
                case AddAppointmentEvent.ON_ADDED_SUCCESS:
                    onAddAppointmentSuccess(appointment);
                    break;
                case AddAppointmentEvent.ON_ADDED_ERROR:
                    onAddAppointmentError(event.getError());
                    break;
                case AddAppointmentEvent.ON_MODIFIED_SUCCESS:
                    onModifiedAppointmentSuccess(appointment);
                    break;
                case AddAppointmentEvent.ON_MODIFIED_ERROR:
                    onModifiedAppointmentError(event.getError());
                    break;
            }
        }
    }

    @Override
    public void onAddAppointmentError(String error) {
        addAppointmentView.showError(error);
    }

    @Override
    public void onModifiedAppointmentError(String error) {
        addAppointmentView.showError(error);
    }

    @Override
    public void modifyAppointment(Appointment appointment) {
        addAppointmentInteractor.modifyAppointment(appointment);
    }

    @Override
    public void onAddAppointmentSuccess(Appointment appointment) {
        addAppointmentView.addAppointment(appointment);
    }

    @Override
    public void onModifiedAppointmentSuccess(Appointment appointment) {
        addAppointmentView.modifyAppointment(appointment);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        addAppointmentInteractor.addAppointment(appointment);
    }
}
