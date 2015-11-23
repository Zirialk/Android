package com.example.usuario.pr023listconfragment;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListaFragment.OnItemSelected, FragmentManager.OnBackStackChangedListener{


    private static final String STATE_ALUMNO = "alumno";
    private FragmentManager mGestor;
    private Alumno mAlumnoSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mGestor = getSupportFragmentManager();
        mGestor.addOnBackStackChangedListener(this);
        initViews();

    }

    private void initViews() {
        ArrayList<Alumno> listaAlumno = new ArrayList<>();
        listaAlumno.add(new Alumno("Alejandro Torres Gómez", 18, "Jimena", "La Loba", "http://lorempixel.com/1000/1000/sports/6/"));
        listaAlumno.add(new Alumno("Manué", 18, "Algeciras", "Sevilla", "http://lorempixel.com/1000/1000/people/3/"));
        listaAlumno.add(new Alumno("Estefan", 32, "Conil", "Cádiz", "http://lorempixel.com/1000/1000/people/2/"));
        listaAlumno.add(new Alumno("Lucia", 22, "Castelló de la Plana", "Valencia", "http://lorempixel.com/1000/1000/people/9/"));
        listaAlumno.add(new Alumno("Baldomero", 42, "Estepona", "Málaga", "http://lorempixel.com/1000/1000/people/8/"));
        listaAlumno.add(new Alumno("Teresa", 25, "Sabinillas", "Málaga", "http://lorempixel.com/1000/1000/people/1/"));
        listaAlumno.add(new Alumno("Ana", 12, "Granada", "Granada", "http://lorempixel.com/1000/1000/people/10/"));
        listaAlumno.add(new Alumno("Pepe", 42, "Madrid", "Madrid", "http://lorempixel.com/1000/1000/people/7/"));

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
        if(mGestor.findFragmentByTag(tag)== null) {
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

        if(findViewById(R.id.flHuecoSecundario)==null) { //Modo Vertical
            //Se inicia otra actividad con los detalles del alumno pulsado.
            mAlumnoSeleccionado =alumno;
            DetallesActivity.start(this, alumno);
        }

        else
            loadFragmentDetalles(R.id.flHuecoSecundario, alumno, alumno.getNombre());
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Elimina el item de menú cuando esta en postrait
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
             menu.removeItem(R.id.itemLlamar);
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_ALUMNO, mAlumnoSeleccionado);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Cuando vuelve de DetallesActivity y la pantalla se encuentra apaisada, marcará el item de la lista que estaba observandose
        //anteriormente en DetallesActivity.
        if(getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mAlumnoSeleccionado =savedInstanceState.getParcelable(STATE_ALUMNO);
            if(mAlumnoSeleccionado !=null){
                loadFragmentDetalles(R.id.flHuecoSecundario, mAlumnoSeleccionado, mAlumnoSeleccionado.getNombre());
            }


        }

    }
}
