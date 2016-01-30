package es.example.alex.pr041proyectoretrofit;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public class Bdd extends Application {
    private static final String BASE_URL = "http://192.168.1.38:3000/";
    private static BddInterface servicio;

    public interface BddInterface{
        @GET("alumnos")
        Call<List<Alumno>> listarAlumnos();
        @POST("alumnos")
        Call<Alumno> crearAlumno(@Body Alumno alumno);
        @DELETE("alumnos/{id}")
        Call<Alumno> borrarAlumno(@Path("id") int idAlumno);
        @PUT("alumnos/{id}")
        Call<Alumno> actualizarAlumno(@Path("id") int id, @Body Alumno alumno);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Se crea el servicio.
        servicio = retrofit.create(BddInterface.class);



    }

    public static BddInterface getServicio() {
        return servicio;
    }
}
