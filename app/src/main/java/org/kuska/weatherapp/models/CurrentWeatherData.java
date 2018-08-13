package org.kuska.weatherapp.models;

import org.kuska.weatherapp.models.currentweatherdata.Clouds;
import org.kuska.weatherapp.models.currentweatherdata.Coordinates;
import org.kuska.weatherapp.models.currentweatherdata.Main;
import org.kuska.weatherapp.models.currentweatherdata.Rain;
import org.kuska.weatherapp.models.currentweatherdata.Sys;
import org.kuska.weatherapp.models.currentweatherdata.Weather;
import org.kuska.weatherapp.models.currentweatherdata.Wind;

import java.util.List;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class CurrentWeatherData {
    private Coordinates coord;
    private Sys sys;
    private List<Weather> weather = null;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private Integer dt;
    private Integer id;
    private String name;
    private Integer cod;

    public Coordinates getCoord() {
        return coord;
    }

    public Sys getSys() {
        return sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCod() {
        return cod;
    }
}
