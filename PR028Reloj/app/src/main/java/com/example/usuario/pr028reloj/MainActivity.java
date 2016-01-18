package com.example.usuario.pr028reloj;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIniciar;
    private TextView lblTiempo;
    private Thread hiloSecundario;
    private final SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        lblTiempo = (TextView) findViewById(R.id.lblTiempo);
        lblTiempo.setText(formateador.format(new Date()));
        btnIniciar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnIniciar:
                if (btnIniciar.getText().toString().equals(getResources().getString(R.string.iniciar)))
                    iniciar();
                else
                    parar();
                break;
        }
    }
    private void iniciar(){
        hiloSecundario = new Thread(new Reloj(), "Secundario");

        hiloSecundario.start();

        btnIniciar.setText("Parar");
    }

    private void parar(){
        hiloSecundario.interrupt();
        btnIniciar.setText(getString(R.string.iniciar));
    }

    private class Reloj implements Runnable{


        final SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");


        @Override
        public void run() {
            while (true){
                lblTiempo.post(new Runnable() {
                    @Override
                    public void run() {
                        // Se obtiene la representación en cadena de la hora actual.
                        // La variable debe ser final para que se pueda enviar en el
                        // Runnable que se envía al hilo principal.
                        final String cadena = formateador.format(new Date());

                        lblTiempo.setText(cadena);
                    }
                });
                //Espera 1 segundo tras cambiar la cadena de la hora
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //Se finaliza el hilo si el hilo es interrumpido mientras duerme
                    return;
                }

            }

        }
    }
}
