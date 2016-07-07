package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.appointments.events.AppointmentEvent;
import com.adrian.android.clinicappointments.appointments.ui.AppointmentsView;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.adrian.android.clinicappointments.libs.base.EventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by adrian on 6/07/16.
 */
public class AppointmentsPresenterImpl implements AppointmentsPresenter {
    private final static String EMPTY_LIST = "Empty list";
    EventBus eventBus;
    AppointmentsSessionInteractor sessionInteractor;
    AppointmentsInteractor appointmentsInteractor;
    AppointmentsView view;

    public AppointmentsPresenterImpl(EventBus eventBus, AppointmentsSessionInteractor
            sessionInteractor, AppointmentsInteractor appointmentsInteractor, AppointmentsView
                                             view) {
        this.eventBus = eventBus;
        this.sessionInteractor = sessionInteractor;
        this.appointmentsInteractor = appointmentsInteractor;
        this.view = view;
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onCreate() {
        appointmentsInteractor.subscribe();
    }

    @Override
    public void onDestroy() {
        this.view = null;
        eventBus.unregister(this);
    }

    @Override
    public void subscribe() {
        if (view != null) {
            view.hideList();
            view.showProgress();
        }
        appointmentsInteractor.subscribe();
    }

    @Override
    public void unsubscribe() {
        appointmentsInteractor.unsubscribe();
    }

    @Override
    @Subscribe
    public void onEventMainThread(AppointmentEvent event) {
        if (this.view != null) {
            view.hideProgress();
            view.showList();

            String error = event.getError();
            if (error != null) {
                if (error.isEmpty()) {
                    view.onAppointmentError(EMPTY_LIST);
                } else
                    view.onAppointmentError(error);
            } else {
                switch (event.getEventType()) {
                    case AppointmentEvent.ONAPPOINTMENT_ADDED:
                        view.onAppointmentAdded(event.getAppointment());
                        break;
                    case AppointmentEvent.ONAPPOINTMENT_CHANGED:
                        view.onAppointmentChanged(event.getAppointment());
                        break;
                    case AppointmentEvent.ONAPPOINTMENT_REMOVED:
                        view.onAppointmentRemoved(event.getAppointment());
                        break;
                    case AppointmentEvent.READ_EVENT:
                        view.onAppointmentError(event.getError());
                        break;
                }
            }
        }

    }

    @Override
    public void removeAppointment(Appointment appointment) {
        appointmentsInteractor.removeAppointment(appointment);
    }

    @Override
    public void signOff() {
        sessionInteractor.signOff();
    }
}
