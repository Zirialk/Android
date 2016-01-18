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
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
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
        lblMensaje.setText(R.string.trabajando);
        lblMensaje.setVisibility(View.VISIBLE);
    }
    private void actualizarBarras(int progreso){
        lblMensaje.setText(getString(R.string.trabajando, progreso, 10));
        prbBarra.setProgress(progreso);
    }
    //Muestra el total de las tareas realizadas.
    private void mostrarRealizadas(int tareasRealizadas){
        lblMensaje.setText(getResources().getQuantityString(R.plurals.realizadas, tareasRealizadas, tareasRealizadas));

    }
    private void resetearVistas(){
        prbBarra.setVisibility(View.INVISIBLE);
        prbBarra.setProgress(0);
        prbCirculo.setVisibility(View.INVISIBLE);
        prbCirculo.setProgress(0);
        btnIniciar.setEnabled(true);
    }


    private class HiloSecundario implements Runnable{

        @Override
        public void run() {
            Message msgProgreso;

            //Crea y envia el mensaje de inicio al manejador
            Message msgInicio = new Message();
            msgInicio.what = ANTES_DE_EMPEZAR;
            manejador.sendMessage(msgInicio);

            //Se realizan 10 pasos
            for(int i = 0; i<10; i++){
                trabajar();

                msgProgreso = new Message();
                msgProgreso.what = EN_PROGRESO;
                msgProgreso.arg1= i + 1;
                manejador.sendMessage(msgProgreso);
            }
            //Crea y envía el mensaje de fin al manejador
            Message msgFin = new Message();
            msgFin.what = AL_TERMINAR;
            msgFin.arg1=10;
            manejador.sendMessage(msgFin);

        }
        //Simula que trabaja 1 segundo
        private void trabajar(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                    //Mensaje de inicio del hilo secundario
                    case ANTES_DE_EMPEZAR:
                        //Se hacen visibles las barras de progreso
                        activity.mostrarBarras();
                        break;
                    //Mensaje de en progreso del hilo secundario
                    case EN_PROGRESO:
                        int progreso = msg.arg1;
                        activity.actualizarBarras(progreso);
                        break;
                    //Mensaje de fin del hilo secundario.
                    case AL_TERMINAR:
                        //Se informa al usuario y se reinician las vistas.
                        int tareas = msg.arg1;
                        activity.mostrarRealizadas(tareas);
                        activity.resetearVistas();
                        break;
                }

            }
        }
    }
}
