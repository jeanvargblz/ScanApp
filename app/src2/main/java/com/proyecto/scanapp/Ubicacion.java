package com.proyecto.scanapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Ubicacion extends AppCompatActivity {

    private LocationManager ubicacion;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        localizacion();
        checkPermission();
    }

    private void localizacion() {

        checkPermission();
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ubicacion != null) {
            Log.d("LOCALIZACION: LATITUD=", String.valueOf(loc.getLatitude()));
            Log.d("LOCALIZACION: LONGITUD=", String.valueOf(loc.getLongitude()));
        }
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //  Config.mensaje(this, "This version is not android 6 or later " + Build.VERSION.SDK_INT);
        } else {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                }, REQUEST_CODE_ASK_PERMISSIONS);
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    //        Config.mensaje(this, "Requesting permissions");
                }
            }
            return;
        }
    }
}