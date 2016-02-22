package com.example.usuario.pr048alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Si la alarma debe estar on, se reprograma.
        if (AvisarReceiver.isAlarmaOn(context))
            AvisarReceiver.reprogramarAlarma(context);

    }
}
