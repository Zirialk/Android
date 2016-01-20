package com.example.usuario.pr034get;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATOS = "https://dl.dropboxusercontent.com/u/67422/Android/json/datos.json";
    private TextView lblResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

    }

    private void initViews() {
        lblResultado = (TextView) findViewById(R.id.lblResultado);
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


        switch (item.getItemId()){
            case R.id.action_search:
                new TareaGet().execute(URL_DATOS);
                break;
            case R.id.action_post:
                new TareaPost().execute("Baldomero","http://www.informaticasaladillo.es/echo.php");
                break;
        }


        return true;
    }


    class TareaGet extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String contenido="";
            HttpURLConnection conexion=null;

            try {
                URL url = new URL(params[0]);
                //Se abre la conexión
                conexion = (HttpURLConnection) url.openConnection();
                //Se establece el tiempo mínimo de lectura y el intento de conexión.
                conexion.setReadTimeout(10000);
                conexion.setConnectTimeout(15000);
                //Se establece el método de conexión.
                conexion.setRequestMethod("GET");
                //Se indica que pretendemos leer del flujo de entrada
                conexion.setDoInput(true);

                if(conexion.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                    //Se lee linea a linea y se agrega a la cadena contenido
                    String linea;

                    while((linea= lector.readLine())!=null){
                        contenido+=linea;
                    }
                    lector.close();
                }
                return contenido;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(conexion != null)
                    conexion.disconnect();
            }
            return contenido;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Type tipoAlumno = new TypeToken<List<Alumno>>(){

            }.getType();

            List<Alumno> listaAlumnos = gson.fromJson(s,tipoAlumno);

            lblResultado.setText(s);
        }
    }

    //POST
    class TareaPost extends AsyncTask<String,Void,String>{
        // Constantes.
        private static final String KEY_NOMBRE = "nombre";
        private static final String KEY_FECHA = "fecha";


        @Override
        protected String doInBackground(String... params) {
            String resultado = "";
            // Se obtiene el nombre a buscar.
            String nombre = params[0];
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

            try {
                // Se obtiene la url de búsqueda.
                URL url = new URL(params[1]);
                // Se crea la conexión.
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                // Se establece el método de conexión.
                conexion.setRequestMethod("POST");

                //Se indica que pretendemos escribir y leer
                conexion.setDoOutput(true);
                conexion.setDoInput(true);

                PrintWriter escritor = new PrintWriter(conexion.getOutputStream());

                escritor.write(KEY_NOMBRE + "=" + URLEncoder.encode(nombre, "UTF-8"));
                escritor.write("&" + KEY_FECHA + "=" + URLEncoder.encode(formateador.format(new Date()), "UTF-8"));

                //Se envian los datos
                escritor.flush();
                escritor.close();

                if(conexion.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                    String linea = lector.readLine();
                    while(linea != null){
                        resultado+=linea;
                        linea = lector.readLine();
                    }
                    lector.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



            return resultado;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

        }
    }
}
