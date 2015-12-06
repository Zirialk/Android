package com.example.usuario.pr023listconfragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements ListaFragment.OnItemSelected, FragmentManager.OnBackStackChangedListener, DetallesFragment.CambiarImg{


    private static final String STATE_ALUMNO = "alumno";
    private static final String TAG_FRG_LISTA = "fgrLista";
    private static final String TAG_DETALLES = "fgrDetalles";
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
        //Desvinculará el fragmento detalles cuando esté en vertical, para eliminar sus items de menú.
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT )
            desvincularFragmento(TAG_DETALLES);
    }

    private void initViews() {
        loadFragmentLista(R.id.flHuecoPrincipal, TAG_FRG_LISTA);
    }
    private void loadFragmentLista(int idHueco,String tag) {
        FragmentTransaction transaction = mGestor.beginTransaction();

        //Solo se creará el fragmento si no estaba creado de antes.
        if (mGestor.findFragmentByTag(tag) == null) {
            transaction.replace(idHueco, ListaFragment.newInstance(), tag);
            transaction.addToBackStack("a");
        }else
            transaction.replace(idHueco, mGestor.findFragmentByTag(tag));
        transaction.commit();
    }

    private void loadFragmentDetalles(int idHueco, Alumno alumno, String tag){
        FragmentTransaction transaction = mGestor.beginTransaction();

        transaction.replace(idHueco, DetallesFragment.newInstance(ListaFragment.listaAlumnos.indexOf(alumno)), tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        transaction.commit();
    }

    private void desvincularFragmento(String tag){
        //Si está creado el fragmento con este TAG
        if( mGestor.findFragmentByTag(tag)!=null)
            //Lo elimina
            mGestor.beginTransaction().remove(mGestor.findFragmentByTag(tag)).commit();
    }

    @Override
    public void onBackStackChanged() {
        //Si ya no existe nada más en la cola del BackStack al presionar el botón atrás, se saldrá de la aplicación.
        if(mGestor.getBackStackEntryCount()==0)
            onBackPressed();
    }
    @Override
    public void listViewItemPulsado(Alumno alumno) {
        mAlumnoSeleccionado = alumno;
        //Marca como seleccionado el item de la lista que corresponde con el alumno.
        if(findViewById(R.id.flHuecoSecundario)==null) { //Modo Vertical
            //Se inicia otra actividad con los detalles del alumno listViewItemPulsado.
            DetallesActivity.start(this, ListaFragment.listaAlumnos.indexOf(alumno));
        }else
            loadFragmentDetalles(R.id.flHuecoSecundario, alumno, TAG_DETALLES);
    }

    @Override
    public void iconAddContactoPulsado() {
        //Abre la actividad de crearAlumnos cuando hace click en lblNoHayAlumnos del emptyView de la ListView.
        AgregarContactoActivity.startForResultCreando(this);
    }
    //Eliminará el fragmento detalles cuando la lista se haya quedado vacía
    @Override
    public void sinAlumnos() {
        desvincularFragmento(TAG_DETALLES);
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_ALUMNO, ListaFragment.listaAlumnos.indexOf(mAlumnoSeleccionado));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ListaFragment fgr = (ListaFragment) mGestor.findFragmentByTag(TAG_FRG_LISTA);
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getInt(STATE_ALUMNO)!=-1) {  //Si ha pulsado en algún alumno.
            mAlumnoSeleccionado = ListaFragment.listaAlumnos.get(savedInstanceState.getInt(STATE_ALUMNO));
            //Cuando vuelve de DetallesActivity y la pantalla se encuentra apaisada, marcará el item de la lista que estaba observandose
            //anteriormente en DetallesActivity.
            if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (mAlumnoSeleccionado != null){
                    //Marca como seleccionado el item de la lista del último alumno seleccionado.
                    fgr.seleccionarItemChecked(ListaFragment.listaAlumnos.indexOf(mAlumnoSeleccionado));
                    loadFragmentDetalles(R.id.flHuecoSecundario, mAlumnoSeleccionado, TAG_DETALLES);
                }
            }else
                //Deselecciona todos los items, para que cuando esté en vertical y se marquen alumnos para borrar, no salgan ya marcados algunos.
                fgr.deseleccionarItemsChecked();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Si se ha creado un alumno correctamente.
        if(resultCode==RESULT_OK){
            if (requestCode == AgregarContactoActivity.MODO_CREAR)
                refrescarLista();
        }

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
