package com.adrian.android.clinicappointments.addappointment.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adrian.android.clinicappointments.ClinicAppointmentsApp;
import com.adrian.android.clinicappointments.R;
import com.adrian.android.clinicappointments.addappointment.AddAppointmentPresenter;
import com.adrian.android.clinicappointments.domain.Util;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.adrian.android.clinicappointments.entities.Patient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAppointmentActivity extends AppCompatActivity implements AddAppointmentView,
        OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private static final int PERMISSIONS_REQUEST_LOCATION = 1;


    @Bind(R.id.editTxtPatient)
    EditText editTxtPatient;
    @Bind(R.id.txtTime)
    EditText txtTime;
    @Bind(R.id.txtDate)
    EditText txtDate;
    @Bind(R.id.txtAddress)
    EditText txtAddress;
    @Bind(R.id.btnAccept)
    Button btnAccept;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutAddAppointmetContainer)
    RelativeLayout layoutAddAppointmetContainer;
    @Bind(R.id.btnSearchMap)
    ImageButton btnSearchMap;
    @Bind(R.id.layoutAddress)
    LinearLayout layoutAddress;

    @Inject
    AddAppointmentPresenter presenter;
    @Inject
    Util util;

    private Appointment appointment;
    private GoogleMap map;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        ButterKnife.bind(this);
        setupInjection();
        setupMap();

        presenter.onCreate();

        setTitle(getString(R.string.addappointments_title_newAppointment));
        checkForData();

    }

    private void setupMap() {
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id
                .addAppointmentMap);
        mapFragment.getMapAsync(this);
    }

    private void checkForData() {
        Intent intentData = getIntent();
        appointment = (Appointment) intentData.getSerializableExtra("appointment");
        if (appointment != null) {
            setTitle(getString(R.string.addappointments_title_modifiedAppointment));
            setAppointmentOnInputs(appointment);
        } else
            setCurrentDatetime();
    }

    private void setDatetimeInputs(Date datetime) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        txtTime.setText(tf.format(datetime));
        txtDate.setText(df.format(datetime));
    }

    private void setupInjection() {
        ClinicAppointmentsApp app = (ClinicAppointmentsApp) getApplication();
        app.getAddAppointmentComponent(this).inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableImputs() {
        setInputs(false);
    }

    @OnClick(R.id.btnAccept)
    @Override
    public void onAddAppointment() {

//        If appointment is not null then we are modifying an appointment
        if (appointment != null) {
            setAppointmentsFromInputs();
            presenter.modifyAppointment(appointment);
        } else {
            appointment = new Appointment();
            setAppointmentsFromInputs();
            presenter.addAppointment(appointment);
        }
    }

    @OnClick(R.id.btnSearchMap)
    @Override
    public void onAddAddressToMap() {
        addAddressToMap();
    }

    private void addAddressToMap() {
        String address = txtAddress.getText().toString();
        if (address != null && !address.isEmpty()) {
            LatLng location = util.getLocationFromAddress(address);
            if (location == null) {
                String errorMsg = getString(R.string.addappointments_error_address);
                Snackbar.make(layoutAddAppointmetContainer, errorMsg, Snackbar.LENGTH_SHORT).show();
            } else {
                map.clear();
                marker = map.addMarker(new MarkerOptions().position(location));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
            }
        }
    }

    @Override
    public void addAppointment(Appointment appointment) {
        if (appointment != null) {
            Intent result = new Intent();
            result.putExtra("appointment", appointment);
            setResult(RESULT_OK, result);
            finish();
        }
    }

    @Override
    public void modifyAppointment(Appointment appointment) {
        if (appointment != null) {
            Intent result = new Intent();
            result.putExtra("appointment", appointment);
            setResult(RESULT_OK, result);
            finish();
        }
    }

    @Override
    public void showError(String error) {
        String errorMsg = String.format(getString(R.string.addappointments_error_addapointment),
                error);
        appointment = null;
        Snackbar.make(layoutAddAppointmetContainer, errorMsg, Snackbar.LENGTH_LONG).show();
    }

    private void setInputs(boolean enabled) {
        editTxtPatient.setEnabled(enabled);
        txtTime.setEnabled(enabled);
        txtDate.setEnabled(enabled);
        txtAddress.setEnabled(enabled);
    }

    private void setAppointmentOnInputs(Appointment appointment) {
        setDatetimeInputs(appointment.getInitDate());
        editTxtPatient.setText(appointment.getPatient().getPatient());
        if (appointment.getLatitude() != null && appointment.getLongitude() != null) {
            Double lat = Double.parseDouble(appointment.getLatitude());
            Double lng = Double.parseDouble(appointment.getLongitude());
            String address = util.getFromLocation(lat, lng);
            txtAddress.setText(address);
        }
    }

    private void setCurrentDatetime() {
        Date datetime = new Date();
        setDatetimeInputs(new Date());
    }

    private void setAppointmentsFromInputs() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            String datetime = txtDate.getText().toString() + " " + txtTime.getText().toString();
            Date dateFormAppointment = df.parse(datetime);
            appointment.setInitDate(dateFormAppointment);
            appointment.setEndDate(dateFormAppointment);
            if (marker != null) {
                String latStr = String.valueOf(marker.getPosition().latitude);
                String lngStr = String.valueOf(marker.getPosition().longitude);
                appointment.setLatitude(latStr);
                appointment.setLongitude(lngStr);
            }
            Patient patient = new Patient();
            patient.setPatient(editTxtPatient.getText().toString());
            appointment.setPatient(patient);
        } catch (ParseException e) {
            Snackbar.make(layoutAddAppointmetContainer, getString(R.string
                    .addappointments_error_datetime), Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setInfoWindowAdapter(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission
                        .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            map.setMyLocationEnabled(true);
        }
        addAddressToMap();
    }
}
