package com.adrian.android.clinicappointments.appointments.ui;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    private Date dateFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        ButterKnife.bind(this);

        setupInjection();
        setupRecyclerView();

        setDateFilter(new Date());
        appointmentsPresenter.onCreate(dateFilter);
        toolbar.setTitle(getString(R.string.appointments_title));
        setSupportActionBar(toolbar);
    }

    private void setDateFilter(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateFilter = df.parse(df.format(date));
            textViewDate.setText(df.format(dateFilter));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    @OnClick(R.id.nextDateBtn)
    public void onIncDate() {
        changeFilterDate(true);
        setDateFilter(dateFilter);
        adapter.clearAppointments();
        appointmentsPresenter.subsribeToCeckForData();
    }

    @OnClick(R.id.prevDateBtn)
    @Override
    public void onDecDate() {
        changeFilterDate(false);
        setDateFilter(dateFilter);
        adapter.clearAppointments();
        appointmentsPresenter.subsribeToCeckForData();
    }

    private void changeFilterDate(boolean incDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFilter);
        int day = incDate ? 1 : -1;
        cal.add(Calendar.DATE, day);
        dateFilter = cal.getTime();
    }

    @Override
    public void onDateChanged(Appointment appointment) {
        if (isFilterDate(appointment.getInitDate())) {
            adapter.addAppointment(appointment);
        }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_APPOINTMENT) {
                Appointment appointment = (Appointment) data.getExtras().getSerializable
                        ("appointment");
                if (isFilterDate(appointment.getInitDate())) {
                    onAppointmentAdded(appointment);
                }
            } else if (requestCode == MODIFIED_APPOINTMENT) {
                Appointment appointment = (Appointment) data.getExtras().getSerializable
                        ("appointment");
                if (isFilterDate(appointment.getInitDate())) {
                    onAppointmentChanged(appointment);
                }
            }

        }
    }

    private boolean isFilterDate(Date initDate) {
        Date endDate = getEndDate();
        return initDate.after(dateFilter) && initDate.before(endDate);
    }

    private Date getEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFilter);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }
}
