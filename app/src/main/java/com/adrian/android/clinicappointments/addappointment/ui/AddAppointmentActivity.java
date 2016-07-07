package com.adrian.android.clinicappointments.addappointment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adrian.android.clinicappointments.ClinicAppointmentsApp;
import com.adrian.android.clinicappointments.R;
import com.adrian.android.clinicappointments.addappointment.AddAppointmentPresenter;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.adrian.android.clinicappointments.entities.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAppointmentActivity extends AppCompatActivity implements AddAppointmentView {

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

    @Inject
    AddAppointmentPresenter presenter;

    private Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        ButterKnife.bind(this);
        setupInjection();
        presenter.onCreate();

        setTitle(getString(R.string.addappointments_title_newAppointment));
        checkForData();

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
        SimpleDateFormat tf = new SimpleDateFormat("hh:mm");
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
        txtAddress.setText(appointment.getLatitude().toString() + "," +
                appointment.getLongitude().toString());
    }

    private void setCurrentDatetime() {
        Date datetime = new Date();
        setDatetimeInputs(new Date());
    }

    private void setAppointmentsFromInputs() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        try {
            String datetime = txtDate.getText().toString() + " " + txtTime.getText().toString();
            Date dateFormAppointment = df.parse(datetime);
            appointment.setInitDate(dateFormAppointment);
            appointment.setEndDate(dateFormAppointment);
            appointment.setLatitude(0.0);
            appointment.setLongitude(0.0);
            Patient patient = new Patient();
            patient.setPatient(editTxtPatient.getText().toString());
            appointment.setPatient(patient);
        } catch (ParseException e) {
            Snackbar.make(layoutAddAppointmetContainer, getString(R.string
                    .addappointments_error_datetime), Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}
