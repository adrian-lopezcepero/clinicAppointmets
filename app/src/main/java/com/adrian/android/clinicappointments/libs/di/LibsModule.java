package com.adrian.android.clinicappointments.libs.di;

import android.app.Fragment;

import com.adrian.android.clinicappointments.libs.GlideImageLoader;
import com.adrian.android.clinicappointments.libs.GreenRobotEventBus;
import com.adrian.android.clinicappointments.libs.base.EventBus;
import com.adrian.android.clinicappointments.libs.base.ImageLoader;

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

    @Provides
    @Singleton
    ImageLoader providesImageLoader(Fragment fragment) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (fragment != null) {
            imageLoader.setLoaderContext(fragment);
        }
        return imageLoader;
    }
}
