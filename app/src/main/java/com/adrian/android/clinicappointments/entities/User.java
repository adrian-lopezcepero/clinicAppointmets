package com.adrian.android.clinicappointments.entities;

/**
 * Created by adrian on 5/07/16.
 */
public class User {
    public final static boolean ONLINE = true;
    public final static boolean OFFLINE = false;
    private String email;
    private boolean isOnline;

    public User(String email, boolean isOnline) {
        this.email = email;
        this.isOnline = isOnline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
