package org.kuska.weatherapp.injection;

import org.kuska.weatherapp.BuildConfig;
import org.kuska.weatherapp.networking.WeatherDataManager;
import org.kuska.weatherapp.networking.WeatherDatabaseService;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
@Module
public class NetworkModule {
    File cacheFile;

    public NetworkModule(File cacheFile) {
        this.cacheFile = cacheFile;
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        Cache cache = null;
        try {
            cache = new Cache(cacheFile, 10 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* TODO Consider moving elsewhere. */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();

                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("APPID", BuildConfig.API_OPENWEATHERMAP_KEY)
                                .build();

                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .removeHeader("Pragma")
                                .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                                .url(url)
                                .build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        return response;
                    }
                })
                .cache(cache)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_OPENWEATHERMAP_BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public WeatherDatabaseService providesWeatherDatabaseService(Retrofit retrofit) {
        return retrofit.create(WeatherDatabaseService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public WeatherDataManager providesWeatherDataManager(WeatherDatabaseService weatherDatabaseService) {
        return new WeatherDataManager(weatherDatabaseService);
    }

}
