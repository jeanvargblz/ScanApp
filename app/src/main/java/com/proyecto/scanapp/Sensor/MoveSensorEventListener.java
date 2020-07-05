package com.proyecto.scanapp.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.proyecto.scanapp.LectorActivity;

public class MoveSensorEventListener implements SensorEventListener {
    private static final String TAG = "AcceSensorEventListener";
    private float mLastX, mLastY, mLastZ;
    private float mHighPassx, mHighPassy, mHighPassz;
    private float initx = 0, inity = 0, initz = 0;
    private float a1 = 0.85f;
    private float[] accelerationValues;
    private boolean estadoMensaje;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float currentx, currenty, currentz;

        double sumOfSquares, acceleration;
        accelerationValues = event.values.clone();
        currentx = accelerationValues[0];
        currenty = accelerationValues[1];
        currentz = accelerationValues[2];

        mHighPassx = highPass(currentx, mLastX, mHighPassx);
        mHighPassy = highPass(currenty, mLastY, mHighPassy);
        mHighPassz = highPass(currentz, mLastZ, mHighPassz);

        mLastX = currentx;
        mLastY = currenty;
        mLastZ = currentz;


        sumOfSquares = (mHighPassx * mHighPassx) + (mHighPassy * mHighPassy) + (mHighPassz * mHighPassz);
        acceleration = Math.sqrt(sumOfSquares);
        determineMovement(acceleration);
        Log.d(TAG, "Acceleration: " + "x: " + mHighPassx + ",\t\ty: " + mHighPassy + ",\t\tz:" + mHighPassz + ",\t\tacceleration:" + acceleration);


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void determineMovement(double acceleration) {


        if (acceleration >= 1 && !estadoMensaje) {

            LectorActivity.getInstance().showMessage();

            Log.d("ESTADO", "NO MUEVA EL DISPOSITIVO");
            estadoMensaje = true;

        } else {
            estadoMensaje = false;
        }

    }

    private float highPass(float current, float last, float filtered) {
        return a1 * (filtered + current - last);
    }


}
