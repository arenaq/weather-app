package org.kuska.weatherapp.feature.home;

import org.kuska.weatherapp.models.CurrentWeatherData;
import org.kuska.weatherapp.networking.NetworkError;
import org.kuska.weatherapp.networking.WeatherDataManager;
import org.kuska.weatherapp.util.rx.AppSchedulers;
import org.kuska.weatherapp.util.rx.RxPresenter;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class WeatherPresenter extends RxPresenter<WeatherView> {
    private final WeatherDataManager weatherDataManager;
    private final AppSchedulers appSchedulers;

    @Inject
    public WeatherPresenter(final WeatherDataManager weatherDataManager, final AppSchedulers appSchedulers) {
        this.weatherDataManager = weatherDataManager;
        this.appSchedulers = appSchedulers;
    }

    public void getCurrentWeather(double latitude, double longitude) {
        final WeatherView weatherView = getView();
        if (weatherView == null) {
            return;
        }

        weatherView.clearData();
        weatherView.showProgressBar();

        addSubscription(weatherDataManager.getCurrentWeather(Double.toString(latitude), Double.toString(longitude))
        .subscribeOn(appSchedulers.background())
        .observeOn(appSchedulers.ui())
        .subscribe(new Subscriber<CurrentWeatherData>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                weatherView.hideProgressBar();
                weatherView.showError(new NetworkError(e).getAppErrorMessage());
            }

            @Override
            public void onNext(CurrentWeatherData currentWeatherData) {
                weatherView.hideProgressBar();
                weatherView.setData(currentWeatherData);
            }
        }));
    }

}
