package com.example.aleja.practica2trimestre;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by aleja on 10/02/2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
