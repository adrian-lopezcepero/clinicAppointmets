package com.adrian.android.clinicappointments.addappointment.di;

import com.adrian.android.clinicappointments.addappointment.AddAppointmentInteractor;
import com.adrian.android.clinicappointments.addappointment.AddAppointmentInteractorImpl;
import com.adrian.android.clinicappointments.addappointment.AddAppointmentPresenter;
import com.adrian.android.clinicappointments.addappointment.AddAppointmentPresenterImpl;
import com.adrian.android.clinicappointments.addappointment.AddAppointmentRepository;
import com.adrian.android.clinicappointments.addappointment.AddAppointmentRepositoryImpl;
import com.adrian.android.clinicappointments.addappointment.ui.AddAppointmentView;
import com.adrian.android.clinicappointments.domain.FirebaseAPI;
import com.adrian.android.clinicappointments.libs.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adrian on 7/07/16.
 */
@Module
public class AddAppointmentModule {

    AddAppointmentView addAppointmentView;

    public AddAppointmentModule(AddAppointmentView view) {
        this.addAppointmentView = view;
    }

    @Provides
    @Singleton
    AddAppointmentView providesAddAppointmentView() {
        return this.addAppointmentView;
    }

    @Provides
    @Singleton
    AddAppointmentPresenter providesAddAppointmentPresenter(EventBus eventBus,
                                                            AddAppointmentInteractor
            addAppointmentInteractor, AddAppointmentView addAppointmentView) {
        return new AddAppointmentPresenterImpl(eventBus, addAppointmentInteractor,
                addAppointmentView);
    }

    @Provides
    @Singleton
    AddAppointmentInteractor providesAddAppointmentInteractor(AddAppointmentRepository
                                                                      addAppointmentRepository) {
        return new AddAppointmentInteractorImpl(addAppointmentRepository);
    }

    @Provides
    @Singleton
    AddAppointmentRepository providesAddAppointmentRepository(EventBus eventBus, FirebaseAPI
            firebaseAPI) {
        return new AddAppointmentRepositoryImpl(eventBus, firebaseAPI);
    }
}
