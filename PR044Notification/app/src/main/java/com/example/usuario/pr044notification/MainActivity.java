package com.example.usuario.pr044notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int NC_TAREA = 3;
    private Button btnNotificacion;
    private NotificationManager mGestor;
    private int numDislikes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnNotificacion = (Button) findViewById(R.id.btnNotificacion);
        btnNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGestor = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                //Icono pequeño.
                builder.setSmallIcon(R.drawable.ic_action_dislike);
                builder.setContentTitle("Dislikes");
                builder.setContentText(numDislikes++ + " dislikes.");

                String url = "http://www.marca.es";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));

                PendingIntent pendingIntent =
                        PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentIntent(pendingIntent);
                //Hace que desaparezca la notificación de la barra de notificaciones.
                builder.setAutoCancel(true);
                mGestor.notify(NC_TAREA, builder.build());
            }
        });
    }
}
