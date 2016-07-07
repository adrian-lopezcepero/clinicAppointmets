package com.adrian.android.clinicappointments.entities;

import java.io.Serializable;

/**
 * Created by adrian on 6/07/16.
 */
public class Patient implements Serializable {
    private int id;
    private String patient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
}
