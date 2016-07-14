package com.adrian.android.clinicappointments.appointments.di;

import com.adrian.android.clinicappointments.appointments.AppointmentsInteractor;
import com.adrian.android.clinicappointments.appointments.AppointmentsInteractorImpl;
import com.adrian.android.clinicappointments.appointments.AppointmentsPresenter;
import com.adrian.android.clinicappointments.appointments.AppointmentsPresenterImpl;
import com.adrian.android.clinicappointments.appointments.AppointmentsRepository;
import com.adrian.android.clinicappointments.appointments.AppointmentsRepositoryImpl;
import com.adrian.android.clinicappointments.appointments.AppointmentsSessionInteractor;
import com.adrian.android.clinicappointments.appointments.AppointmentsSessionInteractorImpl;
import com.adrian.android.clinicappointments.appointments.ui.AppointmentsView;
import com.adrian.android.clinicappointments.appointments.ui.adapters.AppointmentsAdapter;
import com.adrian.android.clinicappointments.appointments.ui.adapters.OnItemClickListener;
import com.adrian.android.clinicappointments.domain.FirebaseAPI;
import com.adrian.android.clinicappointments.domain.Util;
import com.adrian.android.clinicappointments.entities.Appointment;
import com.adrian.android.clinicappointments.libs.base.EventBus;
import com.adrian.android.clinicappointments.libs.base.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adrian on 6/07/16.
 */
@Module
public class AppointmentsModule {
    AppointmentsView view;
    OnItemClickListener onItemClickListener;

    public AppointmentsModule(AppointmentsView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides
    @Singleton
    AppointmentsView providesAppointmentsView() {
        return this.view;
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.onItemClickListener;
    }

    @Provides
    @Singleton
    AppointmentsPresenter providesAppointmentsPresenter(EventBus eventBus,
                                                        AppointmentsSessionInteractor
                                                                sessionInteractor,
                                                        AppointmentsInteractor
                                                                appointmentsInteractor,
                                                        AppointmentsView
                                                                view) {
        return new AppointmentsPresenterImpl(eventBus, sessionInteractor, appointmentsInteractor,
                view);
    }

    @Provides
    @Singleton
    AppointmentsAdapter providesAppointmentsAdapter(Util util, List<Appointment> appointments,
                                                    ImageLoader imageLoader,
                                                    OnItemClickListener
                                                            onItemClickListener) {
        return new AppointmentsAdapter(util, appointments, imageLoader, onItemClickListener);
    }


    @Provides
    @Singleton
    AppointmentsSessionInteractor providesAppointmentsSessionInteractor(AppointmentsRepository
                                                                                repository) {
        return new AppointmentsSessionInteractorImpl(repository);
    }

    @Provides
    @Singleton
    AppointmentsInteractor providesAppointmentsInteractor(AppointmentsRepository repository) {
        return new AppointmentsInteractorImpl(repository);
    }

    @Provides
    @Singleton
    AppointmentsRepository providesAppointmentsRepository(FirebaseAPI firebaseAPI, EventBus
            eventBus) {
        return new AppointmentsRepositoryImpl(firebaseAPI, eventBus);
    }

    @Provides
    @Singleton
    List<Appointment> providesAppointments() {
        return new ArrayList<Appointment>();
    }


}
