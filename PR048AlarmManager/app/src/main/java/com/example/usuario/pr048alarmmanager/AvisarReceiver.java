package com.example.usuario.pr048alarmmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;


public class AvisarReceiver extends BroadcastReceiver {

    private static final String EXTRA_MENSAJE = "intent_mensaje";
    private static final int RC_ENTENDIDO = 1;
    private static final int RC_AVISO = 2;
    private static final int NC_AVISAR = 3;
    public static final String PREF_FILENAME = "alarmas";
    public static final String PREF_ESTADO = "estado_alarma";
    public static final String PREF_INTERVALO = "pref_intervalo";
    public static final String PREF_MENSAJE = "pref_mensaje";
    public static final int DEFAULT_INTERVAL = 10;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mGestor = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Se configura la notificación.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(context.getString(R.string.aviso_importante));
        builder.setContentText(intent.getStringExtra(EXTRA_MENSAJE));
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setTicker(context.getString(R.string.aviso_importante));
        //Desaparece la notificación al ser pulsada.
        builder.setAutoCancel(true);
        //Al pursarse sobre la notificación se abrirá la actividad principal.
        Intent i =  new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, RC_ENTENDIDO, i, 0);
        builder.setContentIntent(pi);
        //Se construye y muestra la notificación.
        mGestor.notify(NC_AVISAR, builder.build());
    }
    public static void programarAlarma(Context context, String mensaje, int intervalo){
        //Se obtiene el gestor de alarma.
        AlarmManager gestorAlarma = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //Cuando se dispare la alarma, se llamará al broadcast correspondiente.
        Intent intent = new Intent(context, AvisarReceiver.class);
        intent.putExtra(EXTRA_MENSAJE, mensaje);
        PendingIntent pi = PendingIntent.getBroadcast(context, RC_AVISO, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //Se añade la alarma de tipo repetitivo y despertador.
        gestorAlarma.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + intervalo, intervalo, pi);
        //Se guarda el estado de la alarma en las SharedPreferences.
        guardarEstado(context, true, mensaje, intervalo);

    }
    //Reprograma la alarma con los datos guardados en el SharedPreferences.
    public static void reprogramarAlarma(Context context){
        //Se obtienen los datos del sharedpreferences.
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
        String mensaje = preferences.getString(PREF_MENSAJE, context.getString(R.string.quillo_ponte_ya_a_currar));
        int intervalo = preferences.getInt(PREF_INTERVALO, DEFAULT_INTERVAL);
        //Se programa la alarma
        programarAlarma(context, mensaje, intervalo);

    }
    public static void cancelarAlarma(Context context){
        //Se obtiene la alarma.
        AlarmManager gestorAlarma = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //Se crea un pendingIntent que equivalente a programarAlarma.+
        Intent intent = new Intent(context, AvisarReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, RC_AVISO, intent, 0);
        //Se cancela la alarma
        gestorAlarma.cancel(pi);
        //Se almacena el estado de la alarma en las preferencias.
        guardarEstado(context, false, "", 0);
    }
    //Guarda los datos necesarios en el archivo de preferencias.
    private static void guardarEstado(Context context, boolean on, String mensaje, int intervalo) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_ESTADO, on);
        //Si la alarma está activada, se guardará el mensaje y el intervalo.
        if (on){
            editor.putString(PREF_MENSAJE, mensaje);
            editor.putInt(PREF_INTERVALO, intervalo);
        }
        editor.apply();

    }
    public static boolean isAlarmaOn(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(PREF_ESTADO, false);
    }
}
