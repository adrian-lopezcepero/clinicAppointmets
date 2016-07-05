package com.adrian.android.clinicappointments.entities;

import java.util.Date;

/**
 * Created by adrian on 5/07/16.
 */
public class Appointment {
    public final static boolean CLINICAPPOINTMENT = true;
    private Date initDate;
    private Date endDate;
    private String patient;
    private Double longitude;
    private Double latitude;
    private boolean isClinicappointment;

    public Appointment(Date initDate, Date endDate, String patient, Double longitude, Double
            latitude, boolean isClinicappointment) {
        this.initDate = initDate;
        this.endDate = endDate;
        this.patient = patient;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isClinicappointment = isClinicappointment;
    }


    public boolean isClinicappointment() {
        return isClinicappointment;
    }

    public void setClinicappointment(boolean clinicappointment) {
        isClinicappointment = clinicappointment;
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

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;

        Appointment that = (Appointment) o;

        if (isClinicappointment != that.isClinicappointment) return false;
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
        result = 31 * result + (isClinicappointment ? 1 : 0);
        return result;
    }
}
