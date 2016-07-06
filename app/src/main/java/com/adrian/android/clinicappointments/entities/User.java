package com.adrian.android.clinicappointments.entities;

/**
 * Created by adrian on 5/07/16.
 */
public class User {
    private String email;

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
