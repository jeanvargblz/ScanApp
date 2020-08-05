package com.proyecto.scanapp.Sensor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.proyecto.scanapp.Inicio.ListadoActivity;

public class OrientSensorEventListener implements SensorEventListener {
    private static final String TAG = "AcceSensorEventListener";
    private float[] accelerationValues;
    private float[] magneticValues;
    private boolean estado = false;
    private int cont = 0;


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix;

        switch (event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:
                accelerationValues = event.values.clone();
                rotationMatrix = generateRotationMatrix();

                if (rotationMatrix != null)
                {
                    determineOrientation(rotationMatrix);
                }

                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
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

        if (pitch <= 10) {
            if (Math.abs(roll) >= 170 && !estado) {
                Log.d("ESTADO ORIENTACION", "BOCA ABAJO ");
                cont ++;
                estado = true;

            } else if (Math.abs(roll) <= 10 && estado) {
                Log.d("ESTADO ORIENTACION", "BOCA ARRIBA");
                estado = false;

            }
        }
        if(cont >= 2){
            ListadoActivity.getInstance().openLector();
            Log.d("ESTADO ORIENTACION","ABRIENDO CAMARA");
            cont = 0;
        }

        /*
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
                Log.d("ORIENTACION", "BOCA ABAJO "+roll);

            } else if (Math.abs(roll) <= 10) {
                Log.d("ORIENTACION", "BOCA ARRIBA");

            }
        }

         */
    }

}
