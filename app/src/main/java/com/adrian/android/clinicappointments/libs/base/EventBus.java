package com.adrian.android.clinicappointments.libs.base;

/**
 * Created by adrian on 5/07/16.
 */
public interface EventBus {
    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);
}
