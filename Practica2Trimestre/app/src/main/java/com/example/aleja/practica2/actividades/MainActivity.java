package com.example.aleja.practica2.actividades;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.aleja.practica2.fragmentos.EditorFragment;
import com.example.aleja.practica2.fragmentos.TutoriaIndividualFragment;
import com.example.aleja.practica2.fragmentos.VisitasFragment;
import com.example.aleja.practica2.modelos.Alumno;
import com.example.aleja.practica2.R;
import com.example.aleja.practica2.fragmentos.AlumnosFragment;
import com.example.aleja.practica2.utils.Constantes;


public class MainActivity extends AppCompatActivity implements AlumnosFragment.OnAlumnoSelectedListener, NavigationView.OnNavigationItemSelectedListener {


    //Variables
    private FragmentManager mGestorFragmento;
    private static final String STATE_TAG = "tagState";
    private static final String TAG_FRG_LISTA_ALUMNOS = "Alumnos";
    private static final String TAG_FRG_EDITOR = "Editor";
    private static final String TAG_FRG_TUTORIA_INDIVIDUAL = "Tag tutoría individual";
    private static final String TAG_FRG_VISITAS = "Visitas";
    private static final int RC_PREFERENCES = 444;
    private static final String BACKSTACK = "backstack";
    //Vistas
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
        mGestorFragmento = getSupportFragmentManager();
        if(savedInstanceState != null)
            frgActual = mGestorFragmento.findFragmentByTag(savedInstanceState.getString(STATE_TAG));
        initViews();

    }


    private void initViews() {
        configNavigation();
        configFab();
        if(frgActual == null)
            cargarFragmentoInicio();

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

    private void cargarFragmentoInicio() {
        //Obtiene de las preferencias, cual será el fragmento cargado cuando se abra la aplicación.
        String prefEscogida = PreferenceManager.getDefaultSharedPreferences(this).getString(Constantes.PREF_FRAGMENTO_INICIAL, getString(R.string.itemTutorias));
        switch (prefEscogida){
            case Constantes.FRG_INI_TUTORIAS:
                cargarListaAlumnos();
                break;
            case Constantes.FRG_INI_PROX_VISITAS:
                cargarProxVisitas();
                break;
        }

    }
    private void cargarListaAlumnos(){
        toolbar.setTitle(R.string.labelCrearAlumno);
        //Si es la primera vez que se entra a la aplicación creará un nuevo fragmento de lista de alumnos.
        if(mGestorFragmento.findFragmentByTag(TAG_FRG_LISTA_ALUMNOS) == null)
            frgActual = new AlumnosFragment();
        else
            frgActual = mGestorFragmento.findFragmentByTag(TAG_FRG_LISTA_ALUMNOS);

        mGestorFragmento.beginTransaction().replace(R.id.frmContenido, frgActual, TAG_FRG_LISTA_ALUMNOS).commit();
    }
    private void cargarProxVisitas(){
        toolbar.setTitle(R.string.labelProxVisitas);
        frgActual = new VisitasFragment();
        mGestorFragmento.beginTransaction().replace(R.id.frmContenido, frgActual, TAG_FRG_VISITAS).commit();
    }

    private void configFab() {
        fab = (FloatingActionButton)findViewById(R.id.fab);
        //Dependiendo del fragmento que esté cargado en el framelayout, la acción del Fab será diferente.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = mGestorFragmento.findFragmentById(R.id.frmContenido);
                //
                if (frg instanceof TutoriaIndividualFragment) {
                    int currentPage = ((TutoriaIndividualFragment) frgActual).getCurrentPage();
                    if (currentPage == 0)
                        ((EditorFragment) ((TutoriaIndividualFragment) frg).getItem(currentPage)).buscarFotoEnGaleria();
                        //Si se encuentra en el tab de Visitas, al presionar el fab, se podrá crear una visita nueva.
                    else if (currentPage == 1) {
                        Intent intent = new Intent(MainActivity.this, CreadorVisitaActivity.class);
                        intent.putExtra(CreadorVisitaActivity.INTENT_ID_ALUMNO, ((TutoriaIndividualFragment) frgActual).getIdAlumno());
                        startActivityForResult(intent, CreadorVisitaActivity.RC_CREADOR_VISITA);
                    }
                    //Abre el creador de alumnos
                } else if (frg instanceof AlumnosFragment) {
                    mGestorFragmento.popBackStack();
                    frgActual = new EditorFragment();
                    FragmentTransaction trans = mGestorFragmento.beginTransaction();
                    trans.addToBackStack(BACKSTACK);
                    trans.replace(R.id.frmContenido, frgActual, TAG_FRG_EDITOR).commit();
                } else if (frg instanceof EditorFragment) {
                    ((EditorFragment) frg).buscarFotoEnGaleria();
                }
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
    public boolean onNavigationItemSelected(MenuItem item) {
        String tag = "";

        FragmentTransaction trans = mGestorFragmento.beginTransaction();

        //Acciones de los elementos de la navigation drawer.
        switch (item.getItemId()){
            case R.id.nav_nuevo_alumno:
                toolbar.setTitle(R.string.labelCrearAlumno);
                frgActual = new EditorFragment();

                //Permitirá volver a la lista dandole al botón de atrás.
                trans.addToBackStack(BACKSTACK);
                tag = TAG_FRG_EDITOR;
                break;
            case R.id.nav_tutorias:
                frgActual = new AlumnosFragment();
                toolbar.setTitle(R.string.labelAlumnos);
                tag = TAG_FRG_LISTA_ALUMNOS;
                break;
            case R.id.nav_prox_visitas:
                toolbar.setTitle(R.string.labelProxVisitas);
                frgActual = new VisitasFragment();
                tag = TAG_FRG_VISITAS;
                break;
            case R.id.nav_configuracion:
                drawer.closeDrawers();
                startActivityForResult(new Intent(this, PreferenceActivity.class), RC_PREFERENCES);
                //Se sale del método ya que no necesita cargar ningún fragmento.
                return true;
            case R.id.nav_acerca:
                drawer.closeDrawers();
                mostrarAcercaDe();
                return true;

        }
        drawer.closeDrawers();
        //No permite que se vuelva a cargar el mismo fragmento que ya está cargado.
        if(!mGestorFragmento.findFragmentById(R.id.frmContenido).getTag().equals(tag)){
            //Resetea la cola de fragmentos, para que no se hagan un lio.
            mGestorFragmento.popBackStack();
            fab.show();
            //Reemplaza el fragmento actual por el seleccionado de la navigation drawer.
            trans.replace(R.id.frmContenido, frgActual, tag).commit();
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        //Si el navigationDrawer está abierto lo cierra sino ejecuta un onBackPressed normal.
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawers();
        else{
            super.onBackPressed();
            MainActivity.translateFab(fab, 0, 0, R.drawable.ic_add);
        }
    }

    //Se encarga de mover el Fab ocultandolo al principio y mostrandolo cuando ha llegado al destino.
    public static void translateFab(final FloatingActionButton fab, final float toPosX, final float toPosY, final int idDrawable){
        if(fab != null)
            fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(final FloatingActionButton fab) {
                    super.onHidden(fab);
                    fab.setImageResource(idDrawable);
                    fab.animate().translationX(toPosX).translationY(toPosY).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {  }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fab.show();
                        }
                        @Override
                        public void onAnimationCancel(Animator animation) {}
                        @Override
                        public void onAnimationRepeat(Animator animation) {}
                    });
                }
            });
    }


    //Click del fragmento de lista de alumnos.
    @Override
    public void onAlumnoSelected(Alumno alumno) {
        frgActual = TutoriaIndividualFragment.newInstance(alumno);

        FragmentTransaction trans  = mGestorFragmento.beginTransaction();
        trans.addToBackStack(BACKSTACK);
        trans.replace(R.id.frmContenido, frgActual, TAG_FRG_TUTORIA_INDIVIDUAL).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                //Cuando se seleccione foto de la galería a través del fab que contiene el MainActivity, al ejecutarlo desde el MainActivity
                //en vez de ejecutarse el onActivityResult del fragmento, se ejecutará este.
                case EditorFragment.RC_SELECCIONAR_FOTO:
                    //Se conseguirá el fragmento que lanzó el startActivityForResult y se ejecutará su onActivityResult pasandole los parámetros de este.
                    TutoriaIndividualFragment tutoriaFrg = (TutoriaIndividualFragment) mGestorFragmento.findFragmentByTag(TAG_FRG_TUTORIA_INDIVIDUAL);
                    if(tutoriaFrg != null)
                        tutoriaFrg.getItem(tutoriaFrg.getCurrentPage()).onActivityResult(requestCode, resultCode, data);
                    else
                        mGestorFragmento.findFragmentByTag(TAG_FRG_EDITOR).onActivityResult(requestCode,resultCode,data);
                    break;
                case CreadorVisitaActivity.RC_CREADOR_VISITA:
                    ((VisitasFragment) ((TutoriaIndividualFragment)frgActual).getItem(1)).actualizarListaPersonal();
                    break;

                case RC_PREFERENCES:
                    Fragment frg = mGestorFragmento.findFragmentById(R.id.frmContenido);
                    //Si está el fragmento de visitas próximas en primer plano se actualizará con el nuevo orden.
                    if(frg instanceof VisitasFragment)
                        ((VisitasFragment)frg).actualizarProxVisitas();
                    //Si el fragmento de visitas personales del alumno en primer plano se actualizaran con ese orden
                    else if(frg instanceof TutoriaIndividualFragment){
                        TutoriaIndividualFragment tFrag =(TutoriaIndividualFragment) frg;
                        if(tFrag.getItem(tFrag.getCurrentPage()) instanceof VisitasFragment)
                            ((VisitasFragment) tFrag.getItem(tFrag.getCurrentPage())).actualizarListaPersonal();
                    }
                    break;
            }
        }
    }

    private void mostrarAcercaDe(){
        new AlertDialog.Builder(this).setTitle(getString(R.string.tituloAcercaDe))
                .setMessage(getString(R.string.mensajeAcercaDe))
                .setPositiveButton(getString(R.string.btnPositiveAcercaDe), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_TAG, frgActual.getTag());
        super.onSaveInstanceState(outState);
    }
}
