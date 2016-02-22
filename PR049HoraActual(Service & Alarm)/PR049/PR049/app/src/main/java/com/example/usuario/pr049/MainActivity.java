package com.example.usuario.pr049;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static final int INTERVALO = 2000;
    private static final int RC_HORA = 22;
    private static final String PREFERENCIAS_FILE = "Archivo de preferencias.";
    private static final String PREF_NAME_PAIS = "pais";
    private TextView lblHora;
    private Spinner spPaises;
    private BroadcastReceiver mRespuestaServicio;
    private SharedPreferences mPreferences;
    SegundoPlanoReceiver mReceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPreferences = getSharedPreferences(PREFERENCIAS_FILE, Context.MODE_PRIVATE);
        initViews();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configAlarma();
                //Se guarda en el archivo de preferencias el item seleccionado del comboBox.
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putInt(PREF_NAME_PAIS, spPaises.getSelectedItemPosition());
                editor.apply();
            }
        });

    }


    private void initViews() {
        lblHora = (TextView) findViewById(R.id.lblHora);
        spPaises = (Spinner) findViewById(R.id.spPaises);
        //Recupera el item selected del archivo de preferencias.
        spPaises.setSelection(mPreferences.getInt(PREF_NAME_PAIS, 0));
        //Broadcast en respuesta del servicio, donde se aplicará los cambios en la interfaz.
        mRespuestaServicio = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                DatosZonaPOJO respuesta = intent.getParcelableExtra(Servicio.EXTRA_RESPUESTA);
                //Se necesita multiplicar por 1000, por que la API lo devuelve en formato UNIX
                configurarReloj(respuesta.getTimestamp()*1000L);
                abortBroadcast();
            }
        };
        configAlarma();

    }

    public void configAlarma() {
        AlarmManager gestorAlarmas = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Servicio.class);
        intent.putExtra(Servicio.EXTRA_PAIS, spPaises.getSelectedItem().toString());
        PendingIntent pi = PendingIntent.getService(this, RC_HORA, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        gestorAlarmas.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + INTERVALO, INTERVALO, pi);
    }


    private void configurarReloj(long milliSeconds){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date hora = new Date(milliSeconds);
        lblHora.setText(format.format(hora));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Se registra el receptor para la acción.
        IntentFilter respuestaFilter = new IntentFilter(Servicio.ACTION_COMPLETADA);
        //Se le da prioridad al broadcast local para que no salga la notificación mientras estamos dentro de la aplicación
        respuestaFilter.setPriority(5);
        registerReceiver(mRespuestaServicio, respuestaFilter);
    }

    @Override
    protected void onPause() {
        // Se desregistra el receptor para dicha acción
        unregisterReceiver(mRespuestaServicio);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
