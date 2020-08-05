package com.proyecto.scanapp.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.scanapp.R;

public class Movement extends AppCompatActivity {
    private static Movement ins;
    SensorManager sensorManager;
    MoveSensorEventListener moveSensorEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ins = this;
        setContentView(R.layout.activity_orientation);

        initSensor();
    }
    private void initSensor() {
        sensorManager  = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        moveSensorEventListener = new MoveSensorEventListener();
        sensorManager.registerListener(
                moveSensorEventListener,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(moveSensorEventListener);
    }
    public static Movement getInstance(){
        return ins;
    }

    public void showMessage(){
        Movement.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(ins,"No mueva el Dispositivo",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

}