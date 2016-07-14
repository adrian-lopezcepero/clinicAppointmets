package com.adrian.android.clinicappointments.domain;

import com.adrian.android.clinicappointments.entities.Appointment;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by adrian on 5/07/16.
 * <p/>
 * Firebase wrapper.
 * This class wraps all the methods neccesary to send and get all the needed data between
 * Firebase and the app.
 */
public class FirebaseAPI {
    public final static String APPOINTMETNS_PATH = "appointments";
    private Firebase firebase;
    private ChildEventListener appointmentEventListener;
    private ValueEventListener valueEventListener;
    private Util util;

    public FirebaseAPI(Firebase firebase, Util util) {
        this.util = util;
        this.firebase = firebase;
    }


    /**
     * Check if Firebase return an Appointment at Least.
     *
     * @param listener Interface to wrap the events of Firebase.
     */
    public void checkForData(Long initDate, final FirebaseFilterListenerCallback listener) {
        Long endDate = initDate;
        if (valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() > 0) {
                        listener.onSuccess(dataSnapshot);
                    } else
                        listener.onError(null);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    listener.onError(firebaseError);
                }
            };
        }
//        getAppointmentsReference().addValueEventListener(valueEventListener);
        endDate = util.dateToTimeInMillis(util.getEndDate(initDate));
        getAppointmentsReference().orderByChild("initDate").startAt(initDate).endAt(endDate)
                .addValueEventListener
                        (valueEventListener);
    }


    /**
     * Assign the wrapper event to Firebase object to detect if there is an action in the
     * appointments.
     *
     * @param listener Interface to wrap the events of Firebase.
     */
    public void subscribe(final FirebaseEventListener listener) {
        if (appointmentEventListener == null) {
            appointmentEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    listener.onChildAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    listener.onChildChanged(dataSnapshot);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    listener.onChildRemoved(dataSnapshot);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    listener.onCancelled(firebaseError);
                }
            };
            getAppointmentsReference().addChildEventListener
                    (appointmentEventListener);
        }
    }

    /**
     * Revome the appointmentEventListener from for the Firebase object.
     */
    public void unsubscribe() {
        getAppointmentsReference().removeEventListener(appointmentEventListener);
    }

    /**
     * Get the default key of the Firebase API.
     *
     * @return
     */
    public String create() {
        return firebase.push().getKey();
    }

    /**
     * Remove the @param appointment of Firebase.
     *
     * @param appointment
     * @param listener    FirebaseActionListenerCallback
     */
    public void remove(Appointment appointment, FirebaseActionListenerCallback
            listener) {
        firebase.child(APPOINTMETNS_PATH).child(appointment.getId()).removeValue();
        listener.onSuccess();
    }

    /**
     * Return the email of the current Authenticated user.
     *
     * @return
     */
    public String getAuthEmail() {
        String email = null;
        if (firebase.getAuth() != null) {
            Map<String, Object> providerData = firebase.getAuth().getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    /**
     * Create a new user on Firebase.
     *
     * @param email
     * @param password
     * @param listener FirebaseActionListenerCallback
     */
    public void signUp(String email, String password, final FirebaseActionListenerCallback
            listener) {
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>
                () {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                listener.onSuccess();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                listener.onError(firebaseError);
            }
        });
    }

    /**
     * Try to perform login with password and email. The response is wrapped with listener
     * .onSuccess or listener.onError.
     *
     * @param email
     * @param password
     * @param listener FirebaseActionListenerCallback
     */
    public void login(String email, String password, final FirebaseActionListenerCallback
            listener) {
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listener.onSuccess();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listener.onError(firebaseError);
            }
        });
    }

    /**
     * Check if the session is active.
     *
     * @param listener
     */
    public void checkForSession(FirebaseActionListenerCallback listener) {
        if (firebase.getAuth() != null) {
            listener.onSuccess();
        } else
            listener.onError(null);
    }

    public Firebase getOneAppointmentReference(String appointmentId) {
        Firebase appointmentReference = null;
        if (appointmentId != null) {
            appointmentReference = firebase.getRoot().child(APPOINTMETNS_PATH).child(appointmentId);
        }
        return appointmentReference;
    }

    public Firebase getAppointmentsReference() {
        Firebase appointmentReference = null;
        appointmentReference = firebase.getRoot().child(APPOINTMETNS_PATH);
        return appointmentReference;
    }

    /**
     * Add the appointment of Firebase.
     *
     * @param appointment
     * @param listener    FirebaseActionListenerCallback
     */
    public void addAppointment(Appointment appointment, FirebaseActionListenerCallback
            listener) {
        firebase = getAppointmentsReference().push();
        firebase.setValue(appointment);
        appointment.setId(firebase.getKey());
        listener.onSuccess();
    }

    /**
     * Modify the appointment of Firebase.
     *
     * @param appointment
     * @param listener    FirebaseActionListenerCallback
     */
    public void modifyAppointment(Appointment appointment, FirebaseActionListenerCallback
            listener) {
        Firebase ref = getAppointmentsReference().child(appointment.getId());
        ref.setValue(appointment);
        listener.onSuccess();
    }

    /**
     * Perform logout on Firebase.
     */
    public void logout() {
        firebase.unauth();
    }

    public void destroyListener() {
        firebase.removeEventListener(appointmentEventListener);
    }

    public void destroyCheckForDataListener() {
        firebase.removeEventListener(valueEventListener);
    }

    public void unsubscribeToCheckForData() {
        getAppointmentsReference().removeEventListener(valueEventListener);

    }
}
