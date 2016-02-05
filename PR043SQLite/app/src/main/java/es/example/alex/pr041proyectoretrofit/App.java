package es.example.alex.pr041proyectoretrofit;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Usuario on 01/02/2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
