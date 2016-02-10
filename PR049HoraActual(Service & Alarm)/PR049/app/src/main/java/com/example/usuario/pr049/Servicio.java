package com.example.usuario.pr049;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Servicio extends IntentService {
    public static final String SERVICE_NAME = "consultarHora";
    public static final String EXTRA_RESPUESTA = "Hora";
    public static final String EXTRA_PAIS = "Pais";
    private final int ESPERA = 1;
    public static final String ACTION_COMPLETADA = "com.example.usuario.pr049.completada";


    public Servicio() {
        super(SERVICE_NAME);
    }
    @Override
    protected void onHandleIntent(final Intent intent) {
        try {
            //Se simula que se tarda en obtener los datos.
            TimeUnit.SECONDS.sleep(ESPERA);
            //Se consulta la API de las horas.
            String pais = intent.getStringExtra(EXTRA_PAIS);
            Call<DatosZonaPOJO> llamada = TimeZoneAPI.getmInstance().getServicio().getHora(pais);
            llamada.enqueue(new Callback<DatosZonaPOJO>() {
                @Override
                public void onResponse(Call<DatosZonaPOJO> call, Response<DatosZonaPOJO> response) {
                    //Intent implicito a todos los que puedan "escuchar" ACTION_COMPLETADA.
                    Intent intentRespuesta = new Intent(ACTION_COMPLETADA);
                    intentRespuesta.putExtra(EXTRA_RESPUESTA, response.body());
                    sendOrderedBroadcast(intentRespuesta, null);
                }

                @Override
                public void onFailure(Call<DatosZonaPOJO> call, Throwable t) {

                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Se llama al onStartCommand del padre.
        super.onStartCommand(intent, flags, startId);
        // El servicio NO será reiniciado automáticamente.
        return START_NOT_STICKY;
    }
}
