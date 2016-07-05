package com.adrian.android.clinicappointments.libs.di;

import com.adrian.android.clinicappointments.libs.GreenRobotEventBus;
import com.adrian.android.clinicappointments.libs.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adrian on 5/07/16.
 */
@Module
public class LibsModule {

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return new GreenRobotEventBus();
    }
}
