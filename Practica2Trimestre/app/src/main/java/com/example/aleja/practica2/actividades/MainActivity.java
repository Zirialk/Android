package com.example.aleja.practica2.actividades;

import android.animation.Animator;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.aleja.practica2.fragmentos.EditorFragment;
import com.example.aleja.practica2.modelos.Alumno;
import com.example.aleja.practica2.R;
import com.example.aleja.practica2.fragmentos.AlumnosFragment;

public class MainActivity extends AppCompatActivity implements AlumnosFragment.OnAlumnoSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    //Variables
    private FragmentManager mGestorFragmento;
    private static final String TAG_FRG_LISTA_ALUMNOS = "Alumnos";
    private static final String TAG_FRG_EDITOR = "Editor";
    //Vistas
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
       // DAO.getInstance(this).createAlumno(new Alumno("Alejandro", "956", "aa@g", "Empresa", "Tutor", "horario", "direccion", "http://lorempixel.com/g/200/200/"));
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
        if(mGestorFragmento.findFragmentByTag(TAG_FRG_LISTA_ALUMNOS) ==null)
            frgActual = new AlumnosFragment();
        else
            frgActual = mGestorFragmento.findFragmentByTag(TAG_FRG_LISTA_ALUMNOS);

        mGestorFragmento.beginTransaction().replace(R.id.frmContenido, frgActual, TAG_FRG_LISTA_ALUMNOS).commit();


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
    //Click del fragmento de lista de alumnos.
    @Override
    public void onAlumnoSelected(Alumno alumno, int position) {
        frgActual = EditorFragment.newInstance(alumno);
        mGestorFragmento.beginTransaction().replace(R.id.frmContenido, frgActual, TAG_FRG_EDITOR).commit();
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
        String tag = TAG_FRG_LISTA_ALUMNOS;

        //Acciones de los elementos de la navigation drawer.
        switch (item.getItemId()){
            case R.id.nav_nuevo_alumno:
                frgActual = new EditorFragment();
                tag = TAG_FRG_EDITOR;
                break;
            case R.id.nav_tutorias:
                frgActual = new AlumnosFragment();
                tag = TAG_FRG_LISTA_ALUMNOS;
                break;
            case R.id.nav_prox_visitas:
                break;
            case R.id.nav_configuracion:
                break;
            case R.id.nav_acerca:
                break;
        }
        //Reemplaza el fragmento actual por el seleccionado de la navigation drawer.
        mGestorFragmento.beginTransaction().replace(R.id.frmContenido, frgActual, tag).commit();
        drawer.closeDrawers();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawers();
        else
            super.onBackPressed();
    }
}
