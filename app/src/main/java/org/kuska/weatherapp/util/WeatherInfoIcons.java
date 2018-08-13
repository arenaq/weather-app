package org.kuska.weatherapp.util;

import org.kuska.weatherapp.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class WeatherInfoIcons {
    private static final Map<Integer, Integer> CONSTANT_MAP =
            Collections.unmodifiableMap(new HashMap<Integer, Integer>() {{
                put(200, R.drawable.wi_storm_showers);
                put(201, R.drawable.wi_storm_showers);
                put(202, R.drawable.wi_storm_showers);
                put(210, R.drawable.wi_storm_showers);
                put(211, R.drawable.wi_thunderstorm);
                put(212, R.drawable.wi_thunderstorm);
                put(221, R.drawable.wi_thunderstorm);
                put(230, R.drawable.wi_storm_showers);
                put(231, R.drawable.wi_storm_showers);
                put(232, R.drawable.wi_storm_showers);
                put(300, R.drawable.wi_sprinkle);
                put(301, R.drawable.wi_sprinkle);
                put(302, R.drawable.wi_sprinkle);
                put(310, R.drawable.wi_sprinkle);
                put(311, R.drawable.wi_sprinkle);
                put(312, R.drawable.wi_sprinkle);
                put(313, R.drawable.wi_sprinkle);
                put(314, R.drawable.wi_sprinkle);
                put(321, R.drawable.wi_sprinkle);
                put(500, R.drawable.wi_rain);
                put(501, R.drawable.wi_rain);
                put(502, R.drawable.wi_rain);
                put(503, R.drawable.wi_rain);
                put(504, R.drawable.wi_rain);
                put(511, R.drawable.wi_rain_mix);
                put(520, R.drawable.wi_showers);
                put(521, R.drawable.wi_showers);
                put(522, R.drawable.wi_showers);
                put(531, R.drawable.wi_showers);
                put(600, R.drawable.wi_snow);
                put(601, R.drawable.wi_snow);
                put(602, R.drawable.wi_snow);
                put(611, R.drawable.wi_sleet);
                put(612, R.drawable.wi_sleet);
                put(615, R.drawable.wi_rain_mix);
                put(616, R.drawable.wi_rain_mix);
                put(620, R.drawable.wi_rain_mix);
                put(621, R.drawable.wi_rain_mix);
                put(622, R.drawable.wi_rain_mix);
                put(701, R.drawable.wi_sprinkle);
                put(711, R.drawable.wi_smoke);
                put(721, R.drawable.wi_day_haze);
                put(731, R.drawable.wi_cloudy_gusts);
                put(741, R.drawable.wi_fog);
                put(751, R.drawable.wi_cloudy_gusts);
                put(761, R.drawable.wi_dust);
                put(762, R.drawable.wi_smog);
                put(771, R.drawable.wi_day_windy);
                put(781, R.drawable.wi_tornado);
                put(800, R.drawable.wi_day_sunny);
                put(801, R.drawable.wi_cloudy);
                put(802, R.drawable.wi_cloudy);
                put(803, R.drawable.wi_cloudy);
                put(804, R.drawable.wi_cloudy);
                put(900, R.drawable.wi_tornado);
                put(901, R.drawable.wi_hurricane);
                put(902, R.drawable.wi_hurricane);
                put(903, R.drawable.wi_snowflake_cold);
                put(904, R.drawable.wi_hot);
                put(905, R.drawable.wi_windy);
                put(906, R.drawable.wi_hail);
                put(951, R.drawable.wi_day_sunny);
                put(952, R.drawable.wi_cloudy_gusts);
                put(953, R.drawable.wi_cloudy_gusts);
                put(954, R.drawable.wi_cloudy_gusts);
                put(955, R.drawable.wi_cloudy_gusts);
                put(956, R.drawable.wi_cloudy_gusts);
                put(957, R.drawable.wi_cloudy_gusts);
                put(958, R.drawable.wi_cloudy_gusts);
                put(959, R.drawable.wi_cloudy_gusts);
                put(960, R.drawable.wi_thunderstorm);
                put(961, R.drawable.wi_thunderstorm);
                put(962, R.drawable.wi_cloudy_gusts);
            }});

    public static Integer getIconDrawableResource(int iconCode) {
        return CONSTANT_MAP.get(iconCode);
    }
}
