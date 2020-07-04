package com.proyecto.scanapp.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class MoveSensorEventListener implements SensorEventListener {
    private static final String TAG = "AcceSensorEventListener";
    private float mLastX, mLastY, mLastZ;
    private float mHighPassx, mHighPassy,mHighPassz;

    private float a1 = 0.85f;
    private float[] accelerationValues;
    private float[] magneticValues;


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x, y, z;
        double sumOfSquares, acceleration;
        accelerationValues = event.values.clone();
                x = accelerationValues[0];
                y = accelerationValues[1];
                z = accelerationValues[2];

                mHighPassx = highPass(x, mLastX, mHighPassx);
                mHighPassy = highPass(y, mLastY, mHighPassy);
                mHighPassz = highPass(z, mLastZ, mHighPassz);

                mLastX = x;
                mLastY = y;
                mLastZ = z;


                sumOfSquares = (mHighPassx * mHighPassx) + (mHighPassy * mHighPassy) + (mHighPassz * mHighPassz);
                acceleration = Math.sqrt(sumOfSquares);
                determineMovement(acceleration);
                Log.d(TAG, "Acceleration: "+"x: "+mHighPassx+",\t\ty: " + mHighPassy+",\t\tz:"+mHighPassz +",\t\tacceleration:"+acceleration);


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void determineMovement(double acceleration){
        if(acceleration>= 2){

            Log.d("ESTADO", "NO MUEVA EL DISPOSITIVO");
        }
        else {

            Log.d("ESTADO", "DISPOSITIVO QUIETO");
        }

    }
    private float highPass(float current, float last, float filtered) {
        return a1 * (filtered + current - last);
    }
}
