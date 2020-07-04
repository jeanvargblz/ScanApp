package com.proyecto.scanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.proyecto.scanapp.Sensor.OrientSensorEventListener;

public class Orientation extends AppCompatActivity {
    SensorManager sensorManager;
    OrientSensorEventListener orientSensorEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);

        initSensor();
    }
    private void initSensor() {
        sensorManager  = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensorMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        orientSensorEventListener = new OrientSensorEventListener();
        sensorManager.registerListener(
                orientSensorEventListener,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(
                orientSensorEventListener,
                sensorMagnetometer,
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(orientSensorEventListener);
    }
}