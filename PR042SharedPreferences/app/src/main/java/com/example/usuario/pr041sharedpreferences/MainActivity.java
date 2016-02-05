package com.example.usuario.pr041sharedpreferences;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    private TextView txtNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fab.animate().setDuration(1200).rotation(360).translationX(-30).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                         fab.setRotationX(0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                //Se abre (o se crea en su defecto) el archivo de preferencias
                SharedPreferences preferencias = getSharedPreferences("pref_apl", Context.MODE_PRIVATE);

                int numero = Integer.valueOf(txtNumero.getText().toString())+1;
                txtNumero.setText(String.valueOf(numero));
                //Guardo el número en el archivo de preferencias
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putInt("total", numero);
                editor.apply();
            }
        });
    }

    private void initViews() {
        SharedPreferences preferencias = getSharedPreferences("pref_apl", Context.MODE_PRIVATE);
        txtNumero = (TextView) findViewById(R.id.txtNumero);
        txtNumero.setText(String.valueOf(preferencias.getInt("total",0)));
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
