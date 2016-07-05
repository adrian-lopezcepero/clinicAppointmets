package com.adrian.android.clinicappointments.domain.di;

import com.adrian.android.clinicappointments.domain.FirebaseAPI;
import com.firebase.client.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adrian on 5/07/16.
 */

@Module
public class DomainModule {
    public static String FIREBASE_URL = "";

    @Provides
    @Singleton
    FirebaseAPI providesFirebaseAPI(Firebase firebase) {
        return new FirebaseAPI(firebase);
    }


}
