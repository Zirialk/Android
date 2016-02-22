package com.example.usuario.pr56mediarecorder;

import android.animation.Animator;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaRecorder.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    FloatingActionButton fab;
    // Constantes.
    private static final int MAX_DURACION_MS = 10000;
    MediaRecorder grabadora;
    private MediaPlayer reproductor;
    private boolean grabando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!grabando) {
                    fab.animate().scaleX(2).scaleY(2).start();
                    iniciarGrabacion();
                }
                // Si se suelta el botón, se finaliza la grabación.
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    fab.animate().scaleX(1).scaleY(1).start();
                    pararGrabacion();
                }
                return false;
            }
        });


    }

    // Inicia la grabación.
    private void iniciarGrabacion() {
        // Se prepara la grabación.
        prepararGrabacion();
        // Se inicia la grabación.
        grabadora.start();
        // Se cambia estado de grabación.
        cambiarEstadoGrabacion(true);
    }
    // Cambia el estado de grabación.
    private void cambiarEstadoGrabacion(boolean estaGrabando) {
        grabando = estaGrabando;
    }

    // Prepara la grabación.
    private void prepararGrabacion() {
        // Se crea el objeto grabadora.
        grabadora = new MediaRecorder();
        // Se configura la grabación con fichero de salida, origen, formato,
        // tipo de codificación y duración máxima.
        String pathGrabacion = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/audio.3gp";
        grabadora.setOutputFile(pathGrabacion);
        grabadora.setAudioSource(MediaRecorder.AudioSource.MIC);
        grabadora.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        grabadora.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        grabadora.setMaxDuration(MAX_DURACION_MS);
        grabadora.setOnInfoListener(this);
        // Se prepara la grabadora (de forma síncrona).
        try {
            grabadora.prepare();
        } catch (IOException e) {
            Log.e(getString(R.string.app_name), "Fallo en grabación");
        }
    }
    // Detiene la grabación en curso.
    private void pararGrabacion() {
        // Se detiene la grabación y se liberan los recursos de la grabadora.
        if (grabadora != null) {
            grabadora.stop();
            grabadora.release();
            grabadora = null;
        }
        // Se cambia el estado de grabación y el icono del botón.
        cambiarEstadoGrabacion(false);
        // Se prepara la reproducción.
        prepararReproductor();
    }

    private void prepararReproductor() {
        // Si ya existía reproductor, se elimina.
        if (reproductor != null) {
            reproductor.reset();
            reproductor.release();
            reproductor = null;
        }
        // Se crea el objeto reproductor.
        reproductor = new MediaPlayer();
        try {
            // Path de la grabación.
            reproductor.setDataSource(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/audio.3gp");
            // Stream de audio que utilizará el reproductor.
            reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // Volumen
            reproductor.setVolume(1.0f, 1.0f);
            // La actividad actuará como listener cuando el reproductor ya esté
            // preparado para reproducir y cuando se haya finalizado la
            // reproducción.
            reproductor.setOnPreparedListener(this);
            reproductor.setOnCompletionListener(this);
            // Se prepara el reproductor.
            // reproductor.prepare(); // síncrona.
            reproductor.prepareAsync(); // asíncrona.
        } catch (Exception e) {
            Log.d("Reproductor", "ERROR: " + e.getMessage());
        }
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
    public void onInfo(MediaRecorder mr, int what, int extra) {
        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED || what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED)
            // Se cambia el icono del botón para que el usuario se de cuenta de que ya no está grabando.
            fab.animate().scaleX(1).scaleY(1);

    }
    //Reproductor on Prepared
    @Override
    public void onPrepared(MediaPlayer mp) {
        // Se inicia la reproducción.
        reproductor.start();
        // Se desactiva el botón de grabación.
        fab.setEnabled(false);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        fab.setEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Se liberan los recursos del reproductor.
        if (reproductor != null) {
            reproductor.release();
            reproductor = null;
        }
        // Se liberan los recursos de la grabadora.
        if (grabadora != null) {
            grabadora.release();
            grabadora = null;
        }
    }
}
