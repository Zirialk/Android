package com.example.usuario.paquete;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;


public class AlgoReceiver extends BroadcastReceiver{
    private static final int NC_TAREA = 3;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mGestor = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //Icono pequeño.
        builder.setSmallIcon(R.drawable.ic_action_dislike);
        builder.setContentTitle("Dislikes");
        builder.setContentText("Dislike recibido.");

        String url = "http://www.marca.es";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        //Hace que desaparezca la notificación de la barra de notificaciones.
        builder.setAutoCancel(true);
        mGestor.notify(NC_TAREA, builder.build());
    }
}
