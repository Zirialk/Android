package com.example.usuario.examen2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;

import java.util.concurrent.TimeUnit;


public class AlarmaReceiver extends BroadcastReceiver{
    private static final int NC_AVISAR = 223;
    private static final int RC_ENTENDIDO = 23111;
    private static final int RC_AVISO = 4222;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mGestor = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Se configura la notificación.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_action_search);
        builder.setContentTitle(context.getString(R.string.notificationContentTitle));
        builder.setContentText(context.getString(R.string.notificationContentText));
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setTicker("Han pasado " + Constantes.TIEMPO_ALARMA + " segundos sin consultar la alarma");
        //Desaparece la notificación al ser pulsada.
        builder.setAutoCancel(true);
        //Al pursarse sobre la notificación se abrirá la actividad principal.
        Intent i =  new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, RC_ENTENDIDO, i, 0);
        builder.setContentIntent(pi);
        //Se construye y muestra la notificación.
        mGestor.notify(NC_AVISAR, builder.build());
    }
    public static void programarAlarma(Context context){
        //Se obtiene el gestor de alarma.
        AlarmManager gestorAlarma = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //Cuando se dispare la alarma, se llamará al broadcast correspondiente.
        Intent intent = new Intent(context, AlarmaReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, RC_AVISO, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //Se añade la alarma de tipo repetitivo y despertador.
        long miliSegs = TimeUnit.SECONDS.toMillis(Constantes.TIEMPO_ALARMA);
        gestorAlarma.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + miliSegs, miliSegs, pi);

    }
    public static void cancelarAlarma(Context context){
        //Se obtiene la alarma.
        AlarmManager gestorAlarma = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmaReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, RC_AVISO, intent, 0);
        //Se cancela la alarma
        gestorAlarma.cancel(pi);
    }
}
