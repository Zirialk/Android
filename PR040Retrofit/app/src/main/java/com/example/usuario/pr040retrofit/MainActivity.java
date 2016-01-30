package com.example.usuario.pr040retrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    private TextView txtContenido;
    private Button btnCrear;
    Instituto.InstitutoInterface servicio;
    Alumno alumno;
    int idAlumno;
    private Button btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        servicio = Instituto.getServicio();
        Call<List<Alumno>> llamada = servicio.listarAlumnos();
        initViews();
        llamada.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Response<List<Alumno>> response) {

                txtContenido.setText(response.body().get(0).getNombre());

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void initViews() {
        txtContenido = (TextView) findViewById(R.id.txtContenido);
        btnCrear = (Button) findViewById(R.id.btnCrear);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alumno = new Alumno(true,18,"La Loba","956640333", "DAM 1A", "Pepe Juanes se", "http://lorempixel.com/200/300/abstract/3/");
                Call<Alumno> llamada = servicio.crearAlumno(alumno);
                llamada.enqueue(new Callback<Alumno>() {
                    @Override
                    public void onResponse(Response<Alumno> response) {
                        Toast.makeText(MainActivity.this,response.body().getNombre()+ " introducido",Toast.LENGTH_SHORT).show();
                        idAlumno = response.body().getId();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });

        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alumno!=null){
                    Call<Alumno> llamada = servicio.borrarAlumno(idAlumno);
                    llamada.enqueue(new Callback<Alumno>() {
                        @Override
                        public void onResponse(Response<Alumno> response) {
                            Toast.makeText(MainActivity.this,response.body()+" borrado",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });

                }
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
}
