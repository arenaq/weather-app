package org.kuska.weatherapp.models.currentweatherdata;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class Main {
    private Double temp;
    private Integer humidity;
    private Double pressure;
    private Double tempMin;
    private Double tempMax;

    public Double getTemp() {
        return temp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }
}
