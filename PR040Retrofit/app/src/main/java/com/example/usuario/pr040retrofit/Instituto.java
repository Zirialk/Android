package com.example.usuario.pr040retrofit;

import android.app.Application;



import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class Instituto extends Application{

    private static final String BASE_URL = "http://192.168.1.38:3000/";
    private static InstitutoInterface servicio;

    public interface InstitutoInterface{
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
        OkHttpClient cliente = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Se crea el servicio.
        servicio = retrofit.create(InstitutoInterface.class);



    }
    public static InstitutoInterface getServicio() {
        return servicio;
    }


}
