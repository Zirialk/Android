package com.example.aleja.practica2.actividades;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.UiThread;
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
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.aleja.practica2.bdd.DAO;
import com.example.aleja.practica2.fragmentos.EditorFragment;
import com.example.aleja.practica2.fragmentos.TutoriaIndividualFragment;
import com.example.aleja.practica2.fragmentos.VisitasFragment;
import com.example.aleja.practica2.modelos.Alumno;
import com.example.aleja.practica2.R;
import com.example.aleja.practica2.fragmentos.AlumnosFragment;
import com.example.aleja.practica2.modelos.Visita;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements AlumnosFragment.OnAlumnoSelectedListener,VisitasFragment.OnVisitaSelectedListener, NavigationView.OnNavigationItemSelectedListener {
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
        //DAO.getInstance(this).createAlumno(new Alumno("Pepe", "956", "aa@g", "Empresa", "Tutor", "horario", "direccion", ""));
        //DAO.getInstance(this).createVisita(new Visita(4,new Date(),new Date(), new Date(), "Tio sin foto"));
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
        String tag = "";

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
                frgActual = new VisitasFragment();
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
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawers();
        else
            super.onBackPressed();
    }

    public static void translateFab(final FloatingActionButton fab, final float toPosX, final float toPosY, final Drawable drawable){
        //Oculta el FAB
        fab.animate().scaleX(0).scaleY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //Mueve el fab a la posicion deseada.
                fab.animate().translationX(toPosX).translationY(toPosY).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        //Le cambia el icono
                        fab.setImageDrawable(drawable);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        //Lo hace reaparecer
                        fab.animate().scaleX(1).scaleY(1);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });



            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }
    //Click en una visita de la lista de fragmentVisitas
    @Override
    public void onVisitaSelected(Visita visita, int position) {

    }
    //Click del fragmento de lista de alumnos.
    @Override
    public void onAlumnoSelected(Alumno alumno, int position) {
        frgActual = TutoriaIndividualFragment.newInstance(alumno);
        mGestorFragmento.beginTransaction().replace(R.id.frmContenido, frgActual, TAG_FRG_EDITOR).commit();
    }
}
