package com.example.usuario.pr0053audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private MediaPlayer reproductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_white_24dp));
                prepararReproductor();
            }
        });
    }

    private void prepararReproductor() {
        // Se crea el reproductor.
        reproductor = new MediaPlayer();
        try {
            // Se establecen sus propiedades.
            reproductor.setDataSource(this, Uri.parse("https://www.youtube.com/audiolibrary_download?vid=036500ffbf472dcc")); // Fuente.
            reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC); // Música.
            // La actividad actuará como listener cuando el reproductor esté preparado.
            reproductor.setOnPreparedListener(this);
            // Se prepara el reproductor (asíncronamente)
            reproductor.prepareAsync();
        } catch (Exception e) {
            Log.d("Reproductor", "ERROR: " + e.getMessage());
        }
    }

    // Cuando el reproductor ya está preparado para reproducir.
    @Override
    public void onPrepared(MediaPlayer repr) {
        repr.start();
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
    public void finish() {
        if(reproductor != null){

            reproductor.stop();
            reproductor.release();
        }
    }
}
