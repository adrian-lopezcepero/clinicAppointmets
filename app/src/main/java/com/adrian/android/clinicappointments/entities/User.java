package com.adrian.android.clinicappointments.entities;

import java.io.Serializable;

/**
 * Created by adrian on 5/07/16.
 */
public class User implements Serializable {
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
