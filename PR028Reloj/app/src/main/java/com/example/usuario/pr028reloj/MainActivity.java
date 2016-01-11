package com.example.usuario.pr028reloj;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Button btnIniciar;
    private TextView lblTiempo;
    private Thread hiloSecundario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        lblTiempo = (TextView) findViewById(R.id.lblTiempo);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lblTiempo.getText().toString().equals(getResources().getString(R.string.iniciar)))
                    iniciar();
                else
                    parar();
            }
        });
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
                        lblTiempo.setText(formateador.format(new Date()));
                    }
                });
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    return;
                }

            }

        }
    }
}
