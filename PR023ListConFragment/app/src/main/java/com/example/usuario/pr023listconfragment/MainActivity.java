package com.example.usuario.pr023listconfragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ListaFragment.OnItemSelected, FragmentManager.OnBackStackChangedListener, DetallesFragment.CambiarImg{


    private static final String STATE_ALUMNO = "alumno";
    private static final String TAG_FRG_LISTA = "fgrLista";
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
        loadFragmentLista(R.id.flHuecoPrincipal, TAG_FRG_LISTA);
    }

    private void loadFragmentLista(int idHueco,String tag) {
        FragmentTransaction transaction = mGestor.beginTransaction();

        //Solo se cargará el fragmento si no estaba cargado de antes.
        if (mGestor.findFragmentByTag(tag) == null) {
            transaction.replace(idHueco, ListaFragment.newInstance(), tag);
            transaction.addToBackStack("a");
        }else
            transaction.replace(idHueco, mGestor.findFragmentByTag(tag));
        transaction.commit();
    }

    private void loadFragmentDetalles(int idHueco, Alumno alumno, String tag){
        FragmentTransaction transaction = mGestor.beginTransaction();
        //Solo se cargará el fragmento si no estaba cargado de antes.
        if(mGestor.findFragmentByTag(tag)== null) {
            transaction.replace(idHueco, DetallesFragment.newInstance(ListaFragment.listaAlumnos.indexOf(alumno)), tag);
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
    public void ListViewItemPulsado(Alumno alumno) {
        mAlumnoSeleccionado = alumno;
        if(findViewById(R.id.flHuecoSecundario)==null) { //Modo Vertical
            //Se inicia otra actividad con los detalles del alumno ListViewItemPulsado.
            DetallesActivity.start(this, ListaFragment.listaAlumnos.indexOf(alumno));
        }else
            loadFragmentDetalles(R.id.flHuecoSecundario, alumno, alumno.getNombre());
    }

    @Override
    public void IconAddContactoPulsado() {
        //Abre la actividad de crearAlumnos cuando hace click en lblNoHayAlumnos del emptyView de la ListView.
        AgregarContactoActivity.startForResult(this, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Elimina los items de menú sobrantes cuando esta en postrait o no hay seleccionado ningún alumno.
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || mAlumnoSeleccionado==null){
            menu.removeItem(R.id.itemLlamar);
            menu.removeItem(R.id.itemLlamar);
            menu.removeItem(R.id.itemEditar);
            menu.removeItem(R.id.itemEditar);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAdd:
                AgregarContactoActivity.startForResult(this, -1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_ALUMNO, ListaFragment.listaAlumnos.indexOf(mAlumnoSeleccionado));
        outState.putParcelable(STATE_ALUMNO, mAlumnoSeleccionado);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(ListaFragment.listaAlumnos.size()>0) {
            mAlumnoSeleccionado = ListaFragment.listaAlumnos.get(savedInstanceState.getInt(STATE_ALUMNO));
            //Cuando vuelve de DetallesActivity y la pantalla se encuentra apaisada, marcará el item de la lista que estaba observandose
            //anteriormente en DetallesActivity.
            if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (mAlumnoSeleccionado != null)
                    loadFragmentDetalles(R.id.flHuecoSecundario, mAlumnoSeleccionado, mAlumnoSeleccionado.getNombre());
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Si se ha creado un alumno correctamente.
        if(resultCode==RESULT_OK)
            if(requestCode==AgregarContactoActivity.MODO_CREAR)
               refrescarLista();

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void refrescarLista(){
        //Se busca el fragmento ya creado.
        ListaFragment lstFragment = (ListaFragment) mGestor.findFragmentByTag(TAG_FRG_LISTA);
        //Se le notifica que tiene que actualizarse, ya que contiene datos nuevos sobre el alumno creado.
        lstFragment.refrescarListView();
    }

    @Override
    protected void onResume() {
        //Si ha editado algun contacto, esto hará que cuando vuelva, esté la lista actualizada con los nuevos datos.
        refrescarLista();
        super.onResume();
    }

    @Override
    public void cambiarImgAvatar(String path) {
    }
}
