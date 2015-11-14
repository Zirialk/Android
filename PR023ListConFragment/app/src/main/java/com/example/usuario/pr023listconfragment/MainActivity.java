package com.example.usuario.pr023listconfragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListaFragment.OnItemSelected, FragmentManager.OnBackStackChangedListener{

    private FragmentManager mGestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGestor = getSupportFragmentManager();
        mGestor.addOnBackStackChangedListener(this);
        initViews();
    }

    private void initViews() {
        ArrayList<Alumno> listaAlumno = new ArrayList<Alumno>();
        listaAlumno.add(new Alumno("Alejandro Torres Gómez", 18, "Jimena", "La Loba", "http://lorempixel.com/1000/1000/people/1/"));
        listaAlumno.add(new Alumno("Manué", 18, "Algeciras", "Sevilla", "http://lorempixel.com/1000/1000/people/3/"));

        loadFragmentLista(R.id.flHuecoPrincipal, listaAlumno, "fgrListaVertical");


    }

    private void loadFragmentLista(int idHueco, ArrayList<Alumno> listaAlumnos,String tag) {
        FragmentTransaction transaction = mGestor.beginTransaction();

        //Solo se cargará el fragmento si no estaba cargado de antes.
        if (mGestor.findFragmentByTag(tag) == null) {
            transaction.replace(idHueco, ListaFragment.newInstance(listaAlumnos), tag);
            transaction.addToBackStack("a");
        }else
            transaction.replace(idHueco, mGestor.findFragmentByTag(tag));
        transaction.commit();
    }

    private void loadFragmentDetalles(int idHueco, Alumno alumno, String tag){
        FragmentTransaction transaction = mGestor.beginTransaction();
        //Solo se cargará el fragmento si no estaba cargado de antes.
        if(mGestor.findFragmentByTag(tag)==null){
            transaction.replace(idHueco, DetallesFragment.newInstance(alumno), tag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

            transaction.commit();
        }
    }

    @Override
    public void onBackStackChanged() {
        //Si ya no existe nada más en la cola del BackStack al presionar el botón atrás, se saldrá de la aplicación.
        if(mGestor.getBackStackEntryCount()==0)
            onBackPressed();
    }
    @Override
    public void pulsado(Alumno alumno) {

        if(findViewById(R.id.flHuecoSecundario)==null) //Modo Vertical
            //Se inicia otra actividad con los detalles del alumno pulsado.
            DetallesActivity.start(this,alumno);

        else
            loadFragmentDetalles(R.id.flHuecoSecundario, alumno, alumno.getNombre());
    }
}
