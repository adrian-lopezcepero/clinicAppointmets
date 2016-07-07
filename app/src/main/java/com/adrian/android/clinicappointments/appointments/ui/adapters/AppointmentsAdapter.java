package com.adrian.android.clinicappointments.appointments.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adrian.android.clinicappointments.R;
import com.adrian.android.clinicappointments.domain.Util;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.google.android.gms.maps.MapView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by adrian on 6/07/16.
 */
public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {


    private Util util;
    private List<Appointment> appointments;
    private OnItemClickListener onItemClickListener;

    public AppointmentsAdapter(Util util, List<Appointment> appointments, OnItemClickListener
            onItemClickListener) {
        this.util = util;
        this.appointments = appointments;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .content_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        double lat = appointment.getLatitude();
        double lng = appointment.getLongitude();

        DateFormat timeFormat = new SimpleDateFormat("HH:MM");
        holder.txtHour.setText(timeFormat.format(appointment.getInitDate()));
        holder.txtPatient.setText(appointment.getPatient().getPatient());

        // TODO: 6/07/16 Add lat and lgn to googlemaps view
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(0, appointment);
        notifyDataSetChanged();
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        notifyDataSetChanged();
    }

    public void modifyAppointment(Appointment appointment) {
        for (Appointment appment : appointments) {
            if (appment.getId() == appointment.getId()) {
                appment = appointment;
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtHour)
        TextView txtHour;
        @Bind(R.id.txtPatient)
        TextView txtPatient;
        @Bind(R.id.layoutPatient)
        LinearLayout layoutPatient;
        @Bind(R.id.mapview)
        MapView mapview;
        @Bind(R.id.imgEdit)
        ImageButton imgEdit;
        @Bind(R.id.imgDelete)
        ImageButton imgDelete;
        @Bind(R.id.layoutButtons)
        LinearLayout layoutButtons;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final Appointment appointment, final
        OnItemClickListener listener) {
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditClick(appointment);
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(appointment);
                }
            });
            // TODO: 6/07/16 Add click event to googlemaps view to open google maps
        }
    }
}
