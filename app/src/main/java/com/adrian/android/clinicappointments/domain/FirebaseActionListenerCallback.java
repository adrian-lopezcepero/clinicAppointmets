package com.adrian.android.clinicappointments.domain;

import com.firebase.client.FirebaseError;

/**
 * Created by adrian on 5/07/16.
 */
public interface FirebaseActionListenerCallback {
    void onSuccess();

    void onError(FirebaseError error);
}
