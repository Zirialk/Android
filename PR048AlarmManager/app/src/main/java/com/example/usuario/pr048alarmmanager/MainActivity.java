package com.example.usuario.pr048alarmmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView txtMensaje;
    private TextView txtIntervalo;
    private SwitchCompat swActivar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        txtMensaje = (TextView) findViewById(R.id.txtMensaje);
        txtIntervalo = (TextView) findViewById(R.id.txtIntervalo);
        swActivar = (SwitchCompat) findViewById(R.id.swActivar);

        SharedPreferences preferencias = getApplicationContext().getSharedPreferences(AvisarReceiver.PREF_FILENAME, MODE_PRIVATE);
        txtMensaje.setText(preferencias.getString(AvisarReceiver.PREF_MENSAJE, getString(R.string.quillo_ponte_ya_a_currar)));
        txtIntervalo.setText(String.valueOf(preferencias.getInt(AvisarReceiver.PREF_INTERVALO, AvisarReceiver.DEFAULT_INTERVAL)));
        swActivar.setChecked(AvisarReceiver.isAlarmaOn(getApplicationContext()));
        swActivar.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            //Se programa la alarma con los datos introducidos por el usuario.
            String mensaje = TextUtils.isEmpty(txtMensaje.getText().toString())? getString(R.string.quillo_ponte_ya_a_currar) : txtMensaje.getText().toString();
            int intervalo;

            try {
                intervalo = Integer.parseInt(txtIntervalo.getText().toString());
            } catch (NumberFormatException e) {
                intervalo = AvisarReceiver.DEFAULT_INTERVAL;
            }
            AvisarReceiver.programarAlarma(getApplicationContext(), mensaje, intervalo);
        }else
            // Se desactiva la alarma.
            AvisarReceiver.cancelarAlarma(getApplicationContext());
    }
}
