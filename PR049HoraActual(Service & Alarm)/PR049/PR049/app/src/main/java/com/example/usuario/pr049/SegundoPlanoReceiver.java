package com.example.usuario.pr049;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class SegundoPlanoReceiver extends BroadcastReceiver {
    private static final int NC_HORA = 222;
    private static final int RC_MAIN_ACTIVITY = 333;

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        DatosZonaPOJO zonaPOJO = intent.getParcelableExtra(Servicio.EXTRA_RESPUESTA);
        String pais = zonaPOJO.getZoneName();
        String hora = format.format(new Date(zonaPOJO.getTimestamp()*1000L));
        crearNotificacion(context, hora, pais);
    }

    private void crearNotificacion(Context context, String hora, String pais) {
        NotificationManager mGestor =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Se crea el constructor de notificaciones.
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setSmallIcon(R.drawable.ic_clock);
        b.setContentTitle("Hora actualizada");
        b.setContentText("Pais: " + pais);
        b.setContentInfo("Hora: " + hora);
        b.setTicker("Se ha cambiado la hora");
        //Se abre el MainActivity cuando se presiona la notificación.
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, RC_MAIN_ACTIVITY, i, PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(pi);
        mGestor.notify(NC_HORA, b.build());
    }
}
