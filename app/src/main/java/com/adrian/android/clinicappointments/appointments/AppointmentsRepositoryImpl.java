package com.adrian.android.clinicappointments.appointments;

import com.adrian.android.clinicappointments.appointments.events.AppointmentEvent;
import com.adrian.android.clinicappointments.domain.FirebaseAPI;
import com.adrian.android.clinicappointments.domain.FirebaseActionListenerCallback;
import com.adrian.android.clinicappointments.domain.FirebaseEventListener;
import com.adrian.android.clinicappointments.domain.FirebaseFilterListenerCallback;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.adrian.android.clinicappointments.libs.base.EventBus;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by adrian on 6/07/16.
 */
public class AppointmentsRepositoryImpl implements AppointmentsRepository {

    FirebaseAPI firebaseAPI;
    EventBus eventBus;

    public AppointmentsRepositoryImpl(FirebaseAPI firebaseAPI, EventBus eventBus) {
        this.firebaseAPI = firebaseAPI;
        this.eventBus = eventBus;
    }

    @Override
    public void signOff() {
        firebaseAPI.logout();
    }

    @Override
    public String getCurrentUserEmail() {
        return firebaseAPI.getAuthEmail();
    }

    @Override
    public void removeAppointment(final Appointment appointment) {
        firebaseAPI.remove(appointment, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                post(AppointmentEvent.ONAPPOINTMENT_REMOVED, appointment);
            }

            @Override
            public void onError(FirebaseError error) {
                post(AppointmentEvent.ONAPPOINTMENT_REMOVED, error.getMessage());
            }
        });
    }

    @Override
    public void subscribeToCheckForData() {
        firebaseAPI.checkForData(new FirebaseFilterListenerCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Appointment appointment = child.getValue
                            (Appointment.class);
                    appointment.setId(child.getKey());
                    post(AppointmentEvent.ON_DATE_CHANGED,
                            appointment);
                }
            }

            @Override
            public void onError(FirebaseError error) {
                if (error != null) {
                    post(AppointmentEvent.READ_EVENT, error.getMessage());
                }
            }
        });
    }

    @Override
    public void unsubscribeToCheckForData() {
        firebaseAPI.destroyCheckForDataListener();
    }

    public Date getEnDate(Date initDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(initDate);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    @Override
    public void subscribeToAppointmentsEvents(final Date date) {
        final Date endDate = getEnDate(date);
        firebaseAPI.subscribe(new FirebaseEventListener() {
                                  @Override
                                  public void onChildAdded(DataSnapshot dataSnapshot) {
                                      Appointment appointment = dataSnapshot.getValue
                                              (Appointment.class);
                                      appointment.setId(dataSnapshot.getKey());
                                      if (appointment.getInitDate().after(date) &&
                                              appointment.getInitDate().before(endDate)) {
                                          post(AppointmentEvent.ONAPPOINTMENT_ADDED,
                                                  appointment);
                                      }
                                  }

                                  @Override
                                  public void onChildRemoved(DataSnapshot dataSnapshot) {
                                      Appointment appointment = dataSnapshot.getValue
                                              (Appointment.class);
                                      appointment.setId(dataSnapshot.getKey());
                                      if (appointment.getInitDate().after(date) &&
                                              appointment.getInitDate().before(endDate)) {
                                          post(AppointmentEvent.ONAPPOINTMENT_REMOVED,
                                                  appointment);
                                      }
                                  }

                                  @Override
                                  public void onCancelled(FirebaseError error) {
                                      post(AppointmentEvent.READ_EVENT, error.getMessage());
                                  }

                                  @Override
                                  public void onChildChanged(DataSnapshot dataSnapshot) {
                                      Appointment appointment = dataSnapshot.getValue
                                              (Appointment.class);
                                      appointment.setId(dataSnapshot.getKey());
                                      if (appointment.getInitDate().after(date) &&
                                              appointment.getInitDate().before(endDate)) {
                                          post(AppointmentEvent.ONAPPOINTMENT_CHANGED,
                                                  appointment);
                                      }
                                  }

                              }

        );

    }

    @Override
    public void unsubscribeToAppointmentsEvents() {
        firebaseAPI.unsubscribe();
    }

    @Override
    public void destroyListener() {
        firebaseAPI.destroyListener();
    }

    private void post(int type, Appointment appointment) {
        post(type, appointment, null);
    }

    private void post(int type, String error) {
        post(type, null, error);
    }

    private void post(int type, Appointment appointment, String error) {
        AppointmentEvent event = new AppointmentEvent();
        event.setEventType(type);
        event.setError(error);
        event.setAppointment(appointment);
        eventBus.post(event);
    }
}
