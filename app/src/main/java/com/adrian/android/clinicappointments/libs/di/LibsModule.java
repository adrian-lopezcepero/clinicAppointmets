package com.adrian.android.clinicappointments.libs.di;

import android.content.Context;

import com.adrian.android.clinicappointments.libs.GlideImageLoader;
import com.adrian.android.clinicappointments.libs.GreenRobotEventBus;
import com.adrian.android.clinicappointments.libs.base.EventBus;
import com.adrian.android.clinicappointments.libs.base.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adrian on 5/07/16.
 */
@Module
public class LibsModule {
    Context context;

    public LibsModule(Context context) {
        this.context = context;
    }


    @Provides
    @Singleton
    EventBus providesEventBus() {
        return new GreenRobotEventBus();
    }

    @Provides
    @Singleton
    public ImageLoader providesImageLoader(RequestManager requestManager) {
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(Context context) {
        return Glide.with(context);
    }

}
