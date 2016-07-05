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
    private Firebase firebase;
    private ChildEventListener appointmentEventListener;

    public FirebaseAPI(Firebase firebase) {
        this.firebase = firebase;
    }

    /**
     * Check if there is new appointments in Firebase.
     *
     * @param listener Interface to wrap the events of Firebase.
     */
    public void checkForData(final FirebaseActionListenerCallback listener) {
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    listener.onSuccess();
                } else
                    listener.onError(null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onError(firebaseError);
            }
        });
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
            firebase.addChildEventListener(appointmentEventListener);
        }
    }

    /**
     * Revome the appointmentEventListener from for the Firebase object.
     */
    public void unsibscribe() {
        firebase.removeEventListener(appointmentEventListener);
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
     * Send the updated data of the appointment to Firebase.
     *
     * @param appointment
     */
    public void update(Appointment appointment) {
        Firebase reference = this.firebase.child(appointment.getId());
        reference.setValue(appointment);
    }

    /**
     * Remove the @param appointment of Firebase.
     *
     * @param appointment
     * @param listener    FirebaseActionListenerCallback
     */
    public void remove(Appointment appointment, FirebaseActionListenerCallback
            listener) {
        firebase.child(appointment.getId()).removeValue();
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

    /**
     * Perform logout on Firebase.
     */
    public void logout() {
        firebase.unauth();
    }

}
