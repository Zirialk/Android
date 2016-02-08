package es.example.alex.pr047practica2trimestre;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AlumnosFragment.OnAlumnoSelectedListener{
    //Variables
    private FragmentManager mGestorFragmento;
    private static final String TAG_FRM_CONTENIDO = "Contenido";
    //Vistas
    private DrawerLayout panelNavegacion;
    private FrameLayout frmContenido;
    private Fragment frgActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        configFragments();

    }

    private void configFragments() {
        frmContenido = (FrameLayout) findViewById(R.id.frmContenido);
        mGestorFragmento = getSupportFragmentManager();
        //Si es la primera vez que se entra a la aplicación.
        if(frgActual == null){
            frgActual = new AlumnosFragment();
            mGestorFragmento.beginTransaction().replace(R.id.frmContenido, frgActual, TAG_FRM_CONTENIDO).commit();
        }

    }
    @Override
    public void onAlumnoSelected(Alumno obra, int position) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                break;
        }

        return true;
    }



}
