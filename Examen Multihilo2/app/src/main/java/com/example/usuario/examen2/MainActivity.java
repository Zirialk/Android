package com.example.usuario.examen2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import api.Main;
import api.OpenWeatherApi;
import api.Respuesta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_RESPUESTA = "stateResp";
    private TextView lblCiudad;
    private TextView lblDescripcion;
    private TextView lblTemperatura;
    private TextView lblLluvia;
    private TextView lblHumedad;
    private TextView lblViento;
    private TextView lblNubosidad;
    private TextView lblAmanecer;
    private ImageView imgIcono;
    private EditText txtCiudad;
    private SwitchCompat swAlarma;
    private Respuesta resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        if(savedInstanceState != null){
            resp =(Respuesta) savedInstanceState.getParcelable(STATE_RESPUESTA);
            if(resp != null)
                cargarDatos(resp);
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Respuesta> llamada = OpenWeatherApi.getmInstance().getServicio().getTraduccion(Constantes.APPID, txtCiudad.getText().toString(), Constantes.UNITS, Constantes.LANG);
                llamada.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        resp = response.body();
                        cargarDatos(resp);
                    }

                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {
                        Toast.makeText(MainActivity.this, R.string.errorBusqueda, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void cargarDatos(Respuesta resp){
        if(resp.getName() != null){
            lblCiudad.setText(String.format("%s, %s", resp.getName(), resp.getSys().getCountry()));
            lblDescripcion.setText(resp.getWeather().get(0).getDescription());
            lblTemperatura.setText(String.format("Temperatura:%nmin %sºC%nmax %sºC%nmedia %sºC", resp.getMain().getTempMin(), resp.getMain().getTempMax(), resp.getMain().getTemp()));
            if(resp.getRain() != null)
                lblLluvia.setText(String.valueOf(resp.getRain().get3h()));
            else
                lblLluvia.setText(R.string.lblSinLluvia);
            lblHumedad.setText(String.format("%s%%", resp.getMain().getHumidity()));
            lblViento.setText(String.format("velocidad %s mps%ndireccion %sº", resp.getWind().getSpeed(), resp.getWind().getDeg()));
            lblNubosidad.setText(String.valueOf(resp.getClouds().getAll()));
            SimpleDateFormat format = new SimpleDateFormat("HH:MM", Locale.getDefault());
            lblAmanecer.setText(String.format("Amanecer: %s Atardecer: %s", format.format(resp.getSys().getSunrise()), format.format(resp.getSys().getSunset())));
            Picasso.with(MainActivity.this).load(String.format("http://openweathermap.org/img/w/%s.png", resp.getWeather().get(0).getIcon())).into(imgIcono);
        }else
            Toast.makeText(MainActivity.this, getString(R.string.ciudadNoEncontrada), Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        txtCiudad = (EditText) findViewById(R.id.txtCiudad);
        lblCiudad = (TextView) findViewById(R.id.lblCiudad);
        lblDescripcion = (TextView) findViewById(R.id.lblDescripcion);
        lblTemperatura = (TextView) findViewById(R.id.lblTemperatura);
        lblLluvia = (TextView) findViewById(R.id.lblLluvia);
        lblHumedad = (TextView) findViewById(R.id.lblHumedad);
        lblViento = (TextView) findViewById(R.id.lblViento);
        lblNubosidad = (TextView) findViewById(R.id.lblNubosidad);
        lblAmanecer = (TextView) findViewById(R.id.lblAmanecer);
        imgIcono = (ImageView) findViewById(R.id.imgIcono);
        swAlarma = (SwitchCompat) findViewById(R.id.swAlarma);
        swAlarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    AlarmaReceiver.programarAlarma(MainActivity.this);
                else
                    AlarmaReceiver.cancelarAlarma(MainActivity.this);
            }
        });
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_RESPUESTA, resp);
        super.onSaveInstanceState(outState);
    }


}
