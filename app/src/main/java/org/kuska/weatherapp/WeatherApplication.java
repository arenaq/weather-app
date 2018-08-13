package org.kuska.weatherapp;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Mapbox.getInstance(getApplicationContext(), BuildConfig.MAPBOX_KEY);
    }
}
