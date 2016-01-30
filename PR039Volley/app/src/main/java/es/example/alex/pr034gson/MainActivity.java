package es.example.alex.pr034gson;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlumnoAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String URL_DATOS = "https://dl.dropboxusercontent.com/u/67422/Android/json/datos.json";
    private static final String STATE_LISTA = "lista";
    private SwipeRefreshLayout swlPanel;
    private AlumnoAdapter mAlumnoAdapter;
    private RecyclerView rvAlumnos;
    private RequestQueue colaPeticiones = App.getRequestQueue();
    private Parcelable mEstadoLista;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        /* Si queremos que se ejecute automáticamente inicialmente */
        if (savedInstanceState ==  null) {
            // Si hay conexión a Internet.
            if (isConnectionAvailable()) {
                swlPanel.post(new Runnable() {
                    @Override
                    public void run() {
                        swlPanel.setRefreshing(true);
                    }
                });
                // realizarPeticionJSON();
                realizarPeticionGson();
                //Si no hay conexión.
            } else {
                Toast.makeText(this,"No hay Conexión", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void initViews() {
        setupPanel();
        configurarRecyclerView();
    }

    private void setupPanel() {
        swlPanel = (SwipeRefreshLayout) findViewById(R.id.swlPanel);
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
        realizarPeticionGson();

    }

    private void configurarRecyclerView() {
        rvAlumnos = (RecyclerView) findViewById(R.id.rvAlumnos);
        mAlumnoAdapter = new AlumnoAdapter(DB.getmDatos());
        mAlumnoAdapter.setOnItemClickListener(this);
        rvAlumnos.setAdapter(mAlumnoAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAlumnos.setLayoutManager(mLinearLayoutManager);
        rvAlumnos.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                refresh();
                break;
        }

        return true;
    }

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {

    }
    //Añade una peticion JSON a la cola de peticiones
    private void realizarPeticionJson() {
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Se oculta el cirbulo de refresco
                swlPanel.setRefreshing(false);
                //Se obtiene el array de alumnos através del JSON
                List<ListItem> alumnos = procesarJson(response);
                //Se le pasa al adaptador el array de los alumnos para que sustituya el antiguo.
                cargarAlumnosEnLista(alumnos);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                swlPanel.setRefreshing(false);
            }
        };
        //Se crea la petición.
        JsonArrayRequest peticion = new JsonArrayRequest(URL_DATOS,listener, errorListener);
        //Se añade la petición a la cola de peticiones.
        colaPeticiones.add(peticion);
    }

    private List<ListItem> procesarJson(JSONArray result) {
        //Array de alumnos que se devolverá
        List<ListItem> alumnos = new ArrayList<>();
        try {
            //Por cada objeto Json del JSONArray
            for (int i = 0; i < result.length(); i++) {
                JSONObject alumnoJSON = result.getJSONObject(i);

                Alumno alumno = new Alumno();

                //Se configura el alumno con los datos del Objeto JSON.
                alumno.setNombre(alumnoJSON.getString(Alumno.KEY_NOMBRE));
                alumno.setDireccion(alumnoJSON.getString(Alumno.KEY_DIRECCION));
                alumno.setTelefono(alumnoJSON.getString(Alumno.KEY_TELEFONO));
                alumno.setCurso(alumnoJSON.getString(Alumno.KEY_CURSO));
                alumno.setRepetidor(alumnoJSON.getBoolean(Alumno.KEY_REPETIDOR));
                alumno.setEdad(alumnoJSON.getInt(Alumno.KEY_EDAD));
                alumno.setFoto(alumnoJSON.getString(Alumno.KEY_FOTO));
                //Se añade el alumno al array
                alumnos.add(alumno);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Se retorna el array.
        return alumnos;
    }
    // Añade a la cola de peticiones una petición Gson.
    private void realizarPeticionGson() {
        // Se crea el listener para la respuesta.
        Response.Listener<ArrayList<ListItem>> listener = new Response.Listener<ArrayList<ListItem>>() {

            @Override
            public void onResponse(ArrayList<ListItem> response) {
                // Se oculta el círculo de progreso.
                swlPanel.setRefreshing(false);
                // Se cargan los alumnos en la lista.
                cargarAlumnosEnLista(response);
            }
        };
        // Se crea el listener de error.
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                swlPanel.setRefreshing(false);
            }
        };
        // Se crea la petición.
        Gson gson = new Gson();
        Type tipo = new TypeToken<List<Alumno>>() {
        }.getType();

        GsonArrayRequest<ArrayList<ListItem>> peticion = new GsonArrayRequest<>(
                Request.Method.GET, URL_DATOS, tipo, listener, errorListener,
                gson);
        // Se añade la petición a la cola de Volley.
        colaPeticiones.add(peticion);
    }


    private void cargarAlumnosEnLista(List<ListItem> alumnos){
        mAlumnoAdapter.swapData(alumnos);
    }


    // Retorna si hay conexión a la red o no.
    private boolean isConnectionAvailable() {
        // Se obtiene del gestor de conectividad la información de red.
        ConnectivityManager gestorConectividad = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return (infoRed != null && infoRed.isConnected());
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del RecyclerView.
        mEstadoLista = mLinearLayoutManager.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Se obtiene el estado anterior de la lista.
        mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se retaura el estado de la lista.
        if (mEstadoLista != null) {
            mLinearLayoutManager.onRestoreInstanceState(mEstadoLista);
        }
    }











}
