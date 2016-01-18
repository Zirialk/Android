package es.example.alex.pr034gson;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlumnoAdapter.OnItemClickListener{

    private static final String URL_PHP = "https://dl.dropboxusercontent.com/u/67422/Android/json/datos.json";
    private AlumnoAdapter mAlumnoAdapter;
    private RecyclerView rvAlumnos;
    private ProgressBar prbCirculo;
    private Button btnLlamar;
    private Button btnBorrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        configurarRecyclerView();



    }


    private void configurarRecyclerView() {
        prbCirculo = (ProgressBar) findViewById(R.id.prbCirculo);
        rvAlumnos = (RecyclerView) findViewById(R.id.rvAlumnos);
        mAlumnoAdapter = new AlumnoAdapter(DB.getmDatos());
        mAlumnoAdapter.setOnItemClickListener(this);
        mAlumnoAdapter.setEmptyView(prbCirculo);
        rvAlumnos.setAdapter(mAlumnoAdapter);
        rvAlumnos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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

        switch (item.getItemId()){
            case R.id.action_search:
                prbCirculo.setVisibility(View.VISIBLE);
                new JsonAsyncTask().execute(URL_PHP);
                break;
        }

        return true;
    }

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {

    }




    class JsonAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            //Devuelve el JSON adquirido de la URL.
            return getWebPageContent(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            List<Alumno> listaAlumnos = getAlumnoFromJSON(s);

            for(Alumno a : listaAlumnos)
            //No se repiten los alumnos
                if(!mAlumnoAdapter.containsItem(a))
                    mAlumnoAdapter.addItem(a);

        }
    }


    private String getWebPageContent(String sUrl) {
        String contenido = "";
        HttpURLConnection conexion = null;
        try {
            // Se obtiene el objeto URL.
            URL url = new URL(sUrl);
            // Se abre la conexión.
             conexion = (HttpURLConnection) url.openConnection();
            // Se establece un tiempo máximo de lectura y de intento de conexión.
            conexion.setReadTimeout(10000); // milisegundos.
            conexion.setConnectTimeout(15000);
            // Se establece el método de conexión.
            conexion.setRequestMethod("GET");
            // Se indica que pretendemos leer del flujo de entrada.
            conexion.setDoInput(true);
            // Se realiza la conexión.
            //conexion.connect();
            // Si el código de respuesta es correcto.
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Se crea un lector que lee del flujo de entrada de la conexión.
                BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                // Se lee línea a línea y se agrega a la cadena contenido.
                String linea = lector.readLine();
                while (linea != null) {
                    contenido += linea;
                    linea = lector.readLine();
                }
                lector.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Tanto si se produce una excepción como si no.
            if (conexion != null) {
                // Se cierra la conexión.
                conexion.disconnect();
            }

        }
        // Se retorna el contenido.
        return contenido;
    }

    private List<Alumno> getAlumnoFromJSON(String json){
        Gson gson = new Gson();

        Type tipoListaAlumnos = new TypeToken<List<Alumno>>() { }.getType();

        return gson.fromJson(json, tipoListaAlumnos);
    }
}
