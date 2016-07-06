package com.adrian.android.clinicappointments.login;

import com.adrian.android.clinicappointments.login.events.LoginEvent;

/**
 * Created by adrian on 5/07/16.
 */
public interface LoginPresenter {
    void onCreate();

    void onDestroy();

    void onResume();

    void onPause();

    void onEventMainThread(LoginEvent event);

    void login(String email, String password);

    void registerNewUser(String email, String password);


}
