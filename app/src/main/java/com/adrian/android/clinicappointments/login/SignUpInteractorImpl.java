package com.adrian.android.clinicappointments.login;

/**
 * Created by adrian on 5/07/16.
 */
public class SignUpInteractorImpl implements SignUpInteractor {
    private LoginRepository loginRepository;

    public SignUpInteractorImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void execute(String email, String password) {
        loginRepository.signUp(email, password);
    }
}
