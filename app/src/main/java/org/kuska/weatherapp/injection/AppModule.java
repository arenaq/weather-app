package org.kuska.weatherapp.injection;

import org.kuska.weatherapp.util.rx.AppSchedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
@Module
public class AppModule {
    @Provides
    @Singleton
    AppSchedulers provideAppSchedulers() {
        return AppSchedulers.DEFAULT;
    }
}
