package com.example.HealthyCampus.module.Mine.Register.second;

import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;

public class RegisterPresenter2 extends RegisterContract2.Presenter {


    @Override
    public void onStart() {

    }

    @Override
    public void listenRegisterEditText() {
        getView().listenRegisterEditTextStatus();
    }

    @Override
    public void foucusRegisterEditText() {
        getView().focusRegisterEditTextStatus();
    }

    @Override
    public void register(RegisterFrom registerFrom) {
        getView().showProgressView();
        UserRepository.getInstance().register(registerFrom, new UserDataSource.UserRegister() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().dismissProgressView();
                getView().showRegisterError(throwable);
            }
            @Override
            public void registerSuccess(String username) {
//                getView().setPageEnable();
                getView().dismissProgressView();
                getView().showTipsView(username);
            }
        });
    }

    @Override
    public RegisterFrom encapsulationData(String phone, String _password) {
        RegisterFrom registerFrom = new RegisterFrom();
        registerFrom.setPassword(_password);
        registerFrom.setPhone(phone);
        return registerFrom;
    }
}