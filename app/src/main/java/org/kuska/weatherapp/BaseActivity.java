package org.kuska.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import org.kuska.weatherapp.injection.AppComponent;
import org.kuska.weatherapp.injection.DaggerAppComponent;
import org.kuska.weatherapp.injection.NetworkModule;

import java.io.File;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    AppComponent appComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        appComponent = DaggerAppComponent.builder().networkModule(new NetworkModule(cacheFile)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected void showNotificationDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_notification_dialog_positive_button, null)
                .create()
                .show();
    }

}
