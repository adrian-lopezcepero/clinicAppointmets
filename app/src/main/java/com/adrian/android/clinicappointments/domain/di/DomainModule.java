package com.adrian.android.clinicappointments.domain.di;

import android.content.Context;
import android.location.Geocoder;

import com.adrian.android.clinicappointments.domain.FirebaseAPI;
import com.adrian.android.clinicappointments.domain.Util;
import com.firebase.client.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adrian on 5/07/16.
 */

@Module
public class DomainModule {
    public static String FIREBASE_URL = "https://clinic-appointments.firebaseio.com";

    @Provides
    @Singleton
    FirebaseAPI providesFirebaseAPI(Firebase firebase, Util util) {
        return new FirebaseAPI(firebase, util);
    }

    @Provides
    @Singleton
    Firebase providesFirebase() {
        return new Firebase(FIREBASE_URL);
    }

    @Provides
    @Singleton
    Util providesUtil(Geocoder geocoder) {
        return new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context) {
        return new Geocoder(context);
    }

}
