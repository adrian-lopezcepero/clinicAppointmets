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
//    Activity activity;

//    public LibsModule(Context context) {
//        this.context = context;
//    }

//    public void setActivity(Activity activity) {
//        this.activity = activity;
//    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return new GreenRobotEventBus();
    }

//    @Provides
//    @Singleton
//    public ImageLoader providesImageLoader() {
//        GlideImageLoader imageLoader = new GlideImageLoader();
//        if (activity != null) {
//            imageLoader.setActivity(this.activity);
//        }
//        return imageLoader;
//    }

}
