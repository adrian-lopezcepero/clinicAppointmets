package com.adrian.android.clinicappointments.domain;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

/**
 * Created by adrian on 8/07/16.
 */
public interface FirebaseFilterListenerCallback {
    void onSuccess(DataSnapshot dataSnapshot);

    void onError(FirebaseError firebaseError);
}
