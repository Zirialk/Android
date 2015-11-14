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
        loadFragmentLista(R.id.flHuecoLista, listaAlumno,"fgrListaVertical");

    }



    private void loadFragmentLista(int idHueco, ArrayList<Alumno> listaAlumnos,String tag){
        FragmentTransaction transaction= mGestor.beginTransaction();


        //Solo se cargará el fragmento si no estaba cargado de antes.
        if(mGestor.findFragmentByTag(tag)==null) {
            transaction.replace(idHueco, ListaFragment.newInstance(listaAlumnos), tag);
            transaction.addToBackStack("a");
        }

        transaction.commit();
    }

    private void loadFragmentDetalles(int idHueco, Alumno alumno, String tag,boolean guardarEnPila){
        FragmentTransaction transaction = mGestor.beginTransaction();
        //Solo se cargará el fragmento si no estaba cargado de antes.
        if(mGestor.findFragmentByTag(tag)==null){
            transaction.replace(idHueco, DetallesFragment.newInstance(alumno), tag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            //Decide si guardarse en la BackStack.
            if(guardarEnPila)
                transaction.addToBackStack(alumno.getNombre());
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
        //Si no existe flHuecoDetalles, es porque el movil no está en modo apaisado (LandScape).
        if(findViewById(R.id.flHuecoDetalles)==null)
            //Se le pasa true, para que se guarde en pila ya que al reemplazar al fragmento anterior,
            //lo necesita para no eliminar el anterior.
            loadFragmentDetalles(R.id.flHuecoLista, alumno, "fgrListaHorizontal",true);
        else
            //No necesita guardar en pila porque se cargará el fragmento contiguo a la lista, ya que se encuentra en apaisado.
            loadFragmentDetalles(R.id.flHuecoDetalles, alumno, alumno.getNombre(),false);
    }
}
