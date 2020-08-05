package com.proyecto.scanapp.Inicio;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.scanapp.R;
import com.proyecto.scanapp.Sensor.LectorActivity;
import com.proyecto.scanapp.Sensor.OrientSensorEventListener;
import com.proyecto.scanapp.Sensor.Ubicacion;

public class ListadoActivity extends AppCompatActivity {

    private Button btnScanner, btnUbi, btnOrientacion;
    private static ListadoActivity ins;
    SensorManager sensorManager;
    OrientSensorEventListener orientSensorEventListener;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ins = this;
        setContentView(R.layout.activity_listado);
        setTitle("");

        btnScanner =findViewById(R.id.btnScanner);
        btnUbi  =findViewById(R.id.btnUbicacion);
//        btnOrientacion = findViewById(R.id.btnOrientacion);


        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LectorActivity.class));
            }
        });

        btnUbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Ubicacion.class));
            }
        });

        checkPermission();
    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            //  Config.mensaje(this, "This version is not android 6 or later " + Build.VERSION.SDK_INT);
        }else {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);
            if(hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                //        Config.mensaje(this, "Requesting permissions");
            }else if(hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){
                //      Config.mensaje(this, "The permissions are already granted ");
                //open camera
            }
        }
        return;
    }
    public void onRequestPemissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode){
            if (grantResults[0] ==PackageManager.PERMISSION_GRANTED){

            }else{

            }
        }else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cerrar:
                SharedPreferences sharedPref = getSharedPreferences("loginsesion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("nombre", "");
                editor.apply();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initSensor() {
        sensorManager  = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensorMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        orientSensorEventListener = new OrientSensorEventListener();
        sensorManager.registerListener(
                orientSensorEventListener,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(
                orientSensorEventListener,
                sensorMagnetometer,
                SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initSensor();
        Log.d("ESTADO ORIENTACION","Iniciando");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(orientSensorEventListener);
    }
    public static ListadoActivity getInstance() {
        return ins;
    }


    public void openLector(){
        startActivity(new Intent(getApplicationContext(), LectorActivity.class));
    }
}
