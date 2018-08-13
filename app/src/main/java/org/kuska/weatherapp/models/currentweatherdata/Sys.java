package org.kuska.weatherapp.models.currentweatherdata;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class Sys {
    private String country;
    private Integer sunrise;
    private Integer sunset;

    public String getCountry() {
        return country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }
}
