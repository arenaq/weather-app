package org.kuska.weatherapp.feature.home;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

import org.kuska.weatherapp.BaseActivity;
import org.kuska.weatherapp.R;
import org.kuska.weatherapp.models.CurrentWeatherData;
import org.kuska.weatherapp.models.currentweatherdata.Sys;
import org.kuska.weatherapp.models.currentweatherdata.Weather;
import org.kuska.weatherapp.models.currentweatherdata.Wind;
import org.kuska.weatherapp.util.FlagIcons;
import org.kuska.weatherapp.util.TemperatureUnit;
import org.kuska.weatherapp.util.WeatherInfoIcons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class WeatherActivity extends BaseActivity implements WeatherView, OnMapReadyCallback, LocationEngineListener, PermissionsListener, MapboxMap.OnMapClickListener {
    private static final String SHARED_PREF = "org.kuska.weatherapp";
    private SharedPreferences sharedPreferences;

    private MapView mapView;
    private MapboxMap map;

    private ProgressBar progressBar;
    private ImageView imgCountryFlag;
    private TextView txtTitle;
    private TextView txtWeatherDescription;
    private ImageView imgWeather;
    private TextView txtTemperature;
    private ImageView imgWindDir;
    private TextView txtWindSpeed;
    private RelativeLayout containerSun;
    private TextView txtSun;

    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Marker selectedPositionMarker;

    @Inject public WeatherPresenter weatherPresenter;
    private CurrentWeatherData currentWeatherData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        setContentView(R.layout.activity_home);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        progressBar = findViewById(R.id.progress);
        imgCountryFlag = findViewById(R.id.imgCountryFlag);
        txtTitle = findViewById(R.id.txtTitle);
        txtWeatherDescription = findViewById(R.id.txtWeatherDescription);
        imgWeather = findViewById(R.id.imgWeather);
        txtTemperature = findViewById(R.id.txtTemperature);
        imgWindDir = findViewById(R.id.imgWindDir);
        txtWindSpeed = findViewById(R.id.txtWindSpeed);
        containerSun = findViewById(R.id.containerSun);
        txtSun = findViewById(R.id.txtSun);
        ImageButton btnLocate = findViewById(R.id.btnLocate);
        btnLocate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (locationEngine != null) {
                    Location lastLocation = locationEngine.getLastLocation();
                    if (lastLocation != null) {
                        showCurrentLocation(new LatLng(lastLocation));
                    }
                } else {
                    getCurrentLocation();
                }
            }
        });

        weatherPresenter.attachView(this);
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        map.addOnMapClickListener(this);
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationEngine();
            initializeLocationLayer();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void initializeLocationEngine() {
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.NO_POWER);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            showCurrentLocation(new LatLng(lastLocation));
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    private void initializeLocationLayer() {
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
        map.setMinZoomPreference(0);
        map.setMaxZoomPreference(12);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            showCurrentLocation(new LatLng(location));
        }
    }

    private void showCurrentLocation(@NonNull LatLng point) {
        showLocation(point);
        map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(point.getLatitude(), point.getLongitude())), 2500);
    }

    private void showLocation(@NonNull LatLng point) {
        if (selectedPositionMarker != null) {
            map.removeMarker(selectedPositionMarker);
        }
        selectedPositionMarker = map.addMarker(new MarkerOptions().position(point));
        weatherPresenter.getCurrentWeather(point.getLatitude(), point.getLongitude());
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        showLocation(point);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        showNotificationDialog(getString(R.string.permissionAlertTitle), getString(R.string.permissionAlertMessage));
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String appErrorMessage) {
        showNotificationDialog(getString(R.string.alert_notification_dialog_title_error), appErrorMessage);
    }

    @Override
    public void setData(CurrentWeatherData currentWeatherData) {
        this.currentWeatherData = currentWeatherData;
        if (currentWeatherData.getName() != null) {
            txtTitle.setText(currentWeatherData.getName());
            txtTitle.setVisibility(View.VISIBLE);
        }

        if (currentWeatherData.getWeather() != null) {
            Weather weather = currentWeatherData.getWeather().get(0);
            if (weather != null) {
                if (weather.getDescription() != null) {
                    txtWeatherDescription.setText(weather.getDescription());
                    txtWeatherDescription.setVisibility(View.VISIBLE);
                }
                if (weather.getId() != null && WeatherInfoIcons.getIconDrawableResource(weather.getId()) != null) {
                    imgWeather.setBackgroundResource(WeatherInfoIcons.getIconDrawableResource(weather.getId()));
                    imgWeather.setVisibility(View.VISIBLE);
                }
            }
        }

        setTemperature();

        Wind wind = currentWeatherData.getWind();
        if (wind != null) {
            if (wind.getDeg() != null) {
                imgWindDir.setBackgroundResource(R.drawable.wi_wind_deg);
                imgWindDir.setRotation(wind.getDeg().floatValue());
                imgWindDir.setVisibility(View.VISIBLE);
            } else if (wind.getSpeed() != null) {
                imgWindDir.setBackgroundResource(R.drawable.wi_strong_wind);
                imgWindDir.setRotation(0);
                imgWindDir.setVisibility(View.VISIBLE);
            }
            imgWindDir.setVisibility(View.VISIBLE);
            if (wind.getSpeed() != null) {
                txtWindSpeed.setText(String.format(Locale.getDefault(), "%.1f m/s",
                        wind.getSpeed()));
                txtWindSpeed.setVisibility(View.VISIBLE);
            }
        }

        Sys sys = currentWeatherData.getSys();
        if (sys != null) {
            if (sys.getCountry() != null && FlagIcons.getIconDrawableResource(sys.getCountry()) != null) {
                imgCountryFlag.setBackgroundResource(FlagIcons.getIconDrawableResource(sys.getCountry()));
                imgCountryFlag.setVisibility(View.VISIBLE);
            }

            if (sys.getSunrise() != null && sys.getSunset() != null) {
                Date sunrise = new Date(Long.valueOf(sys.getSunrise()) * 1000);
                Date sunset = new Date(Long.valueOf(sys.getSunset()) * 1000);
                SimpleDateFormat f = new SimpleDateFormat("HH:mm", Locale.getDefault());
                txtSun.setText(String.format(Locale.getDefault(), "%s - %s",
                        f.format(sunrise),
                        f.format(sunset)));
                containerSun.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void clearData() {
        imgCountryFlag.setVisibility(View.INVISIBLE);
        txtTitle.setVisibility(View.INVISIBLE);
        txtWeatherDescription.setVisibility(View.INVISIBLE);
        imgWeather.setVisibility(View.INVISIBLE);
        txtTemperature.setVisibility(View.INVISIBLE);
        imgWindDir.setVisibility(View.INVISIBLE);
        txtWindSpeed.setVisibility(View.INVISIBLE);
        containerSun.setVisibility(View.INVISIBLE);
    }

    private void setTemperature() {
        if (currentWeatherData.getMain() != null && currentWeatherData.getMain().getTemp() != null) {
            TemperatureUnit tempUnit = TemperatureUnit.getInstance(sharedPreferences);
            txtTemperature.setText(String.format(Locale.getDefault(), "%.0f %s",
                    tempUnit.getTemperature(currentWeatherData.getMain().getTemp()),
                    tempUnit.getUnitLabel()));
            txtTemperature.setVisibility(View.VISIBLE);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // TODO handle orientation change - add android:configChanges="orientation|screenSize" to activity in manifest
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem fahrenheit = menu.getItem(0);
        switch (sharedPreferences.getInt(TemperatureUnit.PREF_TEMPERATURE_UNIT, 0)) {
            case TemperatureUnit.PREF_FAHRENHEIT: fahrenheit.setChecked(true); break;
            case TemperatureUnit.PREF_CELSIUS: fahrenheit.setChecked(false);
            default:
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fahrenheit:
                if (item.isCheckable()) {
                    if (item.isChecked()) {
                        item.setChecked(false);
                        sharedPreferences.edit().putInt(TemperatureUnit.PREF_TEMPERATURE_UNIT, TemperatureUnit.PREF_CELSIUS).apply();
                        setTemperature();
                    } else {
                        item.setChecked(true);
                        sharedPreferences.edit().putInt(TemperatureUnit.PREF_TEMPERATURE_UNIT, TemperatureUnit.PREF_FAHRENHEIT).apply();
                        setTemperature();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onStart() {
        super.onStart();
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            if (locationEngine != null) {
                locationEngine.requestLocationUpdates();
            }
            if (locationLayerPlugin != null) {
                locationLayerPlugin.onStart();
            }
        }
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStop();
        }
        mapView.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        weatherPresenter.detachView();
        if (locationEngine != null) {
            locationEngine.deactivate();
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.exitAlertMessage)
                .setPositiveButton(R.string.yes, (arg0, arg1) -> finish())
                .setNegativeButton(R.string.no, null)
                .show();
    }

}
