package com.adrian.android.clinicappointments.login.events;

/**
 * Created by adrian on 5/07/16.
 */
public class LoginEvent {
    public final static int ONSIGNIN_ERROR = 0;
    public final static int ONSIGNUP_ERROR = 1;
    public final static int ONSIGNIN_SUCCESS = 2;
    public final static int ONSIGNUP_SUCCESS = 3;
    public final static int ONFAILED_TO_RECOVER_SESSION = 4;

    private int eventType;
    private String errorMessage;
    private String loggedUserEmail;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getLoggedUserEmail() {
        return loggedUserEmail;
    }

    public void setLoggedUserEmail(String loggedUserEmail) {
        this.loggedUserEmail = loggedUserEmail;
    }
}
