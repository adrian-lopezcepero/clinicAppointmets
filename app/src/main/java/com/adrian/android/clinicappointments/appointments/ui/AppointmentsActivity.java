package com.adrian.android.clinicappointments.appointments.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.adrian.android.clinicappointments.ClinicAppointmentsApp;
import com.adrian.android.clinicappointments.R;
import com.adrian.android.clinicappointments.addappointment.ui.AddAppointmentActivity;
import com.adrian.android.clinicappointments.appointments.AppointmentsPresenter;
import com.adrian.android.clinicappointments.appointments.ui.adapters.AppointmentsAdapter;
import com.adrian.android.clinicappointments.appointments.ui.adapters.OnItemClickListener;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.adrian.android.clinicappointments.login.ui.LoginActivity;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentsActivity extends AppCompatActivity implements AppointmentsView,
        OnItemClickListener {

    public static int ADD_APPOINTMENT = 0;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerViewAppointments)
    RecyclerView recyclerViewAppointments;
    @Bind(R.id.progressBarAppointments)
    ProgressBar progressBarAppointments;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.appointmentsContainer)
    CoordinatorLayout appointmentsContainer;

    @Inject
    AppointmentsPresenter appointmentsPresenter;
    @Inject
    AppointmentsAdapter adapter;
    @Inject
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        ButterKnife.bind(this);
        setupInjection();
        setupRecyclerView();
//        appointmentsPresenter.onCreate();
        toolbar.setTitle(getString(R.string.appointments_title));
        setSupportActionBar(toolbar);
    }


    private void setupInjection() {
        ClinicAppointmentsApp app = (ClinicAppointmentsApp) getApplication();
        app.getAppointmentComponent(this, this).inject(this);
    }

    @Override
    protected void onDestroy() {
        appointmentsPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        appointmentsPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        appointmentsPresenter.onResume();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        appointmentsPresenter.signOff();
        sharedPreferences.edit().clear().commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setupRecyclerView() {
        recyclerViewAppointments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAppointments.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void addAppointment() {
        Intent intent = new Intent(this, AddAppointmentActivity.class);
        startActivityForResult(intent, ADD_APPOINTMENT);
    }


    @Override
    public void onAppointmentAdded(Appointment appointment) {
        adapter.addAppointment(appointment);
    }

    @Override
    public void onAppointmentRemoved(Appointment appointment) {
        adapter.removeAppointment(appointment);
    }

    @Override
    public void onAppointmentChanged(Appointment appointment) {
        adapter.modifyAppointment(appointment);
    }

    @Override
    public void hideList() {
        recyclerViewAppointments.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBarAppointments.setVisibility(View.VISIBLE);
    }

    @Override
    public void showList() {
        recyclerViewAppointments.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBarAppointments.setVisibility(View.GONE);
    }

    @Override
    public void onAppointmentError(String error) {
        String msgError = String.format(getString(R.string.appointments_error_message), error);
        Snackbar.make(appointmentsContainer, msgError, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(Appointment appointment) {
        appointmentsPresenter.removeAppointment(appointment);
    }

    @Override
    public void onEditClick(Appointment appointment) {
        Intent intent = new Intent(this, AddAppointmentActivity.class);
        intent.putExtra("appointment", (Serializable) appointment);
        startActivityForResult(intent, ADD_APPOINTMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_APPOINTMENT) {
                Appointment appointment = (Appointment) data.getExtras().getSerializable
                        ("appointment");
                onAppointmentAdded(appointment);
            }

        }
    }
}
