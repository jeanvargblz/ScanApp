package com.proyecto.scanapp.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class OrientSensorEventListener implements SensorEventListener {
    private static final String TAG = "AcceSensorEventListener";
    private float mLastX, mLastY, mLastZ;
    private float mHighPassx, mHighPassy,mHighPassz;

    private float a1 = 0.85f;
    private float[] accelerationValues;
    private float[] magneticValues;


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix;
        float x, y, z;
        double sumOfSquares, acceleration;


        switch (event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:
                accelerationValues = event.values.clone();
                rotationMatrix = generateRotationMatrix();

                if (rotationMatrix != null)
                {
                    determineOrientation(rotationMatrix);
                }

               /* x = accelerationValues[0];
                y = accelerationValues[1];
                z = accelerationValues[2];

                mHighPassx = highPass(x, mLastX, mHighPassx);
                mHighPassy = highPass(y, mLastY, mHighPassy);
                mHighPassz = highPass(z, mLastZ, mHighPassz);

                mLastX = x;
                mLastY = y;
                mLastZ = z;

                Log.d(TAG, "Current: "+"x: "+x+",\t\ty: " + y+",\t\tz:"+z);
                Log.d(TAG, "Filtered: "+ "x: "+mHighPassx+",\t\ty: "+mHighPassy+",\t\tz: "+mHighPassz);


                sumOfSquares = (mHighPassx * mHighPassx) + (mHighPassy * mHighPassy) + (mHighPassz * mHighPassz);
                acceleration = Math.sqrt(sumOfSquares);

                Log.d(TAG, "Acceleration: "+"x: "+mHighPassx+",\t\ty: " + mHighPassy+",\t\tz:"+mHighPassz +",\t\tacceleration:"+acceleration);

                */

                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                Log.d(TAG, "magnetometer:");
                magneticValues = event.values.clone();
                rotationMatrix = generateRotationMatrix();

                if (rotationMatrix != null)
                {
                    determineOrientation(rotationMatrix);
                }
                break;
        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // simple low-pass filter
    // percentage of sharing
    private float[] generateRotationMatrix() {
        float[] rotationMatrix = null;

        if (accelerationValues != null && magneticValues != null) {
            rotationMatrix = new float[16];
            boolean rotationMatrixGenerated;
            rotationMatrixGenerated =
                    SensorManager.getRotationMatrix(rotationMatrix,
                            null,
                            accelerationValues,
                            magneticValues);

            if (!rotationMatrixGenerated) {
                rotationMatrix = null;
            }
        }

        return rotationMatrix;
    }

    private void determineOrientation(float[] rotationMatrix) {

        float[] orientationValues = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationValues);

        double azimuth = Math.toDegrees(orientationValues[0]);
        double pitch = Math.toDegrees(orientationValues[1]);
        double roll = Math.toDegrees(orientationValues[2]);
        Log.d("pitch", "" + pitch);
        Log.d("azimuth", "" + azimuth);
        Log.d("roll", "" + roll);


        if (Math.abs(pitch) <= 3 && !(Math.abs(roll) >= 3)) {
            Log.d("ORIENTACION", "HECHADO");

        }
        else if (pitch <= 10) {
            if (roll >= 45 && roll <= 100){
                Log.d("ORIENTACION", "VERTICAL DERECHA");

            }
            else if(roll <= -45 && roll >= -100){
                Log.d("ORIENTACION", "VERTICAL IZQUIERDA");

            }
            else if (Math.abs(roll) >= 170) {
                Log.d("ORIENTACION", "BOCA ABAJO");

            } else if (Math.abs(roll) <= 10) {
                Log.d("ORIENTACION", "BOCA ARRIBA");

            }
        }
    }
    private void determineMovement(double current, double last){
        if((Math.abs(current - last) )>= 3){

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
