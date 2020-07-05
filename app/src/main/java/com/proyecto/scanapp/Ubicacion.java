package com.proyecto.scanapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Ubicacion extends AppCompatActivity {

    private LocationManager ubicacion;
    private TextView latitud, longitud, dir;
    private Button play,stop;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);
        setTitle("Ubicacion");


        latitud = findViewById(R.id.txtLat);
        longitud = findViewById(R.id.txtLong);
        dir= findViewById(R.id.txtDir);
        play =findViewById(R.id.iniciarServ);
 //       stop =findViewById(R.id.stopServ);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initGPS();
            }
        });

        /*
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopGPS();
            }
        });
         */




//        localizacion();

        registrarLocalizacion();

        checkPermission();

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
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    //        Config.mensaje(this, "Requesting permissions");
                }
            }
        }
    }

    /*
    private void localizacion() {

        checkPermission();
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ubicacion != null) {
//            Log.d("LOCALIZACION: LATITUD=", String.valueOf(loc.getLatitude()));
//            Log.d("LOCALIZACION: LONGITUD=", String.valueOf(loc.getLongitude()));
//            latitud.setText(String.valueOf(loc.getLatitude()));
//            longitud.setText(String.valueOf(loc.getLongitude()));
        }
    }

     */

    public void initGPS() {

        Intent intent = new Intent(this, LocationBroadcastReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(//sendBroadcast(...)
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, pendingIntent);
    }





    private void registrarLocalizacion() {

        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        ubicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, new LocalizacionListener());
    }

    private class LocalizacionListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String lat = "Latitud: " +location.getLatitude();
            String lon = "Latitud: " +location.getLongitude();

            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> direccion = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                System.out.println(direccion.get(0).getAddressLine(0));
//                System.out.println(direccion.get(0).getCountryName());
//                System.out.println(direccion.get(0).getLocality());
                dir.setText(direccion.get(0).getAddressLine(0));

            } catch (IOException e) {
                e.printStackTrace();
            }

            latitud.setText(lat);
            longitud.setText(lon);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}