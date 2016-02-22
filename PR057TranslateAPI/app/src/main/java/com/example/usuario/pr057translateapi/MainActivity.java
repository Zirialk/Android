package com.example.usuario.pr057translateapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Spinner spLenguajes;
    private TextView lblTraduccion;
    private TextView txtTexto;
    private BroadcastReceiver mRespuestaServicio;

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
                Call<String> llamada = YandexAPI.getmInstance().getServicio().getTraduccion("trnsl.1.1.20160219T114153Z.08a56fcdd8347276.2054e9715a6d4f29e494f247eb14a767c65e0e3f","es-en", "Hola");
                llamada.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        txtTexto.setText(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println();
                    }
                });

            }
        });
    }

    private void initViews() {
        spLenguajes = (Spinner) findViewById(R.id.spLenguaje);
        txtTexto = (TextView) findViewById(R.id.txtTexto);
        lblTraduccion = (TextView) findViewById(R.id.lblTraduccion);


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
