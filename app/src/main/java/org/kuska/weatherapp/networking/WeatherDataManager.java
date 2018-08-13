package org.kuska.weatherapp.networking;

import android.support.annotation.NonNull;

import org.kuska.weatherapp.models.CurrentWeatherData;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class WeatherDataManager {
    private final WeatherDatabaseService weatherDatabaseService;

    public WeatherDataManager(WeatherDatabaseService weatherDatabaseService) {
        this.weatherDatabaseService = weatherDatabaseService;
    }

    public Observable<CurrentWeatherData> getCurrentWeather(@NonNull String lat, @NonNull String lon) {
        return weatherDatabaseService.getCurrentWeather(lat, lon)
                .subscribeOn(Schedulers.io());
    }
}
