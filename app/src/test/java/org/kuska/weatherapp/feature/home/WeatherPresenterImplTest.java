package org.kuska.weatherapp.feature.home;

import org.junit.Before;
import org.junit.Test;
import org.kuska.weatherapp.models.CurrentWeatherData;
import org.kuska.weatherapp.networking.WeatherDataManager;
import org.kuska.weatherapp.util.rx.TestAppSchedulers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class WeatherPresenterImplTest {
    @Mock WeatherDataManager weatherDataManager;
    @Mock WeatherView weatherView;

    private WeatherPresenter weatherPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        weatherPresenter = new WeatherPresenter(weatherDataManager, new TestAppSchedulers());
        weatherPresenter.attachView(weatherView);
    }

    @Test
    public void getCurrentWeather_NetworkError_showError() {
        when(weatherDataManager.getCurrentWeather(any(String.class), any(String.class))).thenReturn(Observable.<CurrentWeatherData>error(new Exception()));

        weatherPresenter.getCurrentWeather(50.4, 14.3);

        verify(weatherView, times(1)).clearData();
        verify(weatherView, times(1)).showProgressBar();
        verify(weatherView, times(1)).hideProgressBar();
        verify(weatherView, times(1)).showError(any(String.class));
        verify(weatherView, never()).setData(any(CurrentWeatherData.class));
    }

    @Test
    public void getCurrentWeather_ReceiveWeatherData_setData() {
        CurrentWeatherData currentWeatherData = new CurrentWeatherData();
        when(weatherDataManager.getCurrentWeather(any(String.class), any(String.class))).thenReturn(Observable.just(currentWeatherData));

        weatherPresenter.getCurrentWeather(50.4, 14.3);

        verify(weatherView, times(1)).clearData();
        verify(weatherView, times(1)).showProgressBar();
        verify(weatherView, times(1)).hideProgressBar();
        verify(weatherView, times(1)).setData(currentWeatherData);
        verify(weatherView, never()).showError(any(String.class));
    }
}