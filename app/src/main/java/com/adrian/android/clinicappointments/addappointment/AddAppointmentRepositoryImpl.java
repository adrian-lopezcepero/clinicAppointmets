package com.adrian.android.clinicappointments.addappointment;

import com.adrian.android.clinicappointments.addappointment.events.AddAppointmentEvent;
import com.adrian.android.clinicappointments.domain.FirebaseAPI;
import com.adrian.android.clinicappointments.domain.FirebaseActionListenerCallback;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.adrian.android.clinicappointments.libs.base.EventBus;
import com.firebase.client.FirebaseError;

/**
 * Created by adrian on 7/07/16.
 */
public class AddAppointmentRepositoryImpl implements AddAppointmentRepository {

    EventBus eventBus;
    FirebaseAPI firebaseAPI;

    public AddAppointmentRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void addAppointment(final Appointment appointment) {
        firebaseAPI.addAppointment(appointment, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                post(AddAppointmentEvent.ON_ADDED_SUCCESS, appointment);
            }

            @Override
            public void onError(FirebaseError error) {
                post(AddAppointmentEvent.ON_ADDED_ERROR, error.getMessage());
            }
        });
    }

    @Override
    public void modifyAppointment(final Appointment appointment) {
        firebaseAPI.modifyAppointment(appointment, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                post(AddAppointmentEvent.ON_MODIFIED_SUCCESS, appointment);
            }

            @Override
            public void onError(FirebaseError error) {
                post(AddAppointmentEvent.ON_MODIFIED_ERROR, error.getMessage());
            }
        });
    }

    private void post(int type, Appointment appointment) {
        post(type, appointment, null);
    }

    private void post(int type, String error) {
        post(type, null, error);
    }

    private void post(int type, Appointment appointment, String error) {
        AddAppointmentEvent event = new AddAppointmentEvent();
        event.setType(type);
        event.setAppointment(appointment);
        event.setError(error);
        eventBus.post(event);
    }
}
