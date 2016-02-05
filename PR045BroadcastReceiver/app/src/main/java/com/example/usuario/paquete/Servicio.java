package com.example.usuario.paquete;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by Usuario on 04/02/2016.
 */
public class Servicio extends IntentService {
    private static final String SERVICE_NAME = "exportar";
    private static final String ACTION_COMPLETADA = "completed";
    private static final String EXTRA_FILENAME = "extraRespuesta";

    public Servicio() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            TimeUnit.SECONDS.sleep(5);
            devolverRespuesta();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void devolverRespuesta() {
        Intent respuestaIntent = new Intent(ACTION_COMPLETADA);
        respuestaIntent.putExtra(EXTRA_FILENAME, "Respuesta");
        LocalBroadcastManager.getInstance(this).sendBroadcast(
                respuestaIntent);
    }
}
