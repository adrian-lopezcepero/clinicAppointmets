package com.adrian.android.clinicappointments.appointments.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adrian.android.clinicappointments.ClinicAppointmentsApp;
import com.adrian.android.clinicappointments.R;
import com.adrian.android.clinicappointments.addappointment.ui.AddAppointmentActivity;
import com.adrian.android.clinicappointments.appointments.AppointmentsPresenter;
import com.adrian.android.clinicappointments.appointments.ui.adapters.AppointmentsAdapter;
import com.adrian.android.clinicappointments.appointments.ui.adapters.OnItemClickListener;
import com.adrian.android.clinicappointments.domain.Util;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.adrian.android.clinicappointments.login.ui.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentsActivity extends AppCompatActivity implements AppointmentsView,
        OnItemClickListener {

    public static int ADD_APPOINTMENT = 0;
    public static int MODIFIED_APPOINTMENT = 1;

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
    @Bind(R.id.prevDateBtn)
    ImageButton prevDateBtn;
    @Bind(R.id.textViewDate)
    TextView textViewDate;
    @Bind(R.id.nextDateBtn)
    ImageButton nextDateBtn;

    @Inject
    AppointmentsPresenter appointmentsPresenter;
    @Inject
    AppointmentsAdapter adapter;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    Util util;

    private Calendar initDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        ButterKnife.bind(this);

        setupInjection();
        setupRecyclerView();

        setInitDate(null);
        appointmentsPresenter.onCreate(initDate);
        toolbar.setTitle(getString(R.string.appointments_title));
        setSupportActionBar(toolbar);
    }

    private void setInitDate(Calendar calendar) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
            calendar.clear(Calendar.HOUR);
            calendar.clear(Calendar.MINUTE);
            calendar.clear(Calendar.SECOND);
            calendar.clear(Calendar.MILLISECOND);
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.initDate = calendar;
        textViewDate.setText(df.format(initDate.getTime()));
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
        sharedPreferences.edit().clear().apply();
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
    @OnClick(R.id.nextDateBtn)
    public void onIncDate() {
        changeFilterDate(true);
        setInitDate(initDate);
        adapter.clearAppointments();
        appointmentsPresenter.subsribeToCeckForData(initDate.getTimeInMillis());
    }

    @OnClick(R.id.prevDateBtn)
    @Override
    public void onDecDate() {
        changeFilterDate(false);
        setInitDate(initDate);
        adapter.clearAppointments();
        appointmentsPresenter.subsribeToCeckForData(initDate.getTimeInMillis());
    }

    private void changeFilterDate(boolean incDate) {
        int day = incDate ? 1 : -1;
        initDate.add(Calendar.DATE, day);
    }

    @Override
    public void onDateChanged(Appointment appointment) {
        adapter.addAppointment(appointment);
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
        intent.putExtra("appointment", appointment);
        startActivityForResult(intent, MODIFIED_APPOINTMENT);
    }

    @Override
    public void onPlaceClick(Appointment appointment) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Double lat = Double.parseDouble(appointment.getLatitude());
        Double lng = Double.parseDouble(appointment.getLongitude());
        String uriStr = "geo:" + appointment.getLatitude() + "," + appointment.getLongitude() +
                "?q=" + util.getFromLocation(lat, lng);
        intent.setData(Uri.parse(uriStr));
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @OnClick(R.id.textViewDate)
    @Override
    public void onPickDate() {
        DatePickerDialog pickerDialog = new DatePickerDialog(this, R.style.AppTheme_Dialog, new
                DatePickerDialog
                        .OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int
                            dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth, 0, 0);
                        setInitDate(cal);
                        appointmentsPresenter.subsribeToCeckForData(initDate.getTimeInMillis());
                    }
                }, initDate.get(Calendar.YEAR), initDate.get(Calendar.MONTH), initDate.get(Calendar
                .DAY_OF_MONTH));
        pickerDialog.setTitle(getString(R.string.appointments_pickdate_title));
        pickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_APPOINTMENT) {
                Appointment appointment = (Appointment) data.getExtras().getSerializable
                        ("appointment");
                onAppointmentAdded(appointment);
                onDateChanged(appointment);
            } else if (requestCode == MODIFIED_APPOINTMENT) {
                Appointment appointment = (Appointment) data.getExtras().getSerializable
                        ("appointment");
                onAppointmentChanged(appointment);
                onDateChanged(appointment);
            }

        }
    }

}
