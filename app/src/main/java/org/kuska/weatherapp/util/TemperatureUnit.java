package org.kuska.weatherapp.util;

import android.content.SharedPreferences;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public abstract class TemperatureUnit {
    public static final String PREF_TEMPERATURE_UNIT = "org.kuska.weatherapp.temperature_unit";
    public static final int PREF_CELSIUS = 1;
    public static final int PREF_FAHRENHEIT = 2;
    public static final int PREF_KELVIN = 3;
    private static final String CELSIUS = "°C";
    private static final String FAHRENHEIT = "°F";
    private static final String KELVIN = "K";

    public abstract double getTemperature(double kelvin);
    public abstract String getUnitLabel();

    public static TemperatureUnit getInstance(SharedPreferences prefs) {
        // TODO remove prefs from this class
        Integer unit = prefs.getInt(PREF_TEMPERATURE_UNIT, 0);
        switch (unit) {
            case PREF_FAHRENHEIT: return new Fahrenheit();
            case PREF_KELVIN: return new Kelvin();
            case PREF_CELSIUS:
            default: return new Celsius();
        }
    }

    public static class Celsius extends TemperatureUnit {

        @Override
        public double getTemperature(double kelvin) {
            return kelvin - 273.15;
        }

        @Override
        public String getUnitLabel() {
            return CELSIUS;
        }
    }

    public static class Fahrenheit extends TemperatureUnit {

        @Override
        public double getTemperature(double kelvin) {
            return kelvin * 9 / 5 - 459.67;
        }

        @Override
        public String getUnitLabel() {
            return FAHRENHEIT;
        }
    }

    public static class Kelvin extends TemperatureUnit {

        @Override
        public double getTemperature(double kelvin) {
            return kelvin;
        }

        @Override
        public String getUnitLabel() {
            return KELVIN;
        }
    }
}
