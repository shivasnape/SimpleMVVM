package com.shivasnape.samplemvvm.app;

import android.app.Application;
import android.content.Context;

import com.shivasnape.samplemvvm.BuildConfig;
import com.shivasnape.samplemvvm.di.AppComponent;
import com.shivasnape.samplemvvm.di.AppModule;
import com.shivasnape.samplemvvm.di.DaggerAppComponent;
import com.shivasnape.samplemvvm.di.UtilsModule;

import timber.log.Timber;


public class AppController extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//        MultiDex.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
