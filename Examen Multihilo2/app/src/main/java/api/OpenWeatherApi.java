package api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class OpenWeatherApi {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static OpenWeatherApi mInstance;
    private final RetrofitInterface service;

    public interface RetrofitInterface{
        @GET("weather")
        Call<Respuesta> getTraduccion(@Query("appid") String appid ,@Query("q") String localidad, @Query("units") String units, @Query("lang") String lang);
    }

    private OpenWeatherApi(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(RetrofitInterface.class);
    }

    public static synchronized OpenWeatherApi getmInstance() {
        if(mInstance == null)
            mInstance = new OpenWeatherApi();

        return mInstance;
    }
    public RetrofitInterface getServicio(){
        return service;
    }

}
