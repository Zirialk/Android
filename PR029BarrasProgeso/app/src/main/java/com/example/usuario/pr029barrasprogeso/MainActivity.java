package com.example.usuario.pr029barrasprogeso;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final int ANTES_DE_EMPEZAR = 0;
    private static final int EN_PROGRESO = 1;
    private static final int AL_TERMINAR = 2;
    private ProgressBar prbBarra;
    private ProgressBar prbCirculo;
    private TextView lblMensaje;
    private Button btnIniciar;

    private Manejador manejador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        prbBarra = (ProgressBar) findViewById(R.id.prbBarra);
        prbCirculo = (ProgressBar) findViewById(R.id.prbCirculo);
        lblMensaje = (TextView) findViewById(R.id.lblMensaje);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar();
            }
        });
    }
    private void iniciar() {
        btnIniciar.setEnabled(false);
        // Se crea el manejador.
        manejador = new Manejador(this);
        // Se crea la tarea secundaria.
        Runnable tarea = new HiloSecundario();
        // Se crea el hilo y se inicia.
        Thread hiloSecundario = new Thread(tarea);
        hiloSecundario.start();
    }

    private void mostrarBarras(){
        prbBarra.setVisibility(View.VISIBLE);
        prbCirculo.setVisibility(View.VISIBLE);
        lblMensaje.setText(getString(R.string.trabajando));
        lblMensaje.setVisibility(View.VISIBLE);
    }
    private void actualizarBarras(int progreso){
        lblMensaje.setText(getString(R.string.trabajando, progreso, 10));
        prbBarra.setProgress(progreso);
    }


    private class HiloSecundario implements Runnable{

        @Override
        public void run() {

        }
    }
    //Manejador
    static class Manejador extends Handler{
        private final WeakReference<MainActivity> mActivityWeakReference;

        Manejador(MainActivity activity) {
            this.mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivityWeakReference.get();
            if(activity!=null){
                switch(msg.what){
                    case ANTES_DE_EMPEZAR:

                        break;
                }

            }
        }
    }
}
