package com.example.usuario.pr030asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView lblMensaje;
    private ProgressBar prbBarra;
    private ProgressBar prbCirculo;
    private Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        lblMensaje = (TextView) findViewById(R.id.lblMensaje);
        prbBarra = (ProgressBar) findViewById(R.id.prbBarra);
        prbCirculo = (ProgressBar) findViewById(R.id.prbCirculo);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MiTarea().execute();
            }
        });
    }

    class MiTarea extends AsyncTask<Void, Integer, Void>{

        @Override
        protected void onPreExecute() {
            mostrarBarras();
        }

        private void mostrarBarras() {
            prbBarra.setVisibility(View.VISIBLE);
            lblMensaje.setText(R.string.trabajando);
            lblMensaje.setVisibility(View.VISIBLE);
            prbCirculo.setVisibility(View.VISIBLE);
            prbBarra.setMax(10);
        }

        @Override
        protected Void doInBackground(Void... params) {

            for(int i= 0 ; i < 10 ; i++){
                trabajar();
                publishProgress(i+1);

            }
            return null;
        }

        private void trabajar() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            lblMensaje.setText(getString(R.string.trabajando, values[0], 10));
            prbBarra.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void Void) {
            resetearBarras();
            lblMensaje.setText(getResources().getQuantityString(R.plurals.realizadas, 10, 10));
        }

        private void resetearBarras() {
            prbBarra.setVisibility(View.INVISIBLE);
            prbBarra.setProgress(0);
            prbCirculo.setVisibility(View.INVISIBLE);
            prbCirculo.setProgress(0);
            btnIniciar.setEnabled(true);
        }
    }
}
