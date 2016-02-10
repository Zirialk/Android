package com.example.aleja.practica2trimestre;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AlumnosFragment.OnAlumnoSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    //Variables
    private FragmentManager mGestorFragmento;
    private static final String TAG_FRM_CONTENIDO = "Contenido";
    //Vistas
    private DrawerLayout panelNavegacion;
    private FrameLayout frmContenido;
    private Fragment frgActual;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout nvgDrawer;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        configNavigation();
        configFragments();
        configFab();
    }

    private void configNavigation() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //Listener del menú del navigationDrawer
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configFragments() {
        frmContenido = (FrameLayout) findViewById(R.id.frmContenido);
        mGestorFragmento = getSupportFragmentManager();
        //Si es la primera vez que se entra a la aplicación creará un nuevo fragmento de lista de alumnos.
        if(mGestorFragmento.findFragmentByTag(TAG_FRM_CONTENIDO) ==null)
            frgActual = new AlumnosFragment();
        else
            frgActual = mGestorFragmento.findFragmentByTag(TAG_FRM_CONTENIDO);

        mGestorFragmento.beginTransaction().replace(R.id.frmContenido, frgActual, TAG_FRM_CONTENIDO).commit();


    }

    private void configFab() {
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onAlumnoSelected(Alumno obra, int position) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Acciones de los elementos de la navigation drawer.
        switch (item.getItemId()){
            case R.id.nav_iniciar_sesion:
                break;
            case R.id.nav_tutorias:
                break;
            case R.id.nav_prox_visitas:
                break;
            case R.id.nav_configuracion:
                break;
            case R.id.nav_acerca:
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);

    }
}
