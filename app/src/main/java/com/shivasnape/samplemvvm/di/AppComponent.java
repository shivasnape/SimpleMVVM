package com.shivasnape.samplemvvm.di;



import com.shivasnape.samplemvvm.view.login.LoginActivity;
import com.shivasnape.samplemvvm.view.register.SignUpActivity;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(LoginActivity loginActivity);

    void doInjection(SignUpActivity signUpActivity);

}
