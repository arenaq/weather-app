package org.kuska.weatherapp.feature.home;

import org.kuska.weatherapp.models.CurrentWeatherData;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public interface WeatherView {
    void showProgressBar();

    void hideProgressBar();

    void showError(String errorMessage);

    void setData(CurrentWeatherData currentWeatherData);

    void clearData();
}
