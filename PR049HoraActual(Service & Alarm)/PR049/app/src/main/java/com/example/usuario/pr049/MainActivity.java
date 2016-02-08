package com.example.usuario.pr049;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private TextView lblHora;
    private Spinner spPaises;
    private BroadcastReceiver mRespuestaServicio;
    private LocalBroadcastManager mGestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Servicio.class);
                intent.putExtra(Servicio.EXTRA_PAIS, spPaises.getSelectedItem().toString());
                startService(intent);
            }
        });
    }



    private void initViews() {
        lblHora = (TextView) findViewById(R.id.lblHora);
        spPaises = (Spinner) findViewById(R.id.spPaises);
        mGestor = LocalBroadcastManager.getInstance(this);
        //Broadcast en respuesta del servicio, donde se aplicará los cambios en la interfaz.
        mRespuestaServicio = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                DatosZonaPOJO respuesta = intent.getParcelableExtra(Servicio.EXTRA_RESPUESTA);
                //Se necesita multiplicar por 1000, por que la API lo devuelve en formato UNIX
                configurarReloj(respuesta.getTimestamp()*1000L);
            }
        };

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
        mGestor.registerReceiver(mRespuestaServicio, respuestaFilter);
    }

    @Override
    protected void onPause() {
        // Se desregistra el receptor para dicha acción
        mGestor.unregisterReceiver(mRespuestaServicio);
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
