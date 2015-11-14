package com.example.usuario.pr023listconfragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lsvAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ArrayList<Alumno> listaAlumno = new ArrayList<Alumno>();

        lsvAlumnos = (ListView) findViewById(R.id.lsvAlumnos);
        AlumnoAdapter adaptador;

        listaAlumno.add(new Alumno("Alejandro Torres Gómez",18,"Jimena","La Loba","http://lorempixel.com/1000/1000/people/1/"));
        listaAlumno.add(new Alumno("Manué",18,"Algeciras","Sevilla","http://lorempixel.com/1000/1000/people/3/"));
        adaptador = new AlumnoAdapter(this,listaAlumno);

        lsvAlumnos.setAdapter(adaptador);
        loadFragment();
        lsvAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetallesActivity.start(MainActivity.this, (Alumno) parent.getItemAtPosition(position));
            }
        });
    }

    private void loadFragment(String idHueco) {
        FragmentManager gestor;
        FragmentTransaction transaction;

        gestor= getSupportFragmentManager();
        //Iniciando la transacción del fragmento.
        transaction = gestor.beginTransaction();
        //Se carga el fragmento en el hueco.
        transaction.replace(idHueco,ListaFragment.newInstance());

        transaction.commit();
    }


}
