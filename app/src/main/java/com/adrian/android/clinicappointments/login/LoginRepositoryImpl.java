package com.adrian.android.clinicappointments.login;

import com.adrian.android.clinicappointments.domain.FirebaseAPI;
import com.adrian.android.clinicappointments.domain.FirebaseActionListenerCallback;
import com.adrian.android.clinicappointments.libs.base.EventBus;
import com.adrian.android.clinicappointments.login.events.LoginEvent;
import com.firebase.client.FirebaseError;

/**
 * Created by adrian on 5/07/16.
 */
public class LoginRepositoryImpl implements LoginRepository {
    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    public LoginRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void signUp(final String email, final String password) {
        firebaseAPI.signUp(email, password, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                post(LoginEvent.ONSIGNUP_SUCCESS);
                signIn(email, password);
            }

            @Override
            public void onError(FirebaseError error) {
                post(LoginEvent.ONSIGNUP_ERROR, error.getMessage());
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        if (email != null && password != null) {
            firebaseAPI.login(email, password, new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = firebaseAPI.getAuthEmail();
                    post(LoginEvent.ONSIGNIN_SUCCESS, null, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    post(LoginEvent.ONSIGNIN_ERROR, error.getMessage());
                }
            });
        } else {
            firebaseAPI.checkForSession(new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = firebaseAPI.getAuthEmail();
                    post(LoginEvent.ONSIGNIN_SUCCESS, null, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    post(LoginEvent.ONFAILED_TO_RECOVER_SESSION);
                }
            });
        }
    }

    private void post(int type) {
        post(type, null);
    }

    private void post(int type, String errorMessage) {
        post(type, errorMessage, null);
    }

    private void post(int type, String errorMessage, String loggedUserEmail) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        loginEvent.setErrorMessage(errorMessage);
        loginEvent.setLoggedUserEmail(loggedUserEmail);
        eventBus.post(loginEvent);
    }
}
