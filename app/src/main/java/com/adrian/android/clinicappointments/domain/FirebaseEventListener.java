package com.adrian.android.clinicappointments.domain;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

/**
 * Created by adrian on 5/07/16.
 */
public interface FirebaseEventListener {
    void onChildAdded(DataSnapshot dataSnapshot);

    void onChildRemoved(DataSnapshot dataSnapshot);

    void onCancelled(FirebaseError error);

    void onChildChanged(DataSnapshot dataSnapshot);
}
