package org.kuska.weatherapp.injection;


import org.kuska.weatherapp.feature.home.WeatherActivity;
import org.kuska.weatherapp.util.rx.AppSchedulers;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
@Singleton
@Component(modules = {
        NetworkModule.class,
        AppModule.class,})
public interface AppComponent {
    void inject(WeatherActivity homeActivity);

    void inject(AppSchedulers appSchedulers);
}
