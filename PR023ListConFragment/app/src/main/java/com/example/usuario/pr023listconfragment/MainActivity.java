package com.example.usuario.pr023listconfragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements ListaFragment.OnItemSelected, FragmentManager.OnBackStackChangedListener{


    private static final String STATE_ALUMNO = "alumno";
    private static final int ACTIVITY_CREAR = 123;
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
        }else
            loadFragmentDetalles(R.id.flHuecoSecundario, alumno, alumno.getNombre());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Elimina el item de menú cuando esta en postrait
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
             menu.removeItem(R.id.itemLlamar);

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAdd:
                AgregarContactoActivity.startForResult(this, ACTIVITY_CREAR);
                break;

        }
        return super.onOptionsItemSelected(item);
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
            if(mAlumnoSeleccionado !=null)
                loadFragmentDetalles(R.id.flHuecoSecundario, mAlumnoSeleccionado, mAlumnoSeleccionado.getNombre());

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
            if(requestCode==ACTIVITY_CREAR){
                ListaFragment lstFragment = (ListaFragment) mGestor.findFragmentByTag(TAG_FRG_LISTA);
                lstFragment.getAdaptador().notifyDataSetChanged();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
