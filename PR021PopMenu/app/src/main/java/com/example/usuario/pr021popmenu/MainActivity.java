package com.example.usuario.pr021popmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lsvAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ArrayList<Alumno> listaAlumnos= new ArrayList<Alumno>();
        lsvAlumnos=(ListView) findViewById(R.id.lsvAlumnos);
        AlumnoAdapter adaptador;
        listaAlumnos.add(new Alumno("Ale","627111222"));
        listaAlumnos.add(new Alumno("Imae","627333244"));
        adaptador = new AlumnoAdapter(this,listaAlumnos);
        lsvAlumnos.setAdapter(adaptador);


    }


}
