package es.example.alex.pr041proyectoretrofit;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AlumnoAdapter.OnItemClickListener {

    private static final String STATE_ALUMNOS = "alumnos";
    private SwipeRefreshLayout swlPanel;
    private RecyclerView rvAlumnos;
    private AlumnoAdapter mAlumnoAdapter;
    private ArrayList<Alumno> alumnos = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        if(savedInstanceState == null){
            //Solo cargará de la Bdd automaticamente la primera vez que se crea la actividad.
            listarAlumnos();
        }else{
            ArrayList<Alumno> a = savedInstanceState.getParcelableArrayList(STATE_ALUMNOS);
            mAlumnoAdapter.replaceAlumnos(a);
        }


    }

    private void initViews() {
        setupRefresh();
        configurarRecyclerView();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Anima la pulsación del Floating Button.
                fab.animate().setDuration(100).scaleX(-1).scaleY(-1).start();
                //Abre creador de Alumnos.
                CreadorActivity.startCreadorForResult(MainActivity.this);

            }
        });
    }

    private void setupRefresh() {
        swlPanel = (SwipeRefreshLayout)findViewById(R.id.swlPanel);
        swlPanel.setOnRefreshListener(this);
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        swlPanel.setRefreshing(true);
        //Cargar Alumnos de la BDD
        listarAlumnos();

    }


    private void configurarRecyclerView() {
        rvAlumnos = (RecyclerView) findViewById(R.id.rvAlumnos);
        //DAO.getDAO(this).createAlumno(new Alumno("h","2a","95",1, "jimena", false, "aa"));
        mAlumnoAdapter = new AlumnoAdapter(alumnos);
        mAlumnoAdapter.setOnItemClickListener(this);
        //mAlumnoAdapter.setEmptyView(prbCirculo);
        rvAlumnos.setAdapter(mAlumnoAdapter);
        rvAlumnos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAlumnos.setItemAnimator(new DefaultItemAnimator());
    }


    private void listarAlumnos(){
        mAlumnoAdapter.replaceAlumnos((ArrayList) DAO.getDAO(this).getAllAlumnos());
        swlPanel.setRefreshing(false);
    }

    public void makeToast(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //AlumnoAdapter click
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        CreadorActivity.startEditorForResult(this, alumno);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_ALUMNOS, alumnos);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //SE PUEDE ACTUALIZAR UN ALUMNO o ACTUALIZAR TODA LA LISTA EN CASO DE QUE LO QUISIERAMOS ORDENADO
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case CreadorActivity.MODO_CREAR:
                    //Se introduce a través del adaptador, el alumno creado en la actividad de creación.
                    mAlumnoAdapter.addAlumno((Alumno) data.getParcelableExtra(CreadorActivity.INTENT_ALUMNO_CREADO));
                    break;
                case CreadorActivity.MODO_EDICION:
                    Alumno alumno = data.getParcelableExtra(CreadorActivity.INTENT_ALUMNO_EDITADO);
                    mAlumnoAdapter.updateAlumno(alumno.getId() ,alumno);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
