package com.adrian.android.clinicappointments.login;

/**
 * Created by adrian on 5/07/16.
 */
public interface LoginRepository {
    void signUp(String email, String password);

    void signIn(String email, String password);
}
