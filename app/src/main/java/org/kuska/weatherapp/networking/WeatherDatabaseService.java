package org.kuska.weatherapp.networking;

import android.support.annotation.NonNull;

import org.kuska.weatherapp.models.CurrentWeatherData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public interface WeatherDatabaseService {

    @GET("weather")
    Observable<CurrentWeatherData> getCurrentWeather(@NonNull @Query("lat") String lat, @NonNull @Query("lon") String lon);
}
