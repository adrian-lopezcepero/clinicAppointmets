package com.adrian.android.clinicappointments.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by adrian on 5/07/16.
 */
public class Appointment implements Serializable, Comparable<Appointment> {
    public final static boolean CLINICAPPOINTMENT = true;
    @JsonIgnore
    private String id;

    private Date initDate;
    private Date endDate;
    private Patient patient;
    private String longitude;
    private String latitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;

        Appointment that = (Appointment) o;

        if (!initDate.equals(that.initDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!patient.equals(that.patient)) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null)
            return false;
        return latitude != null ? latitude.equals(that.latitude) : that.latitude == null;

    }

    @Override
    public int hashCode() {
        int result = initDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + patient.hashCode();
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Appointment another) {
        return another.getInitDate().compareTo(this.getInitDate());
    }


}
