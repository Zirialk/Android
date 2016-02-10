package com.example.usuario.pr049;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class TimeZoneAPI {

    private static TimeZoneAPI mInstance;
    private final ZonaHorarioInterface service;
    private static final String BASE_URL = "http://api.timezonedb.com/";

    private TimeZoneAPI(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(ZonaHorarioInterface.class);
    }

    public interface ZonaHorarioInterface{
        @GET("?format=json&key=143UZ6JHALZA")
        Call<DatosZonaPOJO> getHora(@Query("zone") String zone);
    }

    public static synchronized TimeZoneAPI getmInstance() {
        if(mInstance == null)
            mInstance = new TimeZoneAPI();

        return mInstance;
    }
    public ZonaHorarioInterface getServicio(){
        return service;
    }


}
